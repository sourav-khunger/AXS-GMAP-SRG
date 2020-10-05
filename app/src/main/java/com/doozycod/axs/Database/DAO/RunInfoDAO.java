package com.doozycod.axs.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.doozycod.axs.Database.Entities.RunInfoEntity;

import java.util.List;

@Dao
public interface RunInfoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RunInfoEntity... runInfoEntity);

    @Query("Select * from runInfoTable")
    LiveData<List<RunInfoEntity>> getRunInfoList();

    @Query("DELETE FROM runInfoTable")
    void deleteAllRunList();
}
