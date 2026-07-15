package org.symphony3.smartglue.integration.murrindindi.data;

public class AuthorityTaskManagingData {
    private String trackId;
    private String formattedAccount;
    private String authorityToken;
    private boolean isRetry;
    private boolean isEnd;
    private String prefix;
    private String infoId;
    private String code;
    private String friendlyIdStr;
    private String taskNote;
    private boolean isACTN;
    private String message;
    private String taskId;
    private String supportInfoName;
    private String supportInfoDate;

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getFormattedAccount() {
        return formattedAccount;
    }

    public void setFormattedAccount(String formattedAccount) {
        this.formattedAccount = formattedAccount;
    }

    public String getAuthorityToken() {
        return authorityToken;
    }

    public void setAuthorityToken(String authorityToken) {
        this.authorityToken = authorityToken;
    }

    public boolean isRetry() {
        return isRetry;
    }

    public void setRetry(boolean retry) {
        this.isRetry = retry;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFriendlyIdStr() {
        return friendlyIdStr;
    }

    public void setFriendlyIdStr(String friendlyIdStr) {
        this.friendlyIdStr = friendlyIdStr;
    }

    public String getTaskNote() {
        return taskNote;
    }

    public void setTaskNote(String taskNote) {
        this.taskNote = taskNote;
    }

    public boolean isACTN() {
        return isACTN;
    }

    public void setACTN(boolean actn) {
        isACTN = actn;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSupportInfoName() {
        return supportInfoName;
    }

    public void setSupportInfoName(String supportInfoName) {
        this.supportInfoName = supportInfoName;
    }

    public String getSupportInfoDate() {
        return supportInfoDate;
    }

    public void setSupportInfoDate(String supportInfoDate) {
        this.supportInfoDate = supportInfoDate;
    }
}
