package com.doozycod.axs.POJO;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class DriverInfo implements Serializable {

    @SerializedName(value = "firstName")
    private String firstName;

    @SerializedName(value = "lastName")
    private String lastName;

    @SerializedName(value = "onDuty")
    private int onDuty;

    @SerializedName(value = "companyId")
    private int companyId;

    @SerializedName(value = "companyName")
    private String companyName;


    @SerializedName(value = "imei")
    private String imei;

    @SerializedName(value = "enableGPS")
    private int enableGPS;

    @SerializedName("allowedCompanies")
    private ArrayList<AllowedCompanyInfo> listOfAllowedCompanyInfo;

    public DriverInfo() {

    }

    public DriverInfo(String firstName, String lastName, int onDuty, int companyId, String companyName, String imei, int enableGPS, ArrayList<AllowedCompanyInfo> listOfAllowedCompanyInfo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.onDuty = onDuty;
        this.companyId = companyId;
        this.companyName = companyName;
        this.imei = imei;
        this.enableGPS = enableGPS;
        this.listOfAllowedCompanyInfo = listOfAllowedCompanyInfo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getOnDuty() {
        return onDuty;
    }

    public void setOnDuty(int onDuty) {
        this.onDuty = onDuty;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public int getEnableGPS() {
        return enableGPS;
    }

    public void setEnableGPS(int enableGPS) {
        this.enableGPS = enableGPS;
    }

    public ArrayList<AllowedCompanyInfo> getListOfAllowedCompanyInfo() {
        return listOfAllowedCompanyInfo;
    }

    public void setListOfAllowedCompanyInfo(ArrayList<AllowedCompanyInfo> listOfAllowedCompanyInfo) {
        this.listOfAllowedCompanyInfo = listOfAllowedCompanyInfo;
    }



}
