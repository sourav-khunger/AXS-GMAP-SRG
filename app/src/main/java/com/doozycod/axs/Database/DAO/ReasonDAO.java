package com.doozycod.axs.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.doozycod.axs.Database.Entities.ReasonEntity;

import java.util.List;

@Dao
public interface ReasonDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void  insert (ReasonEntity reasonEntity);


    @Query("Select * from reasonTable where statusId = :statusId ")
    LiveData<List<ReasonEntity>> getReasonList(int statusId);

    @Query("Select * from reasonTable where reasonId = :reasonId ")
    ReasonEntity getReason(int reasonId);

    @Query("DELETE FROM reasonTable")
    void deleteAllReasons();
}
