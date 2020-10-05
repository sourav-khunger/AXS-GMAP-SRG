package com.doozycod.axs.CoverterPOJOToEntity;

import com.doozycod.axs.Database.Entities.DriverInfoEntity;
import com.doozycod.axs.POJO.DriverInfo;

public class DriverInfoToDriverInfoEntity {

    public static DriverInfoEntity convertDriverInfoToEntity(DriverInfo driverInfo){
        DriverInfoEntity driverInfoEntity = new DriverInfoEntity(driverInfo.getImei(),driverInfo.getFirstName(),driverInfo.getLastName(),driverInfo.getOnDuty(),
                driverInfo.getCompanyId(),driverInfo.getCompanyName(),driverInfo.getEnableGPS(), driverInfo.getListOfAllowedCompanyInfo());
        return driverInfoEntity;
    }
}
