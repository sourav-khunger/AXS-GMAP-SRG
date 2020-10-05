
package com.doozycod.axs.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RunInfo {


    @SerializedName(value = "batchId")
    @Expose
    private String batchId;
    @SerializedName(value = "parcelCounts")
    @Expose
    private int parcelCounts;
    @SerializedName(value = "routeStarted")
    @Expose
    private int routeStarted;
    @SerializedName(value = "runNo")
    @Expose
    private int runNo;

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public int getParcelCounts() {
        return parcelCounts;
    }

    public void setParcelCounts(int parcelCounts) {
        this.parcelCounts = parcelCounts;
    }

    public int getRouteStarted() {
        return routeStarted;
    }

    public void setRouteStarted(int routeStarted) {
        this.routeStarted = routeStarted;
    }

    public int getRunNo() {
        return runNo;
    }

    public void setRunNo(int runNo) {
        this.runNo = runNo;
    }


    @Override
    public String toString() {
        return "RunInfo{" +
                "batchId='" + batchId + '\'' +
                ", parcelCounts=" + parcelCounts +
                ", routeStarted=" + routeStarted +
                ", runNo=" + runNo +
                '}';
    }
}
