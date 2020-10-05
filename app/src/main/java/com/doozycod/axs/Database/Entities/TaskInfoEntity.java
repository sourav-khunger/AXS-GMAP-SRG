package com.doozycod.axs.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.doozycod.axs.Utils.TimeStampConverter;

import java.io.Serializable;

@Entity(tableName = "taskInfoTable")
@TypeConverters({TimeStampConverter.class})
public class TaskInfoEntity implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "taskId")
    private long taskId;

    @ColumnInfo(name = "taskType")
    private String taskType;

    @ColumnInfo(name = "appId")
    private int appId;

    @ColumnInfo(name = "seqNo")
    private int seqNo;


    @ColumnInfo(name = "batchId")
    private String batchId;

    @ColumnInfo(name = "runNo")
    private int runNo;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "apptNo")
    private String apptNo;

    @ColumnInfo(name = "postalCode")
    private String postalCode;

    @ColumnInfo(name = "city")
    public String city;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "phoneNo")
    private String phoneNo;

    @ColumnInfo(name = "latitude")
    private String latitude;

    @ColumnInfo(name = "longitude")
    private String longitude;

    @ColumnInfo(name = "barcode")
    private String barcode;

    @ColumnInfo(name = "agent")
    private String agent;

    @ColumnInfo(name = "manifest")
    private String manifest;

    @ColumnInfo(name = "reffNo")
    private String reffNo;

    @ColumnInfo(name = "quantity")
    private int quantity;

    @ColumnInfo(name = "weight")
    private String weight;

    @ColumnInfo(name = "amount")
    private String amount;

    @ColumnInfo(name = "currency")
    private String currency;

    @ColumnInfo(name = "serviceLevel")
    private String serviceLevel;

    @ColumnInfo(name = "instructions")
    private String instructions;

    @ColumnInfo(name = "workStatus")
    private String workStatus;

    @ColumnInfo(name = "arrivalTime")
    private String arrivalTime;

    @ColumnInfo(name = "completeTime")
    private String completeTime;

    @ColumnInfo(name = "dataId")
    private long dataId;

    @ColumnInfo(name = "stopId")
    private long stopId;

    @ColumnInfo(name = "dataEntered")
    private String dataEntered;

    @ColumnInfo(name = "statusId")
    private int statusId;

    @ColumnInfo(name = "reasonId")
    private int reasonId;

    @ColumnInfo(name = "qtyEntered")
    private int qtyEntered;

    @ColumnInfo(name = "weightEntered")
    private double weightEntered;

    @ColumnInfo(name = "imageTaken")
    private int imageTaken;

    @ColumnInfo(name = "imagePath")
    private String imagePath;

    @ColumnInfo(name = "areaType")
    private String areaType;

    @ColumnInfo(name = "driverComment")
    private String driverComment;

    @ColumnInfo(name = "driverNotice")
    private String driverNotice;

    @ColumnInfo(name = "cashCollect")
    private String cashCollect;

    @ColumnInfo(name = "consolicatedId")
    private int consolicatedId;

    @ColumnInfo(name = "waitingTime")
    private double waitingTime;

    @ColumnInfo(name = "cod")
    private double cod;

    @ColumnInfo(name = "disAmt")
    private String disAmt;

    @ColumnInfo(name = "accessorial")
    private String accessorial;

    @ColumnInfo(name = "codCurrency")
    private String codCurrency;

    @ColumnInfo(name = "mileage")
    private double mileage;

    @ColumnInfo(name = "locationKey")
    private String locationKey;

    @ColumnInfo(name = "recordStatus")
    private int recordStatus;

    @ColumnInfo(name = "signature")
    private String signature;

    @ColumnInfo(name = "signatureName")
    private String signatureName;

    @ColumnInfo(name = "signatureTime")
    private String signatureTime;


    public TaskInfoEntity(long taskId, String taskType, int appId, String name, String address, String apptNo, String postalCode, String city, String email, String phoneNo, String latitude, String longitude,
                          String barcode, String agent, String manifest, String reffNo, int quantity, String weight, String amount, String currency, String serviceLevel, String instructions, String workStatus,
                          String arrivalTime, String completeTime, long dataId, long stopId, String dataEntered, int statusId, int reasonId, int qtyEntered, double weightEntered, int imageTaken,
                          String imagePath, String areaType, String driverComment, String driverNotice, String cashCollect, int consolicatedId, double waitingTime, double cod, String disAmt, String accessorial, String codCurrency, double mileage,
                          int seqNo, String batchId, int runNo) {
        this.taskId = taskId;
        this.taskType = taskType;
        this.appId = appId;
        this.seqNo = seqNo;
        this.name = name;
        this.address = address;
        this.apptNo = apptNo;
        this.postalCode = postalCode;
        this.city = city;
        this.email = email;
        this.phoneNo = phoneNo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.barcode = barcode;
        this.agent = agent;
        this.manifest = manifest;
        this.reffNo = reffNo;
        this.quantity = quantity;
        this.weight = weight;
        this.amount = amount;
        this.currency = currency;
        this.serviceLevel = serviceLevel;
        this.instructions = instructions;
        this.workStatus = workStatus;
        this.arrivalTime = arrivalTime;
        this.completeTime = completeTime;
        this.dataId = dataId;
        this.stopId = stopId;
        this.dataEntered = dataEntered;
        this.statusId = statusId;

        this.reasonId = reasonId;
        this.qtyEntered = qtyEntered;
        this.weightEntered = weightEntered;
        this.imageTaken = imageTaken;
        this.imagePath = imagePath;
        this.areaType = areaType;
        this.driverComment = driverComment;
        this.driverNotice = driverNotice;
        this.cashCollect = cashCollect;
        this.consolicatedId = consolicatedId;
        this.waitingTime = waitingTime;
        this.cod = cod;
        this.disAmt = disAmt;
        this.accessorial = accessorial;
        this.codCurrency = codCurrency;
        this.mileage = mileage;
        if (latitude != null && latitude != null) {
            if (!latitude.substring(0, 1).equals("-")) latitude = "+" + latitude;
            if (!longitude.substring(0, 1).equals("-")) longitude = "+" + longitude;
            this.locationKey = latitude.substring(0, 8) + longitude.substring(0, 8);
        }

        this.recordStatus = 0;
        this.batchId = batchId;
        this.runNo = runNo;
    }

    public int getRunNo() {
        return runNo;
    }

    public void setRunNo(int runNo) {
        this.runNo = runNo;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getName() {
        return name;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getApptNo() {
        return apptNo;
    }

    public void setApptNo(String apptNo) {
        this.apptNo = apptNo;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getManifest() {
        return manifest;
    }

    public void setManifest(String manifest) {
        this.manifest = manifest;
    }

    public String getReffNo() {
        return reffNo;
    }

    public void setReffNo(String reffNo) {
        this.reffNo = reffNo;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getServiceLevel() {
        return serviceLevel;
    }

    public void setServiceLevel(String serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public long getDataId() {
        return dataId;
    }

    public void setDataId(long dataId) {
        this.dataId = dataId;
    }

    public String getDataEntered() {
        return dataEntered;
    }

    public void setDataEntered(String dataEntered) {
        this.dataEntered = dataEntered;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getReasonId() {
        return reasonId;
    }

    public void setReasonId(int reasonId) {
        this.reasonId = reasonId;
    }

    public int getQtyEntered() {
        return qtyEntered;
    }

    public void setQtyEntered(int qtyEntered) {
        this.qtyEntered = qtyEntered;
    }

    public double getWeightEntered() {
        return weightEntered;
    }

    public void setWeightEntered(double weightEntered) {
        this.weightEntered = weightEntered;
    }

    public int getImageTaken() {
        return imageTaken;
    }

    public void setImageTaken(int imageTaken) {
        this.imageTaken = imageTaken;
    }

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    public String getDriverComment() {
        return driverComment;
    }

    public void setDriverComment(String driverComment) {
        this.driverComment = driverComment;
    }

    public String getDriverNotice() {
        return driverNotice;
    }

    public void setDriverNotice(String driverNotice) {
        this.driverNotice = driverNotice;
    }

    public String getCashCollect() {
        return cashCollect;
    }

    public void setCashCollect(String cashCollect) {
        this.cashCollect = cashCollect;
    }

    public int getConsolicatedId() {
        return consolicatedId;
    }

    public void setConsolicatedId(int consolicatedId) {
        this.consolicatedId = consolicatedId;
    }

    public double getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(double waitingTime) {
        this.waitingTime = waitingTime;
    }

    public double getCod() {
        return cod;
    }

    public void setCod(double cod) {
        this.cod = cod;
    }

    public String getDisAmt() {
        return disAmt;
    }

    public void setDisAmt(String disAmt) {
        this.disAmt = disAmt;
    }

    public String getAccessorial() {
        return accessorial;
    }

    public void setAccessorial(String accessorial) {
        this.accessorial = accessorial;
    }

    public String getCodCurrency() {
        return codCurrency;
    }

    public void setCodCurrency(String codCurrency) {
        this.codCurrency = codCurrency;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public String getLocationKey() {
        return locationKey;
    }

    public void setLocationKey(String locationKey) {
        this.locationKey = locationKey;
    }

    public long getStopId() {
        return stopId;
    }

    public void setStopId(long stopId) {
        this.stopId = stopId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(int recordStatus) {
        this.recordStatus = recordStatus;
    }

    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSignatureName() {
        return signatureName;
    }

    public void setSignatureName(String signatureName) {
        this.signatureName = signatureName;
    }

    public String getSignatureTime() {
        return signatureTime;
    }

    public void setSignatureTime(String signatureTime) {
        this.signatureTime = signatureTime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "TaskInfoEntity{" +
                "taskId=" + taskId +
                ", taskType='" + taskType + '\'' +
                ", appId=" + appId +
                ", seqNo=" + seqNo +
                ", batchId=" + batchId +
                ", runNo=" + runNo +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", apptNo='" + apptNo + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", email='" + email + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", barcode='" + barcode + '\'' +
                ", agent='" + agent + '\'' +
                ", manifest='" + manifest + '\'' +
                ", reffNo='" + reffNo + '\'' +
                ", quantity=" + quantity +
                ", weight='" + weight + '\'' +
                ", amount='" + amount + '\'' +
                ", currency='" + currency + '\'' +
                ", serviceLevel='" + serviceLevel + '\'' +
                ", instructions='" + instructions + '\'' +
                ", workStatus='" + workStatus + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", completeTime='" + completeTime + '\'' +
                ", dataId=" + dataId +
                ", stopId=" + stopId +
                ", dataEntered='" + dataEntered + '\'' +
                ", statusId='" + statusId + '\'' +
                ", reasonId=" + reasonId +
                ", qtyEntered=" + qtyEntered +
                ", weightEntered=" + weightEntered +
                ", imageTaken=" + imageTaken +
                ", imagePath='" + imagePath + '\'' +
                ", areaType='" + areaType + '\'' +
                ", driverComment='" + driverComment + '\'' +
                ", driverNotice='" + driverNotice + '\'' +
                ", cashCollect='" + cashCollect + '\'' +
                ", consolicatedId=" + consolicatedId +
                ", waitingTime=" + waitingTime +
                ", cod=" + cod +
                ", disAmt='" + disAmt + '\'' +
                ", accessorial='" + accessorial + '\'' +
                ", codCurrency='" + codCurrency + '\'' +
                ", mileage=" + mileage +
                ", locationKey='" + locationKey + '\'' +
                ", recordStatus=" + recordStatus +
                '}';
    }
}

