package com.doozycod.axs.POJO;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TaskInfoUpdate implements Serializable {
    @SerializedName(value = "status")
    private String status;

    @SerializedName(value = "taskId")
    private long taskId;

    @SerializedName(value = "dataId")
    private long dataId;

    @SerializedName(value = "stopId")
    private long stopId;

    public TaskInfoUpdate() {
    }

    public TaskInfoUpdate(String status, long taskId, long dataId, long stopId) {
        this.status = status;
        this.taskId = taskId;
        this.dataId = dataId;
        this.stopId = stopId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public long getDataId() {
        return dataId;
    }

    public void setDataId(long dataId) {
        this.dataId = dataId;
    }

    public long getStopId() {
        return stopId;
    }

    public void setStopId(long stopId) {
        this.stopId = stopId;
    }
}
