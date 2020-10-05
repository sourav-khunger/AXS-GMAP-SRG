package com.doozycod.axs.CoverterPOJOToEntity;

import com.doozycod.axs.Database.Entities.RunInfoEntity;
import com.doozycod.axs.POJO.RunInfo;

public class RunInfoToRunInfoEntity {

    public static RunInfoEntity convertRunInfoToRunInfoEntity(RunInfo runInfo) {
        RunInfoEntity runInfoEntity = new RunInfoEntity(runInfo.getBatchId(), runInfo.getParcelCounts(), runInfo.getRouteStarted(), runInfo.getRunNo());
        return runInfoEntity;

    }

}