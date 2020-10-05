package com.doozycod.axs.CoverterPOJOToEntity;

import com.doozycod.axs.Database.Entities.ReasonEntity;
import com.doozycod.axs.POJO.ReasonWS;

public class ReasonWSToReasonEntity {

    public static ReasonEntity convert(ReasonWS reasonWS) {
        return new ReasonEntity(reasonWS.getReasonId(), reasonWS.getStatusId(), reasonWS.getReasonName(), reasonWS.getReasonRule());
    }
}
