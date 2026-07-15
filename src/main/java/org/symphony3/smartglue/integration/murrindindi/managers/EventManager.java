package org.symphony3.smartglue.integration.murrindindi.managers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.symphony3.smartglue.integration.murrindindi.data.IntegrationCompletedEventData;

import java.util.List;
import java.util.concurrent.Future;


public class EventManager {
    private static final Logger log = LogManager.getLogger(EventManager.class);
    private EventManager() {
        throw new UnsupportedOperationException("Utility class");
    }
    public static void handleSGCompleteEvent(IntegrationCompletedEventData items, String trackId) {

        Thread thread = new Thread(new IntegrationCompletedEventManager(items, trackId));
        thread.start();
        wait(thread, trackId);


    }

    public static void wait(Thread T, String trackId) {
        try {
            // Wait for the thread to finish
            T.join();
        } catch (InterruptedException e) {
            log.error("{} Error in handling error: {}",trackId, e.toString());
            Thread.currentThread().interrupt();
        }
    }

    public static void waitForCompletion(List<Future<?>> futures) {
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                log.error("Error waiting for future completion: {}", e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }
}
