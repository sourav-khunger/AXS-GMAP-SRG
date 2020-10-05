package com.doozycod.axs.POJO;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginResponse implements Serializable {

    @SerializedName(value = "status")
    private String status;

    @SerializedName(value = "token")
    private String token;

    @SerializedName(value = "driverInfo")
    private DriverInfo driverInfo;

    public LoginResponse() {

    }

    public LoginResponse(String status, String token, DriverInfo driverInfo) {
        this.status = status;
        this.token = token;
        this.driverInfo = driverInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public DriverInfo getDriverInfo() {
        return driverInfo;
    }

    public void setDriverInfo(DriverInfo driverInfo) {
        this.driverInfo = driverInfo;
    }
}
