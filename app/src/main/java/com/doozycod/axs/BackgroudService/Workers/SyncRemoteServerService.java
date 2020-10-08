package com.doozycod.axs.BackgroudService.Workers;

import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.doozycod.axs.ApiService.ApiService;
import com.doozycod.axs.ApiService.ApiUtils;
import com.doozycod.axs.CoverterPOJOToEntity.RunInfoToRunInfoEntity;
import com.doozycod.axs.CoverterPOJOToEntity.TaskInfoToTaskInfoEntity;
import com.doozycod.axs.Database.Entities.TaskInfoEntity;
import com.doozycod.axs.Database.Repository.TaskInfoRepository;
import com.doozycod.axs.POJO.LoginResponse;
import com.doozycod.axs.POJO.RunInfo;
import com.doozycod.axs.POJO.TaskInfo;
import com.doozycod.axs.POJO.TaskInfoResponse;
import com.doozycod.axs.POJO.TaskInfoUpdateResponse;
import com.doozycod.axs.Utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SyncRemoteServerService extends Service {
    private static String TAG = "Service";
    private Handler mHandler;
    // default interval for syncing data
    public static final long DEFAULT_SYNC_INTERVAL = 30 * 1000;
    private List<TaskInfoEntity> mTaskInfoEntityList;
    private List<TaskInfoUpdateResponse> mTaskInfoUpdateResponseList;
    private TaskInfoRepository mTaskInfoRepository;
    private ApiService apiService;

    // task to be run here
    private Runnable runnableService = new Runnable() {
        @Override
        public void run() {
            syncData();
            // Repeat this runnable code block again every ... min
            mHandler.postDelayed(runnableService, DEFAULT_SYNC_INTERVAL);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mTaskInfoEntityList = new ArrayList<TaskInfoEntity>();
        mTaskInfoRepository = new TaskInfoRepository((Application) getApplicationContext());
        mTaskInfoUpdateResponseList = new ArrayList<TaskInfoUpdateResponse>();
        apiService = ApiUtils.getAPIService();

//        Toast.makeText(getApplicationContext(), "Background Service Started.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Create the Handler object
        mHandler = new Handler();
        // Execute a runnable task as soon as possible
        mHandler.post(runnableService);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private synchronized void syncData() {
        // call your rest service here
        try {
            // pulling data from server
            pullData();

            // pushing local changes to server
            pushData();

        } catch (Throwable throwable) {
            Log.d(TAG, "doWork: " + throwable.getMessage());
        }
        // Toast.makeText(getApplicationContext(), ".", Toast.LENGTH_SHORT).show();
    }


    private void pullData() {
        //mTaskInfoRepository.deleteAll();

        if (mTaskInfoRepository.isEmptyTask()) {
            String jsonLoginResponse = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(Constants.PREF_KEY_LOGIN_RESPONSE, "");
            LoginResponse loginResponse = new Gson().fromJson(jsonLoginResponse, LoginResponse.class);
            String token = loginResponse.getToken();

            Date date = new Date();
            String curDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
            String selectedDate = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(Constants.PREF_KEY_SELECTED_DATE, curDate);
            String compId = "" + loginResponse.getDriverInfo().getCompanyId();
            // compId = "34";
            Log.e(TAG, "pullData: SERVICE " + selectedDate);

            apiService.getTaskList(compId, selectedDate, Constants.AUTHORIZATION_TOKEN + token).enqueue(new Callback<TaskInfoResponse>() {
                @Override
                public void onResponse(Call<TaskInfoResponse> call, Response<TaskInfoResponse> response) {
                    Log.d(TAG, "onResponse: ");
                    if (response.code() == 200) {
                        Log.d(TAG, "size: " + response.body().getListOfTaskInfo().size());
                        Log.e(TAG, "RUN size: " + response.body().getListOfRunList().size());
                        saveTaskInfoListToLocalDB(response.body().getListOfTaskInfo(), response.body().getListOfRunList());
                    } else {
                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<TaskInfoResponse> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                    Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private void saveTaskInfoListToLocalDB(ArrayList<TaskInfo> listOfTaskInfo, ArrayList<RunInfo> listfOfRunInfo) {
        mTaskInfoRepository.deleteAll();
        mTaskInfoRepository.deleteAllRunList();
        for (RunInfo runInfo : listfOfRunInfo) {
            Log.e(TAG, "saveTaskInfoListToLocalDB: " + runInfo.toString());
            mTaskInfoRepository.insert(RunInfoToRunInfoEntity.convertRunInfoToRunInfoEntity(runInfo));
        }
        for (TaskInfo taskInfo : listOfTaskInfo) {
            mTaskInfoRepository.insert(TaskInfoToTaskInfoEntity.convertTaskInfoToTaskInfoEntity(taskInfo));
        }

    }

    private void pushData() {
        mTaskInfoEntityList = mTaskInfoRepository.getTaskInfoByRecordStatus(Constants.MODIFIED);
        String jsonLoginResponse = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(Constants.PREF_KEY_LOGIN_RESPONSE, "");
        LoginResponse loginResponse = new Gson().fromJson(jsonLoginResponse, LoginResponse.class);
        String token = loginResponse.getToken();
        for (int i = 0; i < mTaskInfoEntityList.size(); i++) {
            String mTaskInfoJosnString = new Gson().toJson(mTaskInfoEntityList.get(i));
            Log.d(TAG, "syncData: " + Constants.AUTHORIZATION_TOKEN + token);
            Log.d(TAG, "syncData: " + mTaskInfoJosnString);
            apiService.updateTaskInfo(mTaskInfoEntityList.get(i), Constants.AUTHORIZATION_TOKEN + token).enqueue(new Callback<TaskInfoUpdateResponse>() {
                @Override
                public void onResponse(Call<TaskInfoUpdateResponse> call, Response<TaskInfoUpdateResponse> response) {
                    try {
                        if (response.code() == 200) {
                            TaskInfoUpdateResponse mTaskInfoUpdateResponse = response.body();
                            Log.d(TAG, "onResponse: " + response.body());
                            TaskInfoEntity mTaskInfoEntity = mTaskInfoRepository.getTaskInfoWithId(Long.toString(mTaskInfoUpdateResponse.getTaskId()));
                            mTaskInfoEntity.setRecordStatus(Constants.NOT_MODIFIED);
                            mTaskInfoEntity.setDataId(mTaskInfoUpdateResponse.getDataId());
                            mTaskInfoEntity.setStopId(mTaskInfoUpdateResponse.getStopId());
                            mTaskInfoEntity.setWorkStatus(Constants.TASK_INFO_WORK_STATUS_COMPLETED);
                            mTaskInfoRepository.update(mTaskInfoEntity);

                        } else {
                            Log.d(TAG, "onResponse: ");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<TaskInfoUpdateResponse> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        if (mHandler != null) {
            mHandler.removeCallbacks(runnableService);
        }

        // TODO Auto-generated method stub
        Log.d("Destroying ", " Destroying NNotifyService. ");
        super.onDestroy();
    }
}
