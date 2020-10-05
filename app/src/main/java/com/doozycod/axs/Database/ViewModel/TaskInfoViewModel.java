package com.doozycod.axs.Database.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.work.BackoffPolicy;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.doozycod.axs.BackgroudService.ConstraintsForService.NetworkConstraint;
import com.doozycod.axs.BackgroudService.Workers.UpdateRemoteDatabaseWorker;
import com.doozycod.axs.Database.Entities.RunInfoEntity;
import com.doozycod.axs.Database.Entities.TaskInfoEntity;
import com.doozycod.axs.Database.Repository.TaskInfoRepository;
import com.doozycod.axs.POJO.TaskInfoGroupByLocationKey;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.doozycod.axs.Utils.Constants.SYNC_DATA_WORK_NAME;
import static com.doozycod.axs.Utils.Constants.TAG_SYNC_DATA;

public class TaskInfoViewModel extends AndroidViewModel {

    private TaskInfoRepository taskInfoRepository;
    private WorkManager mWorkManager;

    public TaskInfoViewModel(@NonNull Application application) {
        super(application);
        taskInfoRepository = new TaskInfoRepository(application);
        mWorkManager = WorkManager.getInstance(application);
    }

    public LiveData<List<TaskInfoEntity>> getTaskInfoList() {
        return taskInfoRepository.getTaskInfos();
    }

    public LiveData<List<RunInfoEntity>> getRunInfoList() {
        return taskInfoRepository.getRunInfos();
    }

    public TaskInfoEntity getTaskInfo(String taskId) {
        return (TaskInfoEntity) taskInfoRepository.getTaskInfoWithId(taskId);
    }

    public void deleteTaskInfo(TaskInfoEntity... taskInfoEntities) {
        taskInfoRepository.delete(taskInfoEntities);

    }

    public void updateTaskInfo(TaskInfoEntity... taskInfoEntities) {
        taskInfoRepository.update(taskInfoEntities);

    }

    public void insertTaskInfo(TaskInfoEntity... taskInfoEntities) {
        taskInfoRepository.insert(taskInfoEntities);
    }

    public LiveData<List<TaskInfoGroupByLocationKey>> getTaskInfoGroupByLocationKeys() {
        return taskInfoRepository.getTaskInfoGroupByLocationKeys();
    }

    public LiveData<List<TaskInfoGroupByLocationKey>> getTaskInfoGroupByBatchId(String batchId) {
        return taskInfoRepository.getTaskInfoByBatchId(batchId);
    }

    public LiveData<List<TaskInfoGroupByLocationKey>> getTaskInfoSearchByWorkStatusGroupByLocationKeys(String workStatus) {
        return taskInfoRepository.getTaskInfoSearchByWorkStatusGroupByLocationKeys(workStatus);
    }

    public LiveData<List<TaskInfoEntity>> getTaskInfoByLocationKey(String locationKey) {
        Log.d("view model", "getTaskInfoByLocationKey: " + locationKey);
        return taskInfoRepository.getTaskInfoByLocationKey(locationKey);
    }

    public void syncRemoteDBWithLocalDB() {
        PeriodicWorkRequest periodicSyncDataWork =
                new PeriodicWorkRequest.Builder(UpdateRemoteDatabaseWorker.class, PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS)
                        .addTag(TAG_SYNC_DATA)
                        .setConstraints(NetworkConstraint.getNetworkConstraints())
                        // setting a backoff on case the work needs to retry
                        .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                        .build();
        mWorkManager.enqueueUniquePeriodicWork(
                SYNC_DATA_WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP, //Existing Periodic Work policy
                periodicSyncDataWork //work request
        );

    }

    public void cancelWork() {
        Log.i("VIEWMODEL", "Cancelling work");
        mWorkManager.cancelUniqueWork(SYNC_DATA_WORK_NAME);
    }
}
