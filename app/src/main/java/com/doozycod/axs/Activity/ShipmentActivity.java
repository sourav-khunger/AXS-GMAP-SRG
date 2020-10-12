package com.doozycod.axs.Activity;

import android.os.Bundle;

import com.google.gson.Gson;
import com.doozycod.axs.Database.Entities.TaskInfoEntity;
import com.doozycod.axs.R;
import com.doozycod.axs.Utils.Constants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShipmentActivity extends AppCompatActivity {

    public static TaskInfoEntity selectedTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String taskInfoStr = PreferenceManager.getDefaultSharedPreferences(this).getString(Constants.SELECTED_TASK, "");
        Log.e("TAG", "onCreate: " + taskInfoStr);
        selectedTask = new Gson().fromJson(taskInfoStr, TaskInfoEntity.class);

        if (selectedTask.getArrivalTime() == null || selectedTask.getArrivalTime().equals("")) {

            Date date = new Date();
            String curDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
            Log.e("TAG", "onCreate: " + curDate);

            selectedTask.setArrivalTime(curDate);
        }

    }
}