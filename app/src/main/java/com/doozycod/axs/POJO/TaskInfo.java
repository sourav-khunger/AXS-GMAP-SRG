package com.doozycod.axs.POJO;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TaskInfo implements Serializable {

    @SerializedName(value = "taskId")
    private long taskId;

    @SerializedName(value = "taskType")
    private String taskType;

    @SerializedName(value = "appId")
    private int appId;

    @SerializedName(value = "seqNo")
    private int seqNo;

    @SerializedName(value = "batchId")
    private String batchId;
    @SerializedName(value = "runNo")
    private int runNo;

    @SerializedName(value = "name")
    private String name;

    @SerializedName(value = "address")
    private String address;

    @SerializedName(value = "apptNo")
    private String apptNo;

    @SerializedName(value = "postalCode")
    private String postalCode;

    @SerializedName(value = "city")
    public String city;

    @SerializedName(value = "email")
    private String email;

    @SerializedName(value = "phoneNo")
    private String phoneNo;

    @SerializedName(value = "latitude")
    private String latitude;

    @SerializedName(value = "longitude")
    private String longitude;

    @SerializedName(value = "barcode")
    private String barcode;

    @SerializedName(value = "agent")
    private String agent;

    @SerializedName(value = "manifest")
    private String manifest;

    @SerializedName(value = "reffNo")
    private String reffNo;

    @SerializedName(value = "quantity")
    private int quantity;

    @SerializedName(value = "weight")
    private String weight;

    @SerializedName(value = "amount")
    private String amount;

    @SerializedName(value = "currency")
    private String currency;

    @SerializedName(value = "serviceLevel")
    private String serviceLevel;

    @SerializedName(value = "instructions")
    private String instructions;

    @SerializedName(value = "workStatus")
    private String workStatus;

    @SerializedName(value = "arrivalTime")
    private String arrivalTime;

    @SerializedName(value = "completeTime")
    private String completeTime;

    @SerializedName(value = "dataId")
    private long dataId;

    @SerializedName(value = "stopId")
    private long stopId;

    @SerializedName(value = "dataEntered")
    private String dataEntered;

    @SerializedName(value = "statusId")
    private int statusId;

    @SerializedName(value = "reasonId")
    private int reasonId;

    @SerializedName(value = "qtyEntered")
    private int qtyEntered;

    @SerializedName(value = "weightEntered")
    private double weightEntered;

    @SerializedName(value = "taskIimageTakend")
    private int imageTaken;

    @SerializedName(value = "imagePath")
    private String imagePath;

    @SerializedName(value = "areaType")
    private String areaType;

    @SerializedName(value = "driverComment")
    private String driverComment;

    @SerializedName(value = "driverNotice")
    private String driverNotice;

    @SerializedName(value = "cashCollect")
    private String cashCollect;

    @SerializedName(value = "consolicatedId")
    private int consolicatedId;

    @SerializedName(value = "waitingTime")
    private double waitingTime;

    @SerializedName(value = "cod")
    private double cod;

    @SerializedName(value = "disAmt")
    private String disAmt;

    @SerializedName(value = "codAmt")
    private String accessorial;

    @SerializedName(value = "codCurrency")
    private String codCurrency;

    @SerializedName(value = "mileage")
    private double mileage;


    @SerializedName(value = "signature")
    private String signature;

    @SerializedName(value = "signatureName")
    private String signatureName;

    @SerializedName(value = "signatureTime")
    private String signatureTime;


    public TaskInfo() {

    }


    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
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

    public int getRunNo() {
        return runNo;
    }

    public void setRunNo(int runNo) {
        this.runNo = runNo;
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
}
