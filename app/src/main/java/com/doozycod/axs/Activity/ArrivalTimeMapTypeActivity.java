package com.doozycod.axs.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.doozycod.axs.Database.Entities.TaskInfoEntity;
import com.doozycod.axs.Database.Repository.TaskInfoRepository;
import com.doozycod.axs.Database.ViewModel.TaskInfoViewModel;
import com.doozycod.axs.POJO.TaskInfoGroupByLocationKey;
import com.doozycod.axs.R;
import com.doozycod.axs.Utils.Constants;
import com.doozycod.axs.gmap.turn.GmapTurnNavActivity;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ArrivalTimeMapTypeActivity extends AppCompatActivity {
    Toolbar mActionBarToolbar;
    TaskInfoGroupByLocationKey taskInfoGroupByLocationKey;
    private TaskInfoViewModel taskInfoViewModel;
    private List<TaskInfoEntity> taskInfoEntityList;
    public static TaskInfoEntity selectedTask;
    Button mapWithLatLon, mapWithAddress, arrivedButton;
    String currentLocation = "", getTaskByLocationKey = "";
    Location location;
    private Intent intent;
    TextView streetTxt, cityTxt, hoursET, minuteET;
    TimePickerDialog timePickerDialog;
    LinearLayout selectTimeButton;
    String currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrival_time_map_type);
        mActionBarToolbar = findViewById(R.id.toolbar);
        arrivedButton = findViewById(R.id.arrivedButton);
        mapWithAddress = findViewById(R.id.mapWithAddress);
        hoursET = findViewById(R.id.hoursET);
        minuteET = findViewById(R.id.minuteET);
        streetTxt = findViewById(R.id.streetTxt);
        cityTxt = findViewById(R.id.cityTxt);
        mapWithLatLon = findViewById(R.id.mapWithLatLon);
        selectTimeButton = findViewById(R.id.selectTimeTxt);

        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Start Navigation");

        taskInfoViewModel = new ViewModelProvider(this).get(TaskInfoViewModel.class);
        taskInfoEntityList = new ArrayList<TaskInfoEntity>();
        String taskInfoGroupByLocation = PreferenceManager.getDefaultSharedPreferences(this).getString(Constants.SELECTED_LOCATION, "");
        taskInfoGroupByLocationKey = new Gson().fromJson(taskInfoGroupByLocation, TaskInfoGroupByLocationKey.class);
        String locationKey = taskInfoGroupByLocationKey.getLocationKey();
        currentLocation = getIntent().getStringExtra("current");
//        getTaskByLocationKey = getIntent().getStringExtra("bylocation");

        if (selectedTask == null) {
            String taskINfo = getIntent().getStringExtra("task");
            selectedTask = new Gson().fromJson(taskINfo, TaskInfoEntity.class);

        } else {

        }

        arrivedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             /*   PreferenceManager.getDefaultSharedPreferences(ArrivalTimeMapTypeActivity.this).edit()
                        .putString(Constants.PREF_DELIVERY_COMPLETEDTIME, getCurrentTimeDate() + " "
                                + hoursET.getText().toString() + ":" + minuteET.getText().toString() + ":" + "00")
                        .apply();*/
                TaskInfoRepository mTaskInfoRepository = new TaskInfoRepository((Application) getApplicationContext());
                String curDateTime = getCurrentTimeDate() + " " + hoursET.getText().toString() + ":" + minuteET.getText().toString() + ":" + "00";
                mTaskInfoRepository.updateLocation(taskInfoGroupByLocationKey.getLocationKey(), curDateTime);
                selectedTask.setRecordStatus(Constants.PARTIAL_MODIFIED);
                mTaskInfoRepository.update(selectedTask);


                Intent intent = new Intent(ArrivalTimeMapTypeActivity.this,
                        ShowListOfTaskGroupByLocationKeyActivity.class);
                startActivity(intent);
                finish();
            }
        });
        selectTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                new TimePickerDialog(ArrivalTimeMapTypeActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        hoursET.setText(selectedHour + "");
                        minuteET.setText(selectedMinute + "");
                    }
                }, hour, minute, true).show();
            }
        });
        if (currentLocation.equals("")) {
            location = getCurrentLocation();
            currentLocation = location.getLatitude() + "," + location.getLongitude();
        }
        try {
            taskInfoViewModel.getTaskInfoByLocationKey(locationKey).observe(this, new Observer<List<TaskInfoEntity>>() {
                @Override
                public void onChanged(List<TaskInfoEntity> taskInfoEntities) {
                    taskInfoEntityList.clear();
                    taskInfoEntityList.addAll(taskInfoEntities);
//                    listViewAdapter.notifyDataSetChanged();
//                    Log.e(TAG, "onChanged: " + taskInfoEntityList.toString());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        streetTxt.setText(taskInfoGroupByLocationKey.getAddress());
        cityTxt.setText(taskInfoGroupByLocationKey.getCity() + ", " + taskInfoGroupByLocationKey.getPostalCode());

        mapWithLatLon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                intent = new Intent(android.content.Intent.ACTION_VIEW,
//                        Uri.parse("http://maps.google.com/maps?daddr="
//                                + selectedTask.getLatitude() + "," + selectedTask.getLongitude()));
                intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q="
                                + taskInfoGroupByLocationKey.getLatitude() + "," + taskInfoGroupByLocationKey.getLongitude()));
                startActivity(intent);
            }
        });
        mapWithAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q=" + taskInfoGroupByLocationKey.getAddress() + "+" + taskInfoGroupByLocationKey.getCity()));
                startActivity(intent);

            }
        });
    }

    String getCurrentTimeDate() {
        String dateTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return dateTime;
    }

    String getCurrentTime() {
        String time = new SimpleDateFormat("HH mm").format(new Date());
        return time;
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentTime = getCurrentTimeDate();
        String[] time = getCurrentTime().split(" ");

        int i = Integer.parseInt(time[0]);
        hoursET.setText(i + "");
        minuteET.setText(time[1]);
    }

    @SuppressLint("MissingPermission")
    public Location getCurrentLocation() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {

            Location lastKnownLocationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocationGPS != null) {
                return lastKnownLocationGPS;
            } else {
                Location loc = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                System.out.println("1::" + loc);
                System.out.println("2::" + loc.getLatitude());
                return loc;
            }
        } else {
            return null;
        }
    }
}