package com.doozycod.axs.Database.Repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.doozycod.axs.Database.DAO.RunInfoDAO;
import com.doozycod.axs.Database.DAO.TaskInfoDAO;
import com.doozycod.axs.Database.Database;
import com.doozycod.axs.Database.Entities.RunInfoEntity;
import com.doozycod.axs.Database.Entities.TaskInfoEntity;
import com.doozycod.axs.POJO.TaskInfoGroupByLocationKey;
import com.doozycod.axs.Utils.Constants;

import java.util.List;

public class TaskInfoRepository {
    private TaskInfoDAO taskInfoDAO;
    private RunInfoDAO runInfoDAO;

    public TaskInfoRepository(Application application) {
        Database db = Database.getDatabase(application);
        taskInfoDAO = db.taskInfoDAO();
        runInfoDAO = db.runInfoDAO();
    }

    public LiveData<List<TaskInfoEntity>> getTaskInfos() {
        return taskInfoDAO.getTaskInfoList();
    }

    public LiveData<List<RunInfoEntity>> getRunInfos() {
        return runInfoDAO.getRunInfoList();
    }

    public List<TaskInfoEntity> getTaskInfos1() {
        return taskInfoDAO.getTaskInfoList1();
    }

    public TaskInfoEntity getTaskInfoWithId(String taskid) {
        return taskInfoDAO.getTaskInfo(taskid);
    }

    public LiveData<List<TaskInfoGroupByLocationKey>> getTaskInfoGroupByLocationKeys() {
        return taskInfoDAO.getTaskInfoGroupByLocationKeys();
    }

    public LiveData<List<TaskInfoGroupByLocationKey>> getTaskInfoSearchByWorkStatusGroupByLocationKeys(String workStatus) {
        return taskInfoDAO.getTaskInfoSearchByWorkStatusGroupByLocationKeys(workStatus);
    }

    public LiveData<List<TaskInfoEntity>> getTaskInfoByLocationKey(String locationKey) {
        Log.d("repository", "getTaskInfoByLocationKey: " + locationKey);
        return taskInfoDAO.getTaskInfoByLocationKey(locationKey);
    }

    public LiveData<List<TaskInfoGroupByLocationKey>> getTaskInfoByBatchId(String batchId) {
        Log.d("repository", "getTaskInfoByBatchId: " + batchId);
        return taskInfoDAO.getTaskInfoByBatchId(batchId);
    }

    public void insert(final TaskInfoEntity... taskInfoEntities) {
        Database.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                taskInfoDAO.insert(taskInfoEntities);
            }
        });
    }

    public void insert(final RunInfoEntity... runInfoEntities) {
        Database.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                runInfoDAO.insert(runInfoEntities);
            }
        });
    }

    public void update(final TaskInfoEntity... taskInfoEntities) {
        Database.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                taskInfoDAO.updateTaskInfo(taskInfoEntities);
            }
        });

    }

    public void updateLocation(final String locationKey, final String arrivalTime) {
        Database.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                taskInfoDAO.updateLocation(locationKey, arrivalTime);
            }
        });

    }

    public void delete(final TaskInfoEntity... taskInfoEntities) {
        Database.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                taskInfoDAO.deleteTaskInfos(taskInfoEntities);
            }
        });
    }

    public void deleteAll() {
        Database.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                taskInfoDAO.deleteAll();
            }
        });
    }

    public void deleteAllRunList() {
        Database.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                runInfoDAO.deleteAllRunList();
            }
        });
    }

    public List<TaskInfoEntity> getTaskInfoByRecordStatus(int recordStatus) {
        return taskInfoDAO.getTaskInfoByRecordStatus(recordStatus);
    }

    public List<TaskInfoEntity> getTaskInfoCompleted(String locationKey,String workStatus) {
        return taskInfoDAO.getTaskInfoCompleted(locationKey, workStatus);
    }

    public boolean isEmptyTask() {
        return taskInfoDAO.getTaskInfoLimit1().isEmpty();
    }
}
