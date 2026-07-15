package org.symphony3.smartglue.integration.murrindindi.operators;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.symphony3.smartglue.integration.murrindindi.constants.MurrindindiConstants;
import org.symphony3.smartglue.integration.murrindindi.data.DynamoData;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DynamoOperator {
    private static final Logger log = LogManager.getLogger(DynamoOperator.class);
    private DynamoOperator() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static void putItemIntoDynamo(DynamoData data, String trackId) {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(MurrindindiConstants.AWS_ACCESS_KEY_INTEGRATION,
                MurrindindiConstants.AWS_SECRET_KEY_INTEGRATION);

        DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .region(Region.of(MurrindindiConstants.AWS_REGION))
                .build();

        // Table name
        String tableName = MurrindindiConstants.AWS_DYNAMO_INTEGRATION_RECORD_TABLE_NAME;
        // Create the item attributes
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("tenant_integrations_id", AttributeValue.builder().s(data.getTenantIntegrationsId()).build()); // Partition key
        item.put("source_id", AttributeValue.builder().s(data.getSourceId()).build()); // source_id
        item.put("target_id", AttributeValue.builder().s(data.getTargetId()).build()); // target_id (replace with actual value)
        item.put("current_status", AttributeValue.builder().s(data.getStatus()).build()); // status
        item.put("retry_count", AttributeValue.builder().n(data.getRetryCount()).build()); // retry_count
        item.put("updated_at", AttributeValue.builder().s(data.getUpdatedAt()).build());
        item.put("created_at", AttributeValue.builder().s(data.getUpdatedAt()).build());
        item.put("input_json", AttributeValue.builder().s(data.getJson()).build()); // json (empty JSON object)
        log.info("{} put Items into dynamo db: {}",trackId, item);


        try {
            PutItemRequest putItemRequest = PutItemRequest.builder()
                    .tableName(tableName)
                    .item(item)
                    .build();
            // Put the item into DynamoDB
            dynamoDbClient.putItem(putItemRequest);
            log.info("{} Item successfully added to the table.",trackId);
        } catch (Exception e) {
            log.error("{} Unable to add item: {}",trackId, e.getMessage());
        } finally {
            dynamoDbClient.close();
        }
    }

    public static QueryResponse getItemFromDynamo(String tableName, String filterExpression, Map<String, AttributeValue> expressionAttributeValues, boolean isWithSortKey, String trackId) {

        log.info("{} get Items from dynamo db: {}",trackId, expressionAttributeValues);
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(MurrindindiConstants.AWS_ACCESS_KEY_INTEGRATION,
                MurrindindiConstants.AWS_SECRET_KEY_INTEGRATION);

        DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .region(Region.of(MurrindindiConstants.AWS_REGION))
                .build();
        try {

            // Build the query request
            if (Objects.isNull(filterExpression)) {
                QueryRequest queryRequest = QueryRequest.builder()
                        .tableName(tableName)
                        .keyConditionExpression(MurrindindiConstants.AWS_DYNAMO_INTEGRATION_RECORD_TABLE_PARTITION_KEY1 + " = :" +
                                MurrindindiConstants.AWS_DYNAMO_INTEGRATION_RECORD_TABLE_PARTITION_KEY1 +" AND "+
                                MurrindindiConstants.AWS_DYNAMO_INTEGRATION_RECORD_TABLE_PARTITION_KEY2+" = :"+
                                MurrindindiConstants.AWS_DYNAMO_INTEGRATION_RECORD_TABLE_PARTITION_KEY2)
                        .expressionAttributeValues(expressionAttributeValues)
                        .build();
                return dynamoDbClient.query(queryRequest);
            } else if (isWithSortKey) {
                QueryRequest queryRequest = QueryRequest.builder()
                        .tableName(tableName)
                        .keyConditionExpression(MurrindindiConstants.AWS_DYNAMO_INTEGRATION_RECORD_TABLE_PARTITION_KEY1 + " = :" +
                                MurrindindiConstants.AWS_DYNAMO_INTEGRATION_RECORD_TABLE_PARTITION_KEY1 +" AND "+
                                MurrindindiConstants.AWS_DYNAMO_INTEGRATION_RECORD_TABLE_PARTITION_KEY2+" = :"+
                                MurrindindiConstants.AWS_DYNAMO_INTEGRATION_RECORD_TABLE_PARTITION_KEY2)
                        .filterExpression(filterExpression)
                        .expressionAttributeValues(expressionAttributeValues)
                        .build();
                return dynamoDbClient.query(queryRequest);
            } else {
                QueryRequest queryRequest = QueryRequest.builder()
                        .tableName(tableName)
                        .keyConditionExpression(MurrindindiConstants.AWS_DYNAMO_INTEGRATION_RECORD_TABLE_PARTITION_KEY1 + " = :" +
                                MurrindindiConstants.AWS_DYNAMO_INTEGRATION_RECORD_TABLE_PARTITION_KEY1)
                        .filterExpression(filterExpression)
                        .expressionAttributeValues(expressionAttributeValues)
                        .build();
                return dynamoDbClient.query(queryRequest);
            }
        } catch (Exception e) {
            log.error("{} Query failed: {}",trackId, e.getMessage());
        } finally {
            dynamoDbClient.close();
        }
        return null;
    }

    public static void updateItem(String tableName, Map<String, AttributeValue> key, String updateExpression, Map<String, AttributeValue> expressionAttributeValues, String trackId) {
        log.info("{} Updating dynamo db: {}",trackId, expressionAttributeValues);
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(MurrindindiConstants.AWS_ACCESS_KEY_INTEGRATION,
                MurrindindiConstants.AWS_SECRET_KEY_INTEGRATION);

        DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .region(Region.of(MurrindindiConstants.AWS_REGION))
                .build();

        UpdateItemRequest updateItemRequest = UpdateItemRequest.builder()
                .tableName(tableName)
                .key(key)  // Use partition key and source_id as key
                .updateExpression(updateExpression)
                .expressionAttributeValues(expressionAttributeValues)
                .build();

        try {
            // Execute the update
            UpdateItemResponse response = dynamoDbClient.updateItem(updateItemRequest);
            log.info("{} Successfully updated item: {}",trackId, response);
        } catch (DynamoDbException e) {
            log.error("{} Unable to update item: {}",trackId, e.getMessage());
        } finally {
            dynamoDbClient.close();
        }
    }
}
