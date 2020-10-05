
package com.doozycod.axs.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BatchRoutePath {

    @SerializedName("dcName")
    @Expose
    private String dcName;
    @SerializedName("dcLat")
    @Expose
    private double dcLat;
    @SerializedName("dcLon")
    @Expose
    private double dcLon;
    @SerializedName("route")
    @Expose
    private String route;

    public String getDcName() {
        return dcName;
    }

    public void setDcName(String dcName) {
        this.dcName = dcName;
    }

    public double getDcLat() {
        return dcLat;
    }

    public void setDcLat(double dcLat) {
        this.dcLat = dcLat;
    }

    public double getDcLon() {
        return dcLon;
    }

    public void setDcLon(double dcLon) {
        this.dcLon = dcLon;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

}
