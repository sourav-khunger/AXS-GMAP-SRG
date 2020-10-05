package com.doozycod.axs.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.doozycod.axs.Database.Entities.TaskInfoEntity;
import com.doozycod.axs.Database.ViewModel.TaskInfoViewModel;
import com.doozycod.axs.R;
import com.doozycod.axs.Utils.Constants;

public class TaskInfoUpdateActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "TaskInfoUpdateActivity";
    private TaskInfoEntity taskInfoEntity;
    private Button btnTaskInfoUpdate;
    TaskInfoViewModel taskInfoViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_info_update);

        initialize();

        String taskInfoGroupByLocation = PreferenceManager.getDefaultSharedPreferences(this).getString(Constants.SELECTED_TASK, "");
        Log.d(TAG, "onCreate: " + taskInfoGroupByLocation);
        taskInfoEntity = new Gson().fromJson(taskInfoGroupByLocation, TaskInfoEntity.class);
        String name = taskInfoEntity.getName();
        Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();
        // changing data
        if(taskInfoEntity.getArrivalTime()==null || taskInfoEntity.getArrivalTime() == "") {
            taskInfoEntity.setArrivalTime("2020-08-21 14:00:00");
            taskInfoEntity.setCompleteTime("2020-08-21 14:05:00");
        }
        taskInfoEntity.setStatusId(10);
        taskInfoEntity.setReasonId(10);
        taskInfoEntity.setDriverComment("10");
        taskInfoEntity.setCod(0);
    }

    private void initialize() {
        btnTaskInfoUpdate = findViewById(R.id.btnUpdateTaskInfo);
        btnTaskInfoUpdate.setOnClickListener(this);
        taskInfoViewModel = new ViewModelProvider(this).get(TaskInfoViewModel.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnUpdateTaskInfo:
//                String taskInfoEntityJsonString = new Gson().toJson(taskInfoEntity);
//                PreferenceManager.getDefaultSharedPreferences(TaskInfoUpdateActivity.this).edit()
//                        .putString(Constants.SELECTED_TASK, taskInfoEntityJsonString)
//                        .apply();
                taskInfoEntity.setRecordStatus(2);
                taskInfoViewModel.updateTaskInfo(taskInfoEntity);
                Log.d(TAG, "onClick: " + "button is clicked");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }
}