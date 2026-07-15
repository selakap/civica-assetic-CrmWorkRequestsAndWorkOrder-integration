package org.symphony3.smartglue.integration.murrindindi.processors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.symphony3.smartglue.integration.murrindindi.constants.MurrindindiConstants;
import org.symphony3.smartglue.integration.murrindindi.managers.EventManager;
import org.symphony3.smartglue.integration.murrindindi.models.AsseticGetWorkOrderByTimeResponse;

import java.util.UUID;
import java.util.regex.Pattern;

public class AsseticWOProcessor implements Runnable {

    private final String trackId;
    private final String authorityToken;
    private final AsseticGetWorkOrderByTimeResponse.WorkOrder wo;
    private static final Logger log = LogManager.getLogger(AsseticWOProcessor.class);

    public AsseticWOProcessor(String trackId, String authorityToken, AsseticGetWorkOrderByTimeResponse.WorkOrder wo) {
        this.trackId = trackId;
        this.authorityToken = authorityToken;
        this.wo = wo;
    }

    @Override
    public void run() {
        log.info("{} starting AsseticWOProcessor for {}",trackId, wo.getExternalId());
        String externalId = wo.getExternalId();
        String[] items = externalId.split(",");
        for (String id : items) {
            id = id.trim();
            wo.setExternalId(id);
            if (isAuthorityId(wo.getExternalId())) {
                log.info("{} WO list item: {}",trackId,wo.getFriendlyIdStr());
                newWO(wo); //check all the work orders if it is new since we there is a possibility
                // to change the new work orders from open to close within 5 mins (time between two triggers)

                if (MurrindindiConstants.ASSETIC_WO_STATUS_INPRG.equals(wo.getStatus()) ||
                        MurrindindiConstants.ASSETIC_WO_STATUS_TCOMP.equals(wo.getStatus()) ||
                        MurrindindiConstants.ASSETIC_WO_STATUS_COMP.equals(wo.getStatus()) ||
                        MurrindindiConstants.ASSETIC_WO_STATUS_ASSES.equals(wo.getStatus())) {
                    inprgWO(wo);
                }
                if (wo.getSupportingInformation() != null && !wo.getSupportingInformation().isEmpty()) {
                    log.info("{} supporting information history is present in the wr: {}",trackId,wo.getFriendlyIdStr());
                    checkSupportInfo(wo);
                }
                if (MurrindindiConstants.ASSETIC_WO_STATUS_TCOMP.equals(wo.getStatus()) ||
                        MurrindindiConstants.ASSETIC_WO_STATUS_COMP.equals(wo.getStatus()) ||
                        MurrindindiConstants.ASSETIC_WO_STATUS_ASSES.equals(wo.getStatus())) {
                    tcompWO(wo);
                }

            } else {
                log.info("{} this is not following authority id pattern: {}",trackId,wo.getExternalId());
            }
        }

    }

    public boolean isAuthorityId(String input) {

        String pattern = "^\\d{3}\\.\\d{4}\\.\\d{8}\\.\\d{3}$";
        return Pattern.matches(pattern, input);
    }

    public void newWO(AsseticGetWorkOrderByTimeResponse.WorkOrder wo) {
        log.info("{} processing new W/O: {}",trackId,wo.getFriendlyIdStr());
        String executionId = trackId+"-"+ UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        Thread thread = new Thread(new AsseticWONewProcessor(executionId,authorityToken,wo));
        thread.start();
        EventManager.wait(thread,trackId);
    }

    public void inprgWO(AsseticGetWorkOrderByTimeResponse.WorkOrder wo) {
        log.info("{} processing INPRG W/O: {}",trackId,wo.getFriendlyIdStr());
        String executionId = trackId+"-"+ UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        Thread thread = new Thread(new AsseticWOINPRGProcessor(executionId,authorityToken,wo));
        thread.start();
        EventManager.wait(thread,trackId);
    }

    public void tcompWO(AsseticGetWorkOrderByTimeResponse.WorkOrder wo) {
        log.info("{} processing TCOMP WR: {}",trackId,wo.getFriendlyIdStr());
        String executionId = trackId+"-"+ UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        Thread thread = new Thread(new AsseticWOTCOMPProcessor(executionId,authorityToken,wo));
        thread.start();
        EventManager.wait(thread,trackId);

    }

    public void checkSupportInfo(AsseticGetWorkOrderByTimeResponse.WorkOrder wo) {
        for (AsseticGetWorkOrderByTimeResponse.SupportingInformation item : wo.getSupportingInformation()) {
            if (!item.getDescription().startsWith(MurrindindiConstants.ASSETIC_SUPPORT_INFO_PREFIX_FROM_AUTHORITY)) {
                log.info("{} supporting information is inside the trigger: {}",trackId,item.getId());
                supportInfoWO(wo,item.getId(),item.getDescription(),item.getCreatedByDisplayName(),item.getCreatedDateTime());
            } else {
                log.info("{} supporting information {} was created by integration on: {}. Hence ignore it.",trackId,item.getId(),wo.getFriendlyIdStr());
            }
        }
    }

    public void supportInfoWO(AsseticGetWorkOrderByTimeResponse.WorkOrder wo,String infoId, String info,
                              String name, String dateTime) {
        log.info("{} processing supportInfoWO W/O: {}",trackId,wo.getFriendlyIdStr());
        String executionId = trackId+"-"+ UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        Thread thread = new Thread(new AsseticWOSupportInfoProcessor(executionId,authorityToken,wo,infoId,info,name,dateTime));
        thread.start();
        EventManager.wait(thread,trackId);
    }
}
