package com.doozycod.axs.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "statusTable")
public class StatusEntity {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "statusId")
    private int statusId;

    @ColumnInfo(name = "statusName")
    private String statusName;

    @ColumnInfo(name = "shipmentType")
    private String shipmentType;

    @ColumnInfo(name = "statusRule")
    private String statusRule;

    @ColumnInfo(name = "isException")
    private int isException;

    public StatusEntity(int statusId, String statusName, String shipmentType, String statusRule, int isException) {
        this.statusId = statusId;
        this.statusName = statusName;
        this.shipmentType = shipmentType;
        this.statusRule = statusRule;
        this.isException = isException;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getIsException() {
        return isException;
    }

    public void setIsException(int isException) {
        this.isException = isException;
    }
}
