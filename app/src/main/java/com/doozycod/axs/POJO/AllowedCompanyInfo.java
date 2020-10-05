package com.doozycod.axs.POJO;

import java.io.Serializable;
import com.google.gson.annotations.SerializedName;

public class AllowedCompanyInfo implements Serializable {

    @SerializedName(value = "companyId")
    private int companyId;

    @SerializedName(value = "companyName")
    private String companyName;

    public AllowedCompanyInfo() {

    }

    public AllowedCompanyInfo(int companyId, String companyName) {
        this.companyId = companyId;
        this.companyName = companyName;
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
}
