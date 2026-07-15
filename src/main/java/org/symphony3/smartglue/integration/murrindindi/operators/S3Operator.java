package org.symphony3.smartglue.integration.murrindindi.operators;

import com.amazonaws.lambda.thirdparty.org.json.JSONObject;
import com.amazonaws.lambda.thirdparty.org.json.JSONTokener;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.symphony3.smartglue.integration.murrindindi.constants.MurrindindiConstants;
import org.symphony3.smartglue.integration.murrindindi.models.AuthorityTaskConfiguration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class S3Operator {
    private static final Logger log = LogManager.getLogger(S3Operator.class);
    private S3Operator() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String getLastRuntime(String trackId, String fileName) throws IOException {

        S3Client s3Client = S3Client.builder()
                .region(Region.of(MurrindindiConstants.AWS_REGION))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(MurrindindiConstants.AWS_ACCESS_KEY_INTEGRATION,
                        MurrindindiConstants.AWS_SECRET_KEY_INTEGRATION)))
                .build();
        // Construct the path to the JSON file
        String filePath = MurrindindiConstants.AWS_TENANT_FOLDER + MurrindindiConstants.SLASH+
                MurrindindiConstants.TENANT_ID_MURRINDINDI + MurrindindiConstants.SLASH
                + MurrindindiConstants.AWS_LAST_RUN_TIME_FOLDER +MurrindindiConstants.SLASH+fileName;
        log.info("{} Fetching file from S3: {}",trackId, filePath);

        try {
            // Get the JSON file from S3
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(MurrindindiConstants.AWS_SG_BUCKET_NAME)
                    .key(filePath)
                    .build();

            // Read the file content
            StringBuilder fileContent = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(s3Client.getObject(getObjectRequest)))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent.append(line);
                }
            }

            log.info("{} Raw file content: {}",trackId, fileContent);

            // Parse the JSON content
            JSONObject jsonObject = new JSONObject(fileContent.toString());


            return jsonObject.getString("time");

        } catch (S3Exception e) {
            String errorMessage = e.awsErrorDetails() != null && e.awsErrorDetails().errorMessage() != null ?
                    e.awsErrorDetails().errorMessage() : "Unknown error";
            log.error("{} Failed to fetch file from S3: {}",trackId, errorMessage, e);
            throw e;
        } catch (Exception e) {
            log.error("{} Error processing file content",trackId, e);
            throw e;
        }
    }


    public static void updateLastRuntime(String trackId,String newLatsRunTime, String fileName) throws Exception {

        S3Client s3Client = S3Client.builder()
                .region(Region.of(MurrindindiConstants.AWS_REGION))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(MurrindindiConstants.AWS_ACCESS_KEY_INTEGRATION,
                        MurrindindiConstants.AWS_SECRET_KEY_INTEGRATION)))
                .build();
        // Construct the path to the JSON file
        String filePath = MurrindindiConstants.AWS_TENANT_FOLDER + MurrindindiConstants.SLASH +
                MurrindindiConstants.TENANT_ID_MURRINDINDI + MurrindindiConstants.SLASH
                + MurrindindiConstants.AWS_LAST_RUN_TIME_FOLDER + MurrindindiConstants.SLASH+fileName;
        log.info("{} update file in S3: {}",trackId, filePath);

        try {
            // Get the JSON file from S3
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(MurrindindiConstants.AWS_SG_BUCKET_NAME)
                    .key(filePath)
                    .build();

            // Read the file content
            StringBuilder fileContent = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(s3Client.getObject(getObjectRequest)))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent.append(line);
                }
            }

            log.info("{} Raw file content: {}",trackId, fileContent);

            // Parse the JSON content
            JSONObject jsonObject = new JSONObject(fileContent.toString());

            // Update the "time" field with the current UTC time
            jsonObject.put("time", newLatsRunTime);

            // Convert the updated JSON back to a string
            String updatedContent = jsonObject.toString();

            // Upload the updated file back to S3
            byte[] contentBytes = updatedContent.getBytes(StandardCharsets.UTF_8);
            InputStream updatedContentStream = new ByteArrayInputStream(contentBytes);

            // Create a map for metadata (if necessary)
            Map<String, String> metadata = new HashMap<>();
            metadata.put("Content-Type", "application/json");
            metadata.put("Content-Length", String.valueOf(contentBytes.length));

            // Upload the updated content to S3
            s3Client.putObject(PutObjectRequest.builder()
                    .bucket(MurrindindiConstants.AWS_SG_BUCKET_NAME)
                    .key(filePath)
                    .metadata(metadata)  // Pass the metadata as a Map<String, String>
                    .build(), RequestBody.fromInputStream(updatedContentStream, contentBytes.length));  // Use RequestBody.fromInputStream

            log.info("{} Successfully updated the {} file with the current UTC time: {}",trackId,fileName, newLatsRunTime);

        } catch (S3Exception e) {
            log.error("{} Failed to fetch or update file from S3: {} {}",trackId, e.awsErrorDetails().errorMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("{} Error processing file content: {}",trackId, e);
            throw e;
        }
    }

    public static List<AuthorityTaskConfiguration.AuthorityTaskConfig> loadConfigFile() {

        String bucketName = MurrindindiConstants.AUTHORITY_CONFIG_BUCKET_NAME;
        String objectKey = MurrindindiConstants.AUTHORITY_CONFIG_FILE_NAME;
        List<AuthorityTaskConfiguration.AuthorityTaskConfig> authorityTaskConfigList = null;
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = getObjectFromS3(bucketName, objectKey)) { // Use try-with-resources for automatic resource management
            if (is != null) {
                JSONObject jsonObject = new JSONObject(new JSONTokener(is));
                authorityTaskConfigList = mapper.readValue(jsonObject.toString(), AuthorityTaskConfiguration.class).
                                                                                        getAuthorityTasksConfig();
            }
        } catch (Exception e) {
            log.error("Failed to load configuration file: {}", e.getMessage());
        }
        return authorityTaskConfigList;
    }

    private static InputStream getObjectFromS3(String bucketName, String key) {
        try {
            S3Client s3Client = S3Client.builder()
                    .region(Region.of(MurrindindiConstants.AWS_REGION))
                    .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(
                            MurrindindiConstants.AWS_ACCESS_KEY_INTEGRATION, MurrindindiConstants.AWS_SECRET_KEY_INTEGRATION)))
                    .build();
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            return s3Client.getObject(getObjectRequest);
        } catch (S3Exception e) {
            log.error("Error getting file from S3: {}" , e.awsErrorDetails().errorMessage());
            return null;
        }
    }
}
