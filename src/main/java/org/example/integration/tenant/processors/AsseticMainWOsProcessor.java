package org.example.integration.tenant.processors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.integration.tenant.constants.TenantConstants;
import org.example.integration.tenant.managers.AsseticManager;
import org.example.integration.tenant.managers.EventManager;
import org.example.integration.tenant.models.AsseticGetWorkOrderByTimeResponse;
import org.example.integration.tenant.operators.S3Operator;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AsseticMainWOsProcessor implements Runnable{
    private final String trackId;
    private final String authorityToken;
    private String nextRunTime;
    private final ExecutorService executorService = Executors.newFixedThreadPool(100);
    List<Future<?>> futures = new ArrayList<>();
    private static final Logger log = LogManager.getLogger(AsseticMainWOsProcessor.class);
    public AsseticMainWOsProcessor(String trackId, String authorityToken) {
        this.trackId = trackId;
        this.authorityToken = authorityToken;
    }

    @Override
    public void run() {
        try {
            log.info("{} starting AsseticWOProcessor",trackId);
            String lastRunTime = S3Operator.getLastRuntime(trackId, TenantConstants.ASSETIC_WO_LAST_RUNTIME);
            getWOList(lastRunTime);
            //S3Operator.updateLastRuntime(trackId,nextRunTime,TenantConstants.ASSETIC_WO_LAST_RUNTIME);
        } catch (Exception e) {
            log.error("{} Error in AsseticWOProcessor: {}",trackId, e.toString());
        }
    }


    public void getWOList(String lastRunTime) {
        log.info("{} getting WO list from assetic",trackId);
        nextRunTime = String.valueOf(Instant.now());
        List<AsseticGetWorkOrderByTimeResponse.WorkOrder> woList = AsseticManager.getWorkOrdersByLastModifiedTime(trackId,lastRunTime,nextRunTime,TenantConstants.TENANT_INTEGRATION_ID_ASSETIC_TO_CIVICA);
        if (woList != null) {
            for (AsseticGetWorkOrderByTimeResponse.WorkOrder wo : woList) {
                String executionId = trackId+"-"+ UUID.randomUUID().toString().replace("-", "").substring(0, 10);
                AsseticWOProcessor thread = new AsseticWOProcessor(executionId,authorityToken,wo);
                futures.add(executorService.submit(thread));
            }
        }
        EventManager.waitForCompletion(futures);
    }
}
