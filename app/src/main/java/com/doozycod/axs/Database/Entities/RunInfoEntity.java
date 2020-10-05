package com.doozycod.axs.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.doozycod.axs.Utils.TimeStampConverter;

import java.io.Serializable;

@Entity(tableName = "runInfoTable")
@TypeConverters({TimeStampConverter.class})
public class RunInfoEntity implements Serializable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "batchId")
    private String batchId;

    @ColumnInfo(name = "parcelCounts")
    private int parcelCounts;

    @ColumnInfo(name = "routeStarted")
    private int routeStarted;

    @ColumnInfo(name = "runNo")
    private int runNo;

    @NonNull
    public String getBatchId() {
        return batchId;
    }

    public RunInfoEntity(@NonNull String batchId, int parcelCounts, int routeStarted, int runNo) {
        this.batchId = batchId;
        this.parcelCounts = parcelCounts;
        this.routeStarted = routeStarted;
        this.runNo = runNo;
    }

    public void setBatchId(@NonNull String batchId) {
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
        return "RunInfoEntity{" +
                "batchId='" + batchId + '\'' +
                ", parcelCounts='" + parcelCounts + '\'' +
                ", routeStarted=" + routeStarted +
                ", runNo=" + runNo +
                '}';
    }
}
