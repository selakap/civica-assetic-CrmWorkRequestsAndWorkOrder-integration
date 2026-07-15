# Civica Authority ↔ Assetic Work Requests & Work Orders Integration

A serverless (AWS Lambda) integration that keeps **Civica Authority** (CRM / customer request management) and **Assetic** (asset & works management) in sync for a council/utility tenant:

- New **investigation / further-information CRM tasks** raised in Authority automatically create or update **Work Requests** in Assetic (including document attachments).
- **Work Request / Work Order** lifecycle events in Assetic (inspector assignment, rejection, completion, supporting information) automatically open, annotate, and close the corresponding **CRM tasks** in Authority.
- Every processed item is tracked in DynamoDB for idempotency, and failed items are retried automatically on subsequent runs.
- Every terminal outcome (success or failure) is published to an external "integration completed event" webhook for monitoring.

The integration runs as a single AWS Lambda function, invoked on a schedule (every 5 minutes via an EventBridge rule), packaged as a container image.

---

## Table of contents

1. [Architecture](#architecture)
2. [Integration directions & flows](#integration-directions--flows)
   - [Authority → Assetic (new CRM tasks)](#1-authority--assetic-new-crm-tasks)
   - [Assetic → Authority: Work Requests](#2-assetic--authority-work-requests)
   - [Assetic → Authority: Work Orders](#3-assetic--authority-work-orders)
   - [Retry processor](#4-retry-processor)
3. [Task/status code reference](#taskstatus-code-reference)
4. [Data & persistence](#data--persistence)
5. [Project structure](#project-structure)
6. [Configuration (environment variables)](#configuration-environment-variables)
7. [Local development & testing (AWS SAM)](#local-development--testing-aws-sam)
8. [Build, package & deploy](#build-package--deploy)
9. [Logging & observability](#logging--observability)

---

## Architecture

The integration is a single AWS Lambda function packaged as a container image. It has no persistent server component — an **EventBridge rule fires the Lambda every 5 minutes**, and each invocation runs four stages **sequentially**, one after another finishes:

1. `AuthorityTasksProcessor` — pulls new CRM tasks from **Civica Authority** and pushes them into **Assetic** as Work Requests.
2. `AsseticMainWRsProcessor` — pulls Work Request changes from **Assetic** and reflects them back into **Authority**.
3. `AsseticMainWOsProcessor` — pulls Work Order changes from **Assetic** and reflects them back into **Authority**.
4. `RetrierProcessor` — retries anything left in a `received`/`failed*` state from a previous run, plus attachment re-uploads.

Within each stage, individual items (tasks, work requests, work orders) are farmed out to a 100-thread pool and processed concurrently — so the stages themselves are sequential, but the work inside a stage is not.

All four stages share:
- A single **Authority OAuth2 bearer token**, generated once per invocation (`AuthorityManager.generateToken`) and reused throughout.
- An **S3 bucket** holding per-tenant configuration (which CRM categories/action codes should be integrated) and "last run time" bookmarks for each polling loop.
- A **DynamoDB table** (`integrationrecords`) used purely for idempotency and retry bookkeeping — every item processed gets a row recording its status and retry count.
- A **completed-event webhook** — every terminal success or failure is POSTed out for external monitoring.

---

## Integration directions & flows

Two constants identify the "direction" of a given operation and are stamped onto every DynamoDB record and webhook event:

- `TENANT_INTEGRATION_ID_CIVICA_TO_ASSETIC` — Authority → Assetic
- `TENANT_INTEGRATION_ID_ASSETIC_TO_CIVICA` — Assetic → Authority

### 1. Authority → Assetic (new CRM tasks)

`AuthorityTasksProcessor` polls Authority for tasks created since the last run (bookmarked in `last_runtime_authority.json` on S3), matches each task's CRM category + workflow action code against the tenant's configured mapping (loaded from the S3 config file), and dispatches applicable ones to `AuthorityProcessableTaskProcessor`. Before dispatching, it checks DynamoDB so an already-succeeded task is never reprocessed, while a `received`/`failed` one is retried.

Two task types are handled once a match is confirmed:
- **`ACTN` (investigate)** — looks up the CRM's linked name, property and (if no property) GPS data in Authority, then creates a brand-new Assetic Work Request from it, uploads any Authority attachments onto that Work Request, and writes a confirmation comment back onto the Authority task.
- **`CRF*` (further information)** — fetches the CRM task's notes and appends them as supporting information onto the existing Work Request in Assetic (found by matching Assetic's `ExternalIdentifier` to the Authority formatted account).

Every outcome (Work Request created, notes appended, or a failure at any step) updates the DynamoDB record and fires a completed-event webhook call.

### 2. Assetic → Authority: Work Requests

`AsseticMainWRsProcessor` polls Assetic Work Requests modified since the last run (bookmarked in `last_runtime_assetic_work_request.json`), keeps only the ones whose `ExternalIdentifier` matches the Authority id pattern (`\d{3}\.\d{4}\.\d{8}\.\d{3}`), and hands each one to `AsseticWRProcessor`, which independently checks three conditions — more than one can fire for the same Work Request:

| Condition | Processor | Authority task opened |
|---|---|---|
| Inspector assigned (`ReactiveInspectorId` + `ReactiveInspectionDate` set) | `AsseticWRInspectionProcessor` | `ASS2` – "Assigned to relevant inspector" |
| Status `Rejected` or `Cancelled` | `AsseticWRRejectProcessor` | `ASS1` – "Operations & Maintenance rejected/cancelled request" |
| New supporting-info entry not authored by this integration | `AsseticWRSupportInfoProcessor` | `ASS5` – "Operations & Maintenance additional information" |

Each of these sub-processors follows the same pattern via `AuthorityTaskManager`: check whether the relevant Authority task already exists for that account, open it if not, add a note describing what happened in Assetic, and close it — recording the outcome in DynamoDB at each step.

### 3. Assetic → Authority: Work Orders

`AsseticMainWOsProcessor` polls Assetic Work Orders modified since the last run (bookmarked in `last_runtime_assetic_work_order.json`) and hands each matching one to `AsseticWOProcessor`, which — again — evaluates every condition below independently, so a single poll can trigger more than one for the same Work Order:

| Condition | Processor | Authority task |
|---|---|---|
| Always, for a matching Work Order | `AsseticWONewProcessor` | Opens/verifies `ASS3` – "Works have been approved" |
| Status `INPRG`, `TCOMP`, `COMP` or `ASSESS` | `AsseticWOINPRGProcessor` | Opens/verifies `ASS4` – "Works assigned to a team or contractor" |
| Status `TCOMP`, `COMP` or `ASSESS` | `AsseticWOTCOMPProcessor` | Closes the original `ACTN` investigate task – "Works completed" |
| New supporting-info entry not authored by this integration | `AsseticWOSupportInfoProcessor` | Adds a note to the relevant task |

### 4. Retry processor

`RetrierProcessor` runs last on every invocation and covers two cases:

1. **Attachment retry** — re-uploads attachments for recently-succeeded Work Requests (target ids prefixed `wr`) via `AuthorityAttachmentsProcessor`, in case the original attachment upload failed after the Work Request itself was created successfully.
2. **Failed-entry retry** — scans DynamoDB for entries in a `received` or `failed*` state that are **older than 3 minutes** (so an attempt still in flight isn't raced) and have been **retried fewer than 4 times**, then re-dispatches each one to the processor matching its `source_id` prefix: a bare Authority task id goes back through the Authority-task retry path, while `wrInspect`, `wrReject`, `wrSupportInfo`, `woNew`, `woINPRG`, `woTCOMP` and `woSupportInfo` prefixes route back into the corresponding Assetic-side processor listed above. Anything still failing after 4 attempts is left as-is for manual investigation.

---

## Task/status code reference

Authority workflow action codes used by this integration (`TenantConstants`):

| Constant | Code | Meaning |
|---|---|---|
| `AUTHORITY_INVESTIGATE_TASK` | `ACTN` | New investigation task → creates Assetic WR |
| `AUTHORITY_FURTHER_INFO_TASK` | `CRF` | Further-information task → adds WR supporting info |
| `AUTHORITY_REJECTED_TASK` | `ASS1` | Opened when a WR is rejected/cancelled in Assetic |
| `AUTHORITY_REACTIVE_INSPECTION_TASK` | `ASS2` | Opened when an inspector is assigned in Assetic |
| `AUTHORITY_WO_CREATED_TASK` | `ASS3` | Opened when a Work Order is created in Assetic |
| `AUTHORITY_WO_INPRG_TASK` | `ASS4` | Opened when a Work Order moves in-progress |
| `AUTHORITY_SUPPORT_INFO_TASK` | `ASS5` | Opened for new WR supporting info |
| `AUTHORITY_TASK_CLOSE_CODE` | `CAC` | Determination code used to close `ASS*` tasks |
| `AUTHORITY_TASK_CLOSE_CODE_ACTN` | `CCOM` | Determination code used to close `ACTN` tasks |

DynamoDB `current_status` values: `received` → `success` | `failed` | `failed_actn` (task-notes-specific failure).

---

## Data & persistence

### DynamoDB — `integrationrecords`

Used purely for idempotency/retry bookkeeping; one row per processed item.

| Attribute | Description |
|---|---|
| `tenant_integrations_id` (partition key) | Direction constant (Civica→Assetic or Assetic→Civica) |
| `source_id` (sort key) | Correlates to the originating record, e.g. `{taskId}/{formattedAccount}/{crmId}`, `wrInspect{formattedAccount}`, `woNew{formattedAccount}`, etc. |
| `current_status` | `received` / `success` / `failed` / `failed_actn` |
| `retry_count` | Incremented on every failed retry attempt; retries stop at 4 |
| `target_id` | The downstream record created (e.g. `wr{id}`) |
| `input_json` | Last response/payload captured, for troubleshooting |
| `updated_at` / `created_at` | UTC timestamps (`yyyy-MM-dd HH:mm:ss`) |

### S3 bucket layout

```
TenantConfigurations/
└── {TENANT_ID}/
    ├── AuthorityTaskConfig/
    │   └── config.json              # category_id + action_code[] → whether to raise a WR
    └── last_run_time/
        ├── last_runtime_authority.json
        ├── last_runtime_assetic_work_request.json
        └── last_runtime_assetic_work_order.json
```

`config.json` shape (`AuthorityTaskConfiguration`):

```json
{
  "authority_tasks_config": [
    {
      "category_id": "123",
      "action_code": ["ACTN", "CRF"],
      "crm_category_description": "Example category"
    }
  ]
}
```

### Completed-event webhook

Every terminal outcome (success or failure) is POSTed as an `IntegrationCompletedEvent` to `COMPLETED_EVENT_URL` (with an `X-API-Key` header), carrying tenant id, direction, source/target ids, status, retry count, response code/body and a timestamp — intended for external monitoring/alerting rather than for driving integration logic.

---

## Project structure

```
src/main/java/org/example/integration/tenant/
├── handlers/    AuthorityToAsseticHandler        – Lambda entry point, orchestrates the 4 stages
├── processors/  AuthorityTasksProcessor, AuthorityProcessableTaskProcessor,
│                AuthorityAttachmentsProcessor, RetrierProcessor,
│                AsseticMainWRsProcessor / AsseticWRProcessor / AsseticWR*Processor,
│                AsseticMainWOsProcessor / AsseticWOProcessor / AsseticWO*Processor
├── managers/    AuthorityManager, AsseticManager, AuthorityTaskManager,
│                DynamoManager, EventManager, IntegrationCompletedEventManager
├── operators/   HttpOperator (java.net.http client), DynamoOperator (AWS SDK v2),
│                S3Operator (AWS SDK v2)
├── models/      Jackson request/response DTOs for Authority & Assetic REST APIs
├── data/        Plain data-holder classes passed between layers (DynamoData,
│                HttpRsponseData, AuthorityTaskManagingData, IntegrationCompletedEventData)
└── constants/   TenantConstants – all endpoints, env var keys, status/task codes
```

---

## Configuration (environment variables)

All configuration is supplied via Lambda environment variables (see `template.yaml` / `cloudformations/cloudformation.yml`):

| Variable | Purpose |
|---|---|
| `TENANT_ID` | Numeric tenant identifier, also used as the S3 config path segment |
| `TENANT_INTEGRATION_ID_CIVICA_TO_ASSETIC` | Direction id stamped on Authority→Assetic records/events |
| `TENANT_INTEGRATION_ID_ASSETIC_TO_CIVICA` | Direction id stamped on Assetic→Authority records/events |
| `AUTHORITY_HOST` | Authority tenant hostname (no scheme) |
| `AUTHORITY_USER` / `AUTHORITY_PASSWORD` | Authority resource-owner password grant credentials |
| `AUTHORITY_CLIENT_ID` / `AUTHORITY_CLIENT_SECRET` | Authority OAuth2 client credentials |
| `ASSETIC_HOST` | Assetic tenant hostname (no scheme) |
| `ASSETIC_API_BASIC` | Pre-encoded HTTP Basic credential for Assetic |
| `AWS_ACCESS_KEY_INTEGRATION` / `AWS_SECRET_KEY_INTEGRATION` / `AWS_REGION_INTEGRATION` | Explicit AWS credentials used by the S3/DynamoDB SDK clients |
| `AWS_BUCKET_NAME` | S3 bucket holding tenant config + last-run-time files |
| `AUTHORITY_CONFIG_CONFIGFILE_NAME` | S3 key of the category/action-code config file |
| `COMPLETED_EVENT_URL` / `COMPLETED_EVENT_API_KEY_VALUE` | Completed-event webhook endpoint + API key |
| `AWS_CONNECT_INSTANCE_ID` | Reserved (declared in constants; not currently referenced by any processor) |

---

## Local development & testing (AWS SAM)

Because the function ships as a container image, the most faithful way to exercise it on a laptop is [AWS SAM](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/install-sam-cli.html), which builds and runs the exact same image locally via Docker rather than relying on a plain `java -jar` invocation.

**Prerequisites**
- [AWS SAM CLI](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/install-sam-cli.html) installed.
- Docker Desktop (or another local Docker engine) running, since SAM builds and executes the Lambda inside a container that mirrors the real Lambda runtime.

**One-time setup**

The repository already provides everything SAM needs — `template.yaml` (the SAM template) and the `Dockerfile` it builds from — so there's no need to scaffold a new project with `sam init`. Before your first local run, open `template.yaml` and replace the `REPLACE_ME` placeholders with credentials for a sandbox/test Authority and Assetic tenant, plus AWS credentials for an S3 bucket and DynamoDB table you're happy to read/write against during testing. Keep any tenant-specific values you fill in out of version control.

**Building the image**

```
sam build
```

This compiles the project with Maven inside a container matching the `Dockerfile`, so what you run locally is the same artifact that would be deployed to Lambda.

**Invoking it directly**

The handler doesn't inspect its input event — invoking it simply triggers the four polling stages (Authority tasks, Assetic Work Requests, Assetic Work Orders, retries) against whatever Authority/Assetic/AWS endpoints are configured in `template.yaml`. A minimal event file (even an empty JSON object) is enough to trigger a run:

```
sam local invoke "LambdaFunctionTenant" --event path/to/event.json
```

`LambdaFunctionTenant` is the logical id of the function as declared in `template.yaml`; `event.json` can live anywhere convenient on your machine (it isn't part of the repo, since there's nothing tenant-specific to encode in it).

**Invoking it as an API**

`template.yaml` also exposes the same handler behind a local API Gateway route (`POST /crm`), which is convenient if you'd rather trigger runs repeatedly from a browser, `curl`, or Postman while iterating instead of re-running `sam local invoke` each time:

```
sam local start-api
```

**A few things worth knowing before testing**

- Local invocations still call the *real* Authority and Assetic REST APIs and the *real* AWS S3/DynamoDB resources named in your environment variables — there is no mock/stub layer, so point it at sandbox systems and a disposable S3 config/DynamoDB table rather than production.
- The S3 bucket you configure needs the tenant config file (`AuthorityTaskConfig/config.json`) and last-run-time files already seeded (see [S3 bucket layout](#s3-bucket-layout)), otherwise the first run will fail to find a starting bookmark.
- Logs print straight to your terminal via the Log4j2 console appender, prefixed with a per-invocation `trackId`, which is the quickest way to follow a local run stage by stage.

---

## Build, package & deploy

- **Build**: `mvn clean package` (Java 17, shaded/fat jar via `maven-shade-plugin`; JaCoCo coverage wired into `verify`).
- **Container image**: `Dockerfile` builds on `amazoncorretto:17`, compiles with Maven, and runs via the Lambda Runtime Interface Client (`com.amazonaws.services.lambda.runtime.api.client.AWSLambda`), entry point `org.example.integration.tenant.handlers.AuthorityToAsseticHandler::handleRequest`.
- **Infrastructure**: `cloudformations/cloudformation.yml` provisions the Lambda (image-based), its execution role (S3 read/write scoped to `TenantConfigurations/*`, plus logs/ENI permissions for VPC networking), a security group, and an EventBridge rule firing every 5 minutes. `template.yaml` is the local/dev-oriented SAM template used above (it also adds the API Gateway `POST /crm` route not present in the production CloudFormation stack).
- **CI/CD**: `bitbucket-pipelines.yml` defines `develop` and `production` custom pipelines that build the Docker image, push it to ECR, and deploy/update the CloudFormation stack; a separate `run-sonar-scan-quality-gw` pipeline runs `mvn clean verify` + SonarCloud analysis.

> Values marked `REPLACE_ME` in `template.yaml` are placeholders — populate them (and the CI/CD pipeline variables referenced in `bitbucket-pipelines.yml`) with real tenant-specific secrets before deploying.

---

## Logging & observability

- Log4j2 (`src/main/resources/log4j2.xml`) logs to console (picked up by CloudWatch under Lambda), one line per significant step, prefixed with a per-invocation `trackId` (and a per-thread sub-id) to correlate concurrent work across log lines.
- `HttpOperator` masks `access_token` values before logging HTTP responses.
- Every terminal success/failure additionally emits an `IntegrationCompletedEvent` to the configured webhook (see [Completed-event webhook](#completed-event-webhook)).