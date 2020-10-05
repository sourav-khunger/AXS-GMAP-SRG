package com.doozycod.axs.POJO;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DriverLogDutyResponse implements Serializable {

    @SerializedName(value = "status")
    private String status;

    @SerializedName(value = "msg")
    private String msg;

    @SerializedName(value = "companyId")
    private String companyId;

    public DriverLogDutyResponse() {

    }

    public DriverLogDutyResponse(String status, String msg, String companyId) {
        this.status = status;
        this.msg = msg;
        this.companyId = companyId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
