package com.doozycod.axs.Database.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.doozycod.axs.Database.DAO.DriverInfoDAO;

import com.doozycod.axs.Database.Database;
import com.doozycod.axs.Database.Entities.DriverInfoEntity;

import java.util.List;

public class DriverInfoRepository {

    private DriverInfoDAO driverInfoDAO;
    private LiveData<List<DriverInfoEntity>> driverInfos;

    public DriverInfoRepository(Application application){
        Database db = Database.getDatabase(application);
        driverInfoDAO = db.driverInfoDAO();
        driverInfos = driverInfoDAO.getDriverInfo();
    }

    public LiveData<List<DriverInfoEntity>> getDriverInfos(){
        return driverInfos;
    }

    public List<DriverInfoEntity> getDriverInfo(){
        return driverInfoDAO.getSingleDriverInfo();
    }

    public void insert (final DriverInfoEntity driverInfoEntity){
        Database.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                driverInfoDAO.insert(driverInfoEntity);
            }
        });
    }


    public void deleteAll (){
        Database.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                driverInfoDAO.deleteAll();
            }
        });
    }
}
