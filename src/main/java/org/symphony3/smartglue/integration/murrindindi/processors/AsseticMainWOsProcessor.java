package org.symphony3.smartglue.integration.murrindindi.processors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.symphony3.smartglue.integration.murrindindi.constants.MurrindindiConstants;
import org.symphony3.smartglue.integration.murrindindi.managers.AsseticManager;
import org.symphony3.smartglue.integration.murrindindi.managers.EventManager;
import org.symphony3.smartglue.integration.murrindindi.models.AsseticGetWorkOrderByTimeResponse;
import org.symphony3.smartglue.integration.murrindindi.operators.S3Operator;

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
            String lastRunTime = S3Operator.getLastRuntime(trackId, MurrindindiConstants.ASSETIC_WO_LAST_RUNTIME);
            getWOList(lastRunTime);
            //S3Operator.updateLastRuntime(trackId,nextRunTime,MurrindindiConstants.ASSETIC_WO_LAST_RUNTIME);
        } catch (Exception e) {
            log.error("{} Error in AsseticWOProcessor: {}",trackId, e.toString());
        }
    }


    public void getWOList(String lastRunTime) {
        log.info("{} getting WO list from assetic",trackId);
        nextRunTime = String.valueOf(Instant.now());
        List<AsseticGetWorkOrderByTimeResponse.WorkOrder> woList = AsseticManager.getWorkOrdersByLastModifiedTime(trackId,lastRunTime,nextRunTime,MurrindindiConstants.TENANT_INTEGRATION_ID_ASSETIC_TO_CIVICA_MURRINDINDI);
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
