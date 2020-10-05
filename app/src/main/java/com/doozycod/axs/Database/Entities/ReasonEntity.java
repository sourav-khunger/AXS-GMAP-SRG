package com.doozycod.axs.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reasonTable")
public class ReasonEntity {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "reasonId")
    private int reasonId;

    @ColumnInfo(name = "statusId")
    private int statusId;

    @ColumnInfo(name = "reasonName")
    private String reasonName;

    @ColumnInfo(name = "reasonRule")
    private String reasonRule;


    public ReasonEntity(@NonNull int reasonId, @NonNull int statusId, String reasonName, String reasonRule) {
        this.reasonId = reasonId;
        this.statusId = statusId;
        this.reasonName = reasonName;
        this.reasonRule = reasonRule;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReasonId() {
        return reasonId;
    }

    public void setReasonId(int reasonId) {
        this.reasonId = reasonId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getReasonName() {
        return reasonName;
    }

    public void setReasonName(String reasonName) {
        this.reasonName = reasonName;
    }

    public String getReasonRule() {
        return reasonRule;
    }

    public void setReasonRule(String reasonRule) {
        this.reasonRule = reasonRule;
    }
}
