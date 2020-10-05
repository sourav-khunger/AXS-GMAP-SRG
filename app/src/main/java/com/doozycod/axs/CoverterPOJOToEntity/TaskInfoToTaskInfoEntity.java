package com.doozycod.axs.CoverterPOJOToEntity;

import com.doozycod.axs.Database.Entities.TaskInfoEntity;
import com.doozycod.axs.POJO.TaskInfo;

public class  TaskInfoToTaskInfoEntity {

    public static TaskInfoEntity convertTaskInfoToTaskInfoEntity(TaskInfo taskInfo){
        TaskInfoEntity taskInfoEntity = new TaskInfoEntity(taskInfo.getTaskId(),taskInfo.getTaskType(),taskInfo.getAppId(),taskInfo.getName(),taskInfo.getAddress(),taskInfo.getApptNo(),
                taskInfo.getPostalCode(),taskInfo.getCity(),taskInfo.getEmail(),taskInfo.getPhoneNo(),taskInfo.getLatitude(),taskInfo.getLongitude(),taskInfo.getBarcode(),taskInfo.getAgent(),taskInfo.getManifest(),
                taskInfo.getReffNo(),taskInfo.getQuantity(),taskInfo.getWeight(),taskInfo.getAmount(),taskInfo.getCurrency(),taskInfo.getServiceLevel(),taskInfo.getInstructions(),taskInfo.getWorkStatus(),
                taskInfo.getArrivalTime(),taskInfo.getCompleteTime(),taskInfo.getDataId(),taskInfo.getStopId(),taskInfo.getDataEntered(),taskInfo.getStatusId(),taskInfo.getReasonId(),taskInfo.getQtyEntered(),taskInfo.getWeightEntered(),
                taskInfo.getImageTaken(),taskInfo.getImagePath(),taskInfo.getAreaType(),taskInfo.getDriverComment(),taskInfo.getDriverNotice(),taskInfo.getCashCollect(),taskInfo.getConsolicatedId(),taskInfo.getWaitingTime(),taskInfo.getCod(),
                taskInfo.getDisAmt(),taskInfo.getAccessorial(),taskInfo.getCodCurrency(),taskInfo.getMileage(), taskInfo.getSeqNo(),taskInfo.getBatchId(),taskInfo.getRunNo());
        return taskInfoEntity;
    }
}
