package com.doozycod.axs.POJO;

import com.google.gson.annotations.SerializedName;

public class StatusWS {

    @SerializedName(value = "statusId")
    private int statusId;

    @SerializedName(value = "statusName")
    private String statusName;

    @SerializedName(value = "shipmentType")
    private String shipmentType;

    @SerializedName(value = "statusRule")
    private String statusRule;

    @SerializedName(value = "isException")
    private short isException;

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(String shipmentType) {
        this.shipmentType = shipmentType;
    }

    public String getStatusRule() {
        return statusRule;
    }

    public void setStatusRule(String statusRule) {
        this.statusRule = statusRule;
    }

    public short getIsException() {
        return isException;
    }

    public void setException(short exception) {
        isException = exception;
    }
}
