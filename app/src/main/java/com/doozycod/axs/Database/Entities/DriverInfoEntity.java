package com.doozycod.axs.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.doozycod.axs.POJO.AllowedCompanyInfo;
import com.doozycod.axs.Utils.Converters;

import java.util.ArrayList;

@Entity(tableName = "driverInfoTable")
@TypeConverters({Converters.class})
public class DriverInfoEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "imei")
    private String imei;

    @ColumnInfo(name = "firstName")
    private String firstName;

    @ColumnInfo(name = "lastName")
    private String lastName;

    @ColumnInfo(name = "onDuty")
    private int onDuty;

    @ColumnInfo(name = "companyId")
    private int companyId;

    @ColumnInfo(name = "companyName")
    private String companyName;

    @ColumnInfo(name = "enableGPS")
    private int enableGPS;

    @ColumnInfo(name = "allowedCompanies")
    private ArrayList<AllowedCompanyInfo> listOfAllowedCompanyInfo;

    public DriverInfoEntity(@NonNull String imei, String firstName, String lastName, int onDuty, int companyId, String companyName, int enableGPS, ArrayList<AllowedCompanyInfo> listOfAllowedCompanyInfo) {
        this.imei = imei;
        this.firstName = firstName;
        this.lastName = lastName;
        this.onDuty = onDuty;
        this.companyId = companyId;
        this.companyName = companyName;
        this.enableGPS = enableGPS;
        this.listOfAllowedCompanyInfo=listOfAllowedCompanyInfo;
    }

    @NonNull
    public String getImei() {
        return imei;
    }

    public void setImei(@NonNull String imei) {
        this.imei = imei;
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
