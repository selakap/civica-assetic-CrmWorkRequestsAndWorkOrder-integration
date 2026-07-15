package org.example.integration.tenant.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.integration.tenant.constants.TenantConstants;
import org.example.integration.tenant.managers.AuthorityManager;
import org.example.integration.tenant.managers.EventManager;
import org.example.integration.tenant.processors.AsseticMainWOsProcessor;
import org.example.integration.tenant.processors.AsseticMainWRsProcessor;
import org.example.integration.tenant.processors.AuthorityTasksProcessor;
import org.example.integration.tenant.processors.RetrierProcessor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AuthorityToAsseticHandler implements RequestHandler<Map<String, Object>, Map<String, Object>> {
    private static final Logger log = LogManager.getLogger(AuthorityToAsseticHandler.class);
    public Map<String, Object> handleRequest(Map<String, Object> input, Context context) {
        Map<String, Object> response = new HashMap<>();

        String trackId = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        String authorityToken = AuthorityManager.generateToken(trackId);
        if (authorityToken == null) {
            response.put("statusCode", TenantConstants.STATUS_CODE_500);
            response.put("body", "Failed to generate Authority token");
            return response;
        }
        startTaskProcessor(trackId, authorityToken);
        startWRProcessor(trackId, authorityToken);
        startWOProcessor(trackId, authorityToken);
        startRetrierProcessor(trackId, authorityToken);




        response.put("statusCode", "200");
        response.put("body", "success");
        return response;
    }

    public void startTaskProcessor(String trackId, String authorityToken) {
        log.info("{} Starting Authority Task Processor",trackId);
        trackId = trackId+"-"+UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        Thread thread = new Thread(new AuthorityTasksProcessor(trackId, authorityToken));
        thread.start();
        EventManager.wait(thread,trackId);
    }

    public void startWRProcessor(String trackId, String authorityToken) {
        log.info("{} Starting Assetic WR Processor",trackId);
        trackId = trackId+"-"+UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        Thread thread = new Thread(new AsseticMainWRsProcessor(trackId, authorityToken));
        thread.start();
        EventManager.wait(thread,trackId);
    }

    public void startWOProcessor(String trackId, String authorityToken) {
        log.info("{} Starting Assetic WO Processor",trackId);
        trackId = trackId+"-"+UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        Thread thread = new Thread(new AsseticMainWOsProcessor(trackId, authorityToken));
        thread.start();
        EventManager.wait(thread,trackId);
    }

    public void startRetrierProcessor(String trackId, String authorityToken) {
        log.info("{} Starting retrier Processor",trackId);
        trackId = trackId+"-"+UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        Thread thread = new Thread(new RetrierProcessor(trackId, authorityToken));
        thread.start();
        EventManager.wait(thread,trackId);
    }
}
