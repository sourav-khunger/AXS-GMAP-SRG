package com.doozycod.axs.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.doozycod.axs.Database.Entities.StatusEntity;

import java.util.List;

@Dao
public interface StatusDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert (StatusEntity statusEntity);

    @Query("Select * from statusTable where shipmentType = :shipmentType and isException = :isEx order by id")
    LiveData<List<StatusEntity>> getStatusList(String shipmentType, int isEx);

    @Query("Select * from statusTable where statusId = :statusId ")
    StatusEntity getStatus(int statusId);

    @Query("DELETE FROM statusTable")
    void deleteAllStatuses();
}
