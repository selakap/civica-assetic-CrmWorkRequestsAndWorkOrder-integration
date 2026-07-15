package org.symphony3.smartglue.integration.murrindindi.processors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.symphony3.smartglue.integration.murrindindi.constants.MurrindindiConstants;
import org.symphony3.smartglue.integration.murrindindi.managers.EventManager;
import org.symphony3.smartglue.integration.murrindindi.models.AsseticWorkRequestByExternalIdResponse;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.regex.Pattern;

public class AsseticWRProcessor implements Runnable {
    private final AsseticWorkRequestByExternalIdResponse.Resource wr;
    private final String authorityToken;
    private final String trackId;
    private final String lastRunTime;
    private final String nextRunTime;
    private static final Logger log = LogManager.getLogger(AsseticWRProcessor.class);


    public AsseticWRProcessor(String trackId, String authorityToken, AsseticWorkRequestByExternalIdResponse.Resource wr,
                              String lastRunTime, String nextRunTime) {
        this.trackId = trackId;
        this.authorityToken = authorityToken;
        this.wr = wr;
        this.lastRunTime = lastRunTime;
        this.nextRunTime = nextRunTime;
    }

    @Override
    public void run() {
        log.info("{} starting AsseticWRProcessor for {}",trackId, wr.getExternalIdentifier());
        if (isAuthorityId(wr.getExternalIdentifier())) {
            log.info("{} WR list item: {}",trackId,wr.getFriendlyIdStr());
            if (wr.getReactiveInspectorId() != null && wr.getReactiveInspectionDate() !=null) {
                log.info("{} inspector is present in the wr: {}",trackId,wr.getFriendlyIdStr());
                inspectWR(wr);
            }
            if (isInsideWithinThisTrigger(getAsseticTimeWithOffset(wr.getLastModified()))) {
                if (MurrindindiConstants.ASSETIC_WR_STATUS_REJECTED.equals(wr.getWorkRequestStatus()) ||
                        MurrindindiConstants.ASSETIC_WR_STATUS_CANCELLED.equals(wr.getWorkRequestStatus())) {
                    rejectedWR(wr);
                }
                if (wr.getSupportingInformationHistory() != null && !wr.getSupportingInformationHistory().isEmpty()) {
                    log.info("{} supporting information history is present in the wr: {}",trackId,wr.getFriendlyIdStr());
                    checkSupportInfo(wr);
                }
            } else {
                log.info("{} this is not inside the trigger: {}",trackId,wr.getFriendlyIdStr());
            }

        } else {
            log.info("{} this is not following authority id pattern: {}",trackId,wr.getExternalIdentifier());
        }

    }

    public void inspectWR(AsseticWorkRequestByExternalIdResponse.Resource wr) {
        log.info("{} processing inspect WR: {}",trackId,wr.getFriendlyIdStr());
        String executionId = trackId+"-"+ UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        Thread thread = new Thread(new AsseticWRInspectionProcessor(executionId,authorityToken,wr));
        thread.start();
        EventManager.wait(thread,trackId);
    }

    public void rejectedWR(AsseticWorkRequestByExternalIdResponse.Resource wr) {
        log.info("{} processing rejected WR: {}",trackId,wr.getFriendlyIdStr());
        String executionId = trackId+"-"+ UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        Thread thread = new Thread(new AsseticWRRejectProcessor(executionId,authorityToken,wr));
        thread.start();
        EventManager.wait(thread,trackId);
    }

    public boolean isInsideWithinThisTrigger(String createdDateTime) {

        log.info("{} Comparing the time created time: {}, lastRunTime: {}, nextRunTime: {}",trackId, createdDateTime, lastRunTime, nextRunTime);
        Instant inputTime = OffsetDateTime.parse(createdDateTime).toInstant();
        Instant startTime = Instant.parse(lastRunTime);
        Instant endTime = Instant.parse(nextRunTime);

        // Check if inputTime is within the range
        return !inputTime.isBefore(startTime) && !inputTime.isAfter(endTime);
    }

    public boolean isAuthorityId(String input) {

        String pattern = "^\\d{3}\\.\\d{4}\\.\\d{8}\\.\\d{3}$";
        return Pattern.matches(pattern, input);
    }

    public void checkSupportInfo(AsseticWorkRequestByExternalIdResponse.Resource wr) {
        for (AsseticWorkRequestByExternalIdResponse.SupportingInformation item : wr.getSupportingInformationHistory()) {
            if (!item.getDescription().startsWith(MurrindindiConstants.ASSETIC_SUPPORT_INFO_PREFIX_FROM_AUTHORITY)) {
                log.info("{} supporting information is inside the trigger: {}",trackId,item.getId());
                supportInfoWR(wr,item.getId(),item.getDescription(),item.getCreatedByDisplayName(),
                        item.getCreatedDateTime());
            } else {
                log.info("{} supporting information {} was created by integration on: {}. Hence ignore it.",trackId,item.getId(),wr.getFriendlyIdStr());
            }
        }
    }

    public void supportInfoWR(AsseticWorkRequestByExternalIdResponse.Resource wr, String infoId,
                              String info, String name, String dateTime) {

        log.info("{} processing support info {} WR: {}",trackId,infoId,wr.getFriendlyIdStr());
        String executionId = trackId+"-"+ UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        Thread thread = new Thread(new AsseticWRSupportInfoProcessor(executionId,authorityToken,wr,infoId,
                info,name,dateTime));
        thread.start();
        EventManager.wait(thread,trackId);
    }

    //This method is due to missing proper timezone in assetic data/time attribute is payload.
    //This was raised in https://symphony3.atlassian.net/browse/SG-216?focusedCommentId=20187
    public String getAsseticTimeWithOffset(String asseticTime) {
        LocalDateTime localDateTime = LocalDateTime.parse(asseticTime);
        ZonedDateTime melbourneTime = localDateTime.atZone(ZoneId.of("Australia/Melbourne"));
        return melbourneTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

}
