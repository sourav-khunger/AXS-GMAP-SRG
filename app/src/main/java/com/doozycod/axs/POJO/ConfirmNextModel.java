
package com.doozycod.axs.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConfirmNextModel {

    @SerializedName("locationKey")
    @Expose
    private String locationKey;
    @SerializedName("batchId")
    @Expose
    private String batchId;

    public String getLocationKey() {
        return locationKey;
    }

    public void setLocationKey(String locationKey) {
        this.locationKey = locationKey;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

}
