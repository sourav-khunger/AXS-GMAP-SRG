package com.doozycod.axs.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.doozycod.axs.Database.Entities.TaskInfoEntity;
import com.doozycod.axs.POJO.TaskInfoGroupByLocationKey;

import java.util.List;

@Dao
public interface TaskInfoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TaskInfoEntity... taskInfoEntity);


    @Query("Select * from taskInfoTable")
    LiveData<List<TaskInfoEntity>> getTaskInfoList();


    @Query("Select * from taskInfoTable")
    List<TaskInfoEntity> getTaskInfoList1();

    @Query("Select * from taskInfoTable where taskId= :taskId")
    TaskInfoEntity getTaskInfo(String taskId);

    @Query("Select address, postalCode, city, locationKey, COUNT(*) as groupCount, latitude, longitude, MAX(arrivalTime) as arrivalTime  from taskInfoTable where batchId= :batchId GROUP BY locationKey ORDER BY seqNo ")
//    @Query("Select * from taskInfoTable where )
    LiveData<List<TaskInfoGroupByLocationKey>> getTaskInfoByBatchId(String batchId);

    @Delete
    void deleteTaskInfos(TaskInfoEntity... taskInfoEntities);

    @Update
    void updateTaskInfo(TaskInfoEntity... taskInfoEntitis);

    @Query("Select address, postalCode, city, locationKey, COUNT(*) as groupCount, latitude, longitude, MAX(arrivalTime) as arrivalTime  from taskInfoTable GROUP BY locationKey ORDER BY seqNo ")
    LiveData<List<TaskInfoGroupByLocationKey>> getTaskInfoGroupByLocationKeys();

    @Query("Select address, postalCode, city, locationKey, COUNT(*) as groupCount, latitude, longitude, MAX(arrivalTime) as arrivalTime from taskInfoTable where workStatus= :workStatus GROUP BY locationKey")
    LiveData<List<TaskInfoGroupByLocationKey>> getTaskInfoSearchByWorkStatusGroupByLocationKeys(String workStatus);

    @Query("Select * from taskInfoTable where locationKey= :locationKey")
    LiveData<List<TaskInfoEntity>> getTaskInfoByLocationKey(String locationKey);

    @Query("Select * from taskInfoTable where recordStatus= :recordStatus")
    List<TaskInfoEntity> getTaskInfoByRecordStatus(int recordStatus);

    @Query("Select * from taskInfoTable where locationKey= :locationKey AND workStatus= :workStatus")
    List<TaskInfoEntity> getTaskInfoCompleted(String locationKey, String workStatus);

    @Query("Select * from taskInfoTable limit 1")
    List<TaskInfoEntity> getTaskInfoLimit1();

    @Query("DELETE FROM taskInfoTable")
    void deleteAll();


    @Query("UPDATE taskInfoTable SET arrivalTime = :arrivalTime where locationKey = :locationKey ")
    void updateLocation(String locationKey, String arrivalTime);
}
