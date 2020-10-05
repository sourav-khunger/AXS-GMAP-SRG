package com.doozycod.axs.CoverterPOJOToEntity;


import com.doozycod.axs.Database.Entities.StatusEntity;
import com.doozycod.axs.POJO.StatusWS;

public class StatusWSTOStatusEntity {
    public static StatusEntity convert(StatusWS statusWS) {
        return new StatusEntity(statusWS.getStatusId(),statusWS.getStatusName(),statusWS.getShipmentType(),statusWS.getStatusRule(),statusWS.getIsException());
    }
}
