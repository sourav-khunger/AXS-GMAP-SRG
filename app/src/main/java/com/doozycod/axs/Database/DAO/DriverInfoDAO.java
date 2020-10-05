package com.doozycod.axs.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.doozycod.axs.Database.Entities.DriverInfoEntity;

import java.util.List;

@Dao
public interface DriverInfoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void  insert (DriverInfoEntity driverInfoEntity);

    @Query("Select * from driverInfoTable")
    LiveData<List<DriverInfoEntity>> getDriverInfo();

    @Query("Select * from driverInfoTable ")
    List<DriverInfoEntity> getSingleDriverInfo();

    @Delete
    void deleteDriverInfos(DriverInfoEntity... driverInfoEntities);

    @Query("DELETE FROM driverInfoTable")
    void deleteAll();
}
