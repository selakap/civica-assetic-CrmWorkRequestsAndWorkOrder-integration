package org.example.integration.tenant.operators;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.integration.tenant.constants.TenantConstants;
import org.example.integration.tenant.data.HttpRsponseData;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class HttpOperator {
    private static final Logger log = LogManager.getLogger(HttpOperator.class);
    private HttpOperator() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static HttpRsponseData commonHttpCall(String urlString, String payload, Map<String, String> headers, String method, String trackId) {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(Long.parseLong(TenantConstants.ENDPOINT_TIMEOUTS))) // Connection timeout
                .build();
        return commonHttpCall(client, urlString, payload, headers, method, trackId);
    }
    public static HttpRsponseData commonHttpCall(HttpClient client, String urlString, String payload, Map<String, String> headers, String method, String trackId) {
        long startTime = System.nanoTime(); // Start time measurement
        log.info("{} Url: {}",trackId,urlString);
        StringBuilder responseBuilder = new StringBuilder();
        int responseCode;

        try {
            // Build the request
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(urlString))
                    .method(method.toUpperCase(), payload != null && !payload.isEmpty()
                            ? HttpRequest.BodyPublishers.ofString(payload, StandardCharsets.UTF_8)
                            : HttpRequest.BodyPublishers.noBody());

            // Add headers
            if (headers != null) {
                headers.forEach(requestBuilder::header);
            }

            HttpRequest request = requestBuilder.build();

            // Send the request and receive the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            responseCode = response.statusCode();
            responseBuilder.append(response.body());

            // Log response
            // Mask JWT tokens in response
            String maskedResponse = responseBuilder.toString().replaceAll("\"access_token\":[^&]*", "\"access_token\":\"***\"");
            log.info("{} response: {}",trackId, maskedResponse);
            log.info("{} responce code: {}",trackId,responseCode);
            return new HttpRsponseData(responseBuilder.toString(), responseCode);

        } catch (Exception e) {
            log.error(e.toString());
            Thread.currentThread().interrupt();
            return new HttpRsponseData(e.getMessage(), Integer.parseInt(TenantConstants.STATUS_CODE_500));
        } finally {
            long endTime = System.nanoTime(); // End time measurement
            long durationInMillis = (endTime - startTime) / 1_000_000; // Convert to milliseconds
            log.info("{} HTTP call execution time for commonHttpCall: {} ms",trackId, durationInMillis);
        }
    }

    public static HttpRsponseData commonHttpCallForBinaryResponse(String urlString, String payload, Map<String, String> headers, String method, String trackId) {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(Long.parseLong(TenantConstants.ENDPOINT_TIMEOUTS)))
                .build();
        return commonHttpCallForBinaryResponse(client, urlString, payload, headers, method, trackId);
    }

    public static HttpRsponseData commonHttpCallForBinaryResponse(HttpClient client, String urlString, String payload, Map<String, String> headers, String method, String trackId) {
        long startTime = System.nanoTime(); // Start time measurement

        log.info("{} Url: {}",trackId, urlString);
        int responseCode;

        try {
            // Build the request

            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(urlString))
                    .method(method.toUpperCase(), payload != null && !payload.isEmpty()
                            ? HttpRequest.BodyPublishers.ofString(payload, StandardCharsets.UTF_8)
                            : HttpRequest.BodyPublishers.noBody());

            // Add headers
            if (headers != null) {
                headers.forEach(requestBuilder::header);
            }

            HttpRequest request = requestBuilder.build();

            // Send the request and get binary response
            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
            responseCode = response.statusCode();

            String base64Encoded = responseCode == 200
                    ? Base64.getEncoder().encodeToString(response.body())
                    : new String(response.body(), StandardCharsets.UTF_8);
            // Convert response to Base64 string
            //String base64Encoded = Base64.getEncoder().encodeToString(response.body());

            Map<String, List<String>> responseHeaders = response.headers().map();
            log.info("{} Response code: {}",trackId, responseCode);
            log.debug("{} Response headers: {}",trackId, responseHeaders);

            return new HttpRsponseData(base64Encoded, responseCode, responseHeaders);

        } catch (Exception e) {
            log.error("{} Exception occurred: {}",trackId, e.toString());
            Thread.currentThread().interrupt();
            return new HttpRsponseData(e.getMessage(), Integer.parseInt(TenantConstants.STATUS_CODE_500));
        } finally {
            long endTime = System.nanoTime();
            long durationInMillis = (endTime - startTime) / 1_000_000;
            log.info("{} HTTP call execution time for commonHttpCallForBinaryResponse: {} ms" ,trackId,durationInMillis);
        }
    }


}
