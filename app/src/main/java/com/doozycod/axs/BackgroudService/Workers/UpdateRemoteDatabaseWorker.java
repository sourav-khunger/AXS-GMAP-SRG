package com.doozycod.axs.BackgroudService.Workers;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.gson.Gson;
import com.doozycod.axs.ApiService.ApiService;
import com.doozycod.axs.ApiService.ApiUtils;
import com.doozycod.axs.Database.Entities.TaskInfoEntity;
import com.doozycod.axs.Database.Repository.TaskInfoRepository;
import com.doozycod.axs.POJO.LoginResponse;
import com.doozycod.axs.POJO.TaskInfoUpdateResponse;
import com.doozycod.axs.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateRemoteDatabaseWorker extends Worker {
    private static String TAG = "UpdateRemoteDatabaseWorker";
    private Context context;
    private List<TaskInfoEntity> mTaskInfoEntityList;
    private List<TaskInfoUpdateResponse> mTaskInfoUpdateResponseList;
    private TaskInfoRepository mTaskInfoRepository;
    private ApiService apiService;

    public UpdateRemoteDatabaseWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mTaskInfoEntityList = new ArrayList<TaskInfoEntity>();
        mTaskInfoRepository = new TaskInfoRepository((Application) context.getApplicationContext());
        mTaskInfoUpdateResponseList = new ArrayList<TaskInfoUpdateResponse>();
        apiService = ApiUtils.getAPIService();
    }

    @NonNull
    @Override
    public Result doWork() {
        context = getApplicationContext();
        try {
            mTaskInfoEntityList = mTaskInfoRepository.getTaskInfoByRecordStatus(Constants.MODIFIED);
            String jsonLoginResponse = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(Constants.PREF_KEY_LOGIN_RESPONSE, "");
            LoginResponse loginResponse = new Gson().fromJson(jsonLoginResponse, LoginResponse.class);
            String token = loginResponse.getToken();
            for (int i = 0; i < mTaskInfoEntityList.size(); i++) {
                String mTaskInfoJosnString = new Gson().toJson(mTaskInfoEntityList.get(i));
                apiService.updateTaskInfo(mTaskInfoEntityList.get(i), token).enqueue(new Callback<TaskInfoUpdateResponse>() {
                    @Override
                    public void onResponse(Call<TaskInfoUpdateResponse> call, Response<TaskInfoUpdateResponse> response) {
                        TaskInfoUpdateResponse mTaskInfoUpdateResponse = response.body();
                        Log.d(TAG, "onResponse: " + response.body());
                        TaskInfoEntity mTaskInfoEntity = mTaskInfoRepository.getTaskInfoWithId(Long.toString(mTaskInfoUpdateResponse.getTaskId()));
                        mTaskInfoEntity.setRecordStatus(Constants.NOT_MODIFIED);
                        mTaskInfoEntity.setDataId(mTaskInfoUpdateResponse.getDataId());
                        mTaskInfoEntity.setStopId(mTaskInfoUpdateResponse.getStopId());
                        mTaskInfoEntity.setWorkStatus(Constants.TASK_INFO_WORK_STATUS_COMPLETED);
                        mTaskInfoRepository.update(mTaskInfoEntity);
                        Log.d(TAG, "onResponse: " + "task info is updated ");
                        Log.d(TAG, "onResponse: " + new Gson().toJson(mTaskInfoRepository.getTaskInfoWithId(Long.toString(mTaskInfoEntity.getTaskId()))));
                    }

                    @Override
                    public void onFailure(Call<TaskInfoUpdateResponse> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
            }

            return Result.success();

        } catch (Throwable throwable) {
            Log.d(TAG, "doWork: " + throwable.getMessage());
            return Result.failure();
        }
    }

    @Override
    public void onStopped() {
        super.onStopped();
        Log.i(TAG, "OnStopped called for this worker");
    }
}
