package org.example.integration.tenant.managers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.integration.tenant.constants.TenantConstants;
import org.example.integration.tenant.data.DynamoData;
import org.example.integration.tenant.operators.DynamoOperator;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.QueryResponse;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class DynamoManager {
    private static final Logger log = LogManager.getLogger(DynamoManager.class);
    private DynamoManager() {
        throw new UnsupportedOperationException("Utility class");
    }
    public static QueryResponse getEntryFromDynamo(String sourceId, String trackId){
        try {
            String tableName = TenantConstants.AWS_DYNAMO_INTEGRATION_RECORD_TABLE_NAME;
            String partitionKeyName = TenantConstants.AWS_DYNAMO_INTEGRATION_RECORD_TABLE_PARTITION_KEY1;
            String sourceIdKey = TenantConstants.AWS_DYNAMO_INTEGRATION_RECORD_TABLE_PARTITION_KEY2;
            String partitionKeyValue = TenantConstants.TENANT_INTEGRATION_ID_CIVICA_TO_ASSETIC; // Partition key value

            // Define dynamic filter expression and attributes
            String filterExpression = null;
            Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
            expressionAttributeValues.put(":"+sourceIdKey, AttributeValue.builder().s(sourceId).build());
            expressionAttributeValues.put(":" + partitionKeyName, AttributeValue.builder().s(partitionKeyValue).build());
            return DynamoOperator.getItemFromDynamo(tableName, filterExpression, expressionAttributeValues,true,trackId);
        } catch (Exception e) {
            log.error("{} Error getting contact from Dynamo: {}",trackId,e.getMessage());
            return null;
        }

    }

    public static void updateEntryOnDynamo(String sourceId, String status, String targetId, String payload, String trackId) {

        try {
            String tableName = TenantConstants.AWS_DYNAMO_INTEGRATION_RECORD_TABLE_NAME;
            String partitionKeyName = TenantConstants.AWS_DYNAMO_INTEGRATION_RECORD_TABLE_PARTITION_KEY1;
            String partitionKeyValue = TenantConstants.TENANT_INTEGRATION_ID_CIVICA_TO_ASSETIC; // Partition key value

            Map<String, AttributeValue> key = new HashMap<>();
            key.put(partitionKeyName, AttributeValue.builder().s(partitionKeyValue).build());  // Partition key
            key.put("source_id", AttributeValue.builder().s(sourceId).build());

            String updateExpression = "SET current_status = :current_status, retry_count = retry_count + :increment, updated_at = :updated_at, target_id = :target_id, input_json = :json";
            // Define filter expression and attributes
            Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
            expressionAttributeValues.put(":current_status", AttributeValue.builder().s(status).build());
            expressionAttributeValues.put(":increment", AttributeValue.builder().n("1").build());

            expressionAttributeValues.put(":updated_at", AttributeValue.builder().s(getCurrentUtcTime()).build()); // Current UTC time
            expressionAttributeValues.put(":target_id", AttributeValue.builder().s(targetId).build()); // Random target_id (replace as needed)
            expressionAttributeValues.put(":json", AttributeValue.builder().s(payload).build()); // Sample JSON

            DynamoOperator.updateItem(tableName, key, updateExpression, expressionAttributeValues,trackId);

        } catch (Exception e) {
            log.error("{} Error updating contact on Dynamo: {}",trackId,e.getMessage());
        }

    }

    public static QueryResponse getFailedEntryFromDynamo(String trackId){
        try {
            String tableName = TenantConstants.AWS_DYNAMO_INTEGRATION_RECORD_TABLE_NAME;
            String partitionKeyName = TenantConstants.AWS_DYNAMO_INTEGRATION_RECORD_TABLE_PARTITION_KEY1;
            String partitionKeyValue = TenantConstants.TENANT_INTEGRATION_ID_CIVICA_TO_ASSETIC; // Partition key value

            // Define dynamic filter expression and attributes
            String filterExpression = "retry_count < :retry_count AND (current_status = :failed OR current_status = :received OR current_status = :failed_actn)";
            Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
            expressionAttributeValues.put(":retry_count", AttributeValue.builder().n("4").build());
            expressionAttributeValues.put(":failed", AttributeValue.builder().s(TenantConstants.DYNAMO_STATUS_FAILED).build());
            expressionAttributeValues.put(":failed_actn", AttributeValue.builder().s(TenantConstants.DYNAMO_STATUS_FAILED_ACTN_TASK_NOTES).build());
            expressionAttributeValues.put(":received", AttributeValue.builder().s(TenantConstants.DYNAMO_STATUS_RECEIVED).build());
            expressionAttributeValues.put(":" + partitionKeyName, AttributeValue.builder().s(partitionKeyValue).build());

            return DynamoOperator.getItemFromDynamo(tableName, filterExpression, expressionAttributeValues, false,trackId);
        } catch (Exception e) {
            log.error("{} Error getting failed contacts from Dynamo: {}",trackId,e.getMessage());
            return QueryResponse.builder().items(new HashMap<>()).build();
        }
    }

    public static void createEntryOnDynamo(String sourceId, String trackId) {
        try {
            DynamoData data = new DynamoData();
            data.setTenantIntegrationsId(TenantConstants.TENANT_INTEGRATION_ID_CIVICA_TO_ASSETIC);
            data.setSourceId(sourceId);
            data.setTargetId("");
            data.setStatus(TenantConstants.DYNAMO_STATUS_RECEIVED);
            data.setRetryCount("0");
            data.setUpdatedAt(getCurrentUtcTime());
            data.setJson("{}");
            DynamoOperator.putItemIntoDynamo(data, trackId);
        } catch (Exception e) {
            log.error("{} Error creating entry on Dynamo: {}",trackId,e.getMessage());
        }
    }
    public static void createEntryOnWithData(DynamoData data, String trackId) {
        try {
            DynamoOperator.putItemIntoDynamo(data, trackId);
        } catch (Exception e) {
            log.error("{} Error creating entry on Dynamo: {}",trackId,e.getMessage());
        }
    }

    public static QueryResponse getRecentSuccessEntriesFromDynamo(String trackId) {
        try {
            String tableName = TenantConstants.AWS_DYNAMO_INTEGRATION_RECORD_TABLE_NAME;
            String partitionKeyName = TenantConstants.AWS_DYNAMO_INTEGRATION_RECORD_TABLE_PARTITION_KEY1;
            String partitionKeyValue = TenantConstants.TENANT_INTEGRATION_ID_CIVICA_TO_ASSETIC;

            // Get timestamp for one hour ago in the required format
            String oneHourAgoStr = getUtcTimeOneHourAgo();

            // Define filter expression
            String filterExpression = "current_status = :success AND created_at >= :time_threshold";

            Map<String, AttributeValue> expressionAttributeValues = new HashMap<>();
            expressionAttributeValues.put(":success", AttributeValue.builder().s(TenantConstants.DYNAMO_STATUS_SUCCESS).build());
            expressionAttributeValues.put(":time_threshold", AttributeValue.builder().s(oneHourAgoStr).build());
            expressionAttributeValues.put(":" + partitionKeyName, AttributeValue.builder().s(partitionKeyValue).build());

            return DynamoOperator.getItemFromDynamo(
                    tableName,
                    filterExpression,
                    expressionAttributeValues,
                    false,
                    trackId
            );
        } catch (Exception e) {
            log.error("{} Error getting successful entries from Dynamo: {}",trackId, e.getMessage());
            return null;
        }
    }

    public static String getCurrentUtcTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime utcNow = ZonedDateTime.now(ZoneOffset.UTC);
        return utcNow.format(formatter);
    }

    public static String getUtcTimeOneHourAgo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZonedDateTime oneHourAgo = ZonedDateTime.now(ZoneOffset.UTC).minusHours(1);
        return oneHourAgo.format(formatter);
    }
}
