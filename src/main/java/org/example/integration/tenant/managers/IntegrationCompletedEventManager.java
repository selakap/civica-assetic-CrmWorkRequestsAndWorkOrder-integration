package org.example.integration.tenant.managers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.integration.tenant.constants.TenantConstants;
import org.example.integration.tenant.data.HttpRsponseData;
import org.example.integration.tenant.data.IntegrationCompletedEventData;
import org.example.integration.tenant.models.IntegrationCompletedEvent;
import org.example.integration.tenant.operators.HttpOperator;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class IntegrationCompletedEventManager implements Runnable{
    private static final Logger log = LogManager.getLogger(IntegrationCompletedEventManager.class);
    private final IntegrationCompletedEventData items;
    private final String trackId;

    public IntegrationCompletedEventManager(IntegrationCompletedEventData items, String trackId) {
        this.items = items;
        this.trackId = trackId;
    }

    @Override
    public void run() {
        log.info("{} new integration completed event. items: {}",trackId, items);

        String timestamp = getCurrentUtcTime();
        IntegrationCompletedEvent.LogEntry logEntry = new IntegrationCompletedEvent.LogEntry();
        logEntry.setStatus(items.getStatus());
        logEntry.setSyncTime(timestamp);
        logEntry.setResponseCode(items.getResponseCode());
        logEntry.setResponse(items.getResponse());

        // Create List of Logs
        List<IntegrationCompletedEvent.LogEntry> logs = new ArrayList<>();
        logs.add(logEntry);

        // Create Detail
        IntegrationCompletedEvent.Detail detail = new IntegrationCompletedEvent.Detail();
        detail.setVersion(2);
        detail.setRecordType(items.getRecordType());
        detail.setTenantId(Integer.parseInt(TenantConstants.TENANT_ID));
        detail.setTenantIntegrationId(items.getTenantIntegrationId());
        detail.setSourceId(items.getSourceId());
        detail.setTargetId(items.getTargetId());
        detail.setStatus(items.getStatus());
        detail.setRetry(items.getRetryCount());
        detail.setTimestamp(timestamp);
        detail.setUpdatedAt(timestamp);
        detail.setLogs(logs);

        // Create IntegrationCompletedEvent
        IntegrationCompletedEvent event = new IntegrationCompletedEvent();
        event.setDetail(detail);

        // Serialize to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String eventPayload = null;
        try {
            eventPayload = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(event);
        } catch (JsonProcessingException e) {
            log.error("{} error in converting the payload: {}",trackId,e.toString());
        }

        // Print JSON Payload
        log.info(eventPayload);
        Map<String, String> headers = new HashMap<>();
        headers.put(TenantConstants.HEADER_CONTENT_TYPE, TenantConstants.CONTENT_TYPE_APPLICATION_JSON);
        headers.put(TenantConstants.COMPLETED_EVENT_API_KEY_HEADER, TenantConstants.COMPLETED_EVENT_API_KEY_VALUE);
        HttpRsponseData eventResponse = HttpOperator.commonHttpCall(TenantConstants.COMPLETED_EVENT_URL,eventPayload,headers,TenantConstants.METHOD_POST,trackId);
        log.info("{} event response: {}",trackId,eventResponse.getBody()+" "+eventResponse.getStatusCode());
    }

    public static String getCurrentUtcTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        ZonedDateTime utcNow = ZonedDateTime.now(ZoneOffset.UTC);
        return utcNow.format(formatter);
    }
}