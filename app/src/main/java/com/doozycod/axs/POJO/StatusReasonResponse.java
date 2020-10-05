package com.doozycod.axs.POJO;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class StatusReasonResponse implements Serializable {

    @SerializedName(value = "statusList")
    private ArrayList<StatusWS> statusList;

    @SerializedName(value = "reasonList")
    private ArrayList<ReasonWS> reasonList;

    public ArrayList<StatusWS> getStatusList() {
        return statusList;
    }

    public void setStatusList(ArrayList<StatusWS> statusList) {
        this.statusList = statusList;
    }

    public ArrayList<ReasonWS> getReasonList() {
        return reasonList;
    }

    public void setReasonList(ArrayList<ReasonWS> reasonList) {
        this.reasonList = reasonList;
    }
}
