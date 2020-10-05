package com.doozycod.axs.POJO;

import com.google.gson.annotations.SerializedName;

public class ReasonWS {

    @SerializedName(value = "reasonId")
    private int reasonId;

    @SerializedName(value = "reasonName")
    private String reasonName;

    @SerializedName(value = "statusId")
    private int statusId;

    @SerializedName(value = "reasonRule")
    private String reasonRule;


    public int getReasonId() {
        return reasonId;
    }

    public void setReasonId(int reasonId) {
        this.reasonId = reasonId;
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getReasonRule() {
        return reasonRule;
    }

    public void setReasonRule(String reasonRule) {
        this.reasonRule = reasonRule;
    }
}
