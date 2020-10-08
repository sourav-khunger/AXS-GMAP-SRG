package com.doozycod.axs.Activity;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.doozycod.axs.Database.Repository.TaskInfoRepository;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.doozycod.axs.Adapter.TaskInfoGroupByListViewAdapter;
import com.doozycod.axs.Database.Entities.TaskInfoEntity;
import com.doozycod.axs.Database.ViewModel.TaskInfoViewModel;
import com.doozycod.axs.POJO.TaskInfoGroupByLocationKey;
import com.doozycod.axs.R;
import com.doozycod.axs.Utils.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ShowListOfTaskGroupByLocationKeyActivity extends AppCompatActivity implements OnMapReadyCallback {

    // map embedded in the map fragment
    private GoogleMap map = null;


    private TaskInfoViewModel taskInfoViewModel;
    private List<TaskInfoEntity> taskInfoEntityList;
    private String TAG = "ShowListOfTaskGroupByLocationKeyActivity";
    ListView listView;
    TaskInfoGroupByLocationKey taskInfoGroupByLocationKey;
    TaskInfoGroupByListViewAdapter listViewAdapter;
    Toolbar mActionBarToolbar;
    Button continue_btn;
    int LAUNCH_SECOND_ACTIVITY = 1;
    private Button scanPackageBtn;
    private SupportMapFragment supportMapFragment;

    //    private Button scanPackageBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_of_task_group_by_location_key);
        initialize();

        listView = findViewById(R.id.taskInfoGroupListView);
        continue_btn = findViewById(R.id.continue_btn);
        Log.e(TAG, "onCreate: " + taskInfoEntityList.size());
        listViewAdapter = new TaskInfoGroupByListViewAdapter(ShowListOfTaskGroupByLocationKeyActivity.this, taskInfoEntityList, getApplication());
        listView.setAdapter(listViewAdapter);
        String taskInfoGroupByLocation = PreferenceManager.getDefaultSharedPreferences(this).getString(Constants.SELECTED_LOCATION, "");
        Log.e(TAG, "onCreate: " + taskInfoGroupByLocation);

        scanPackageBtn = findViewById(R.id.scan_package_btn);
        scanPackageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ShowListOfTaskGroupByLocationKeyActivity.this, ScanPackageActivity.class);
                startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);

                // startActivity(new Intent(ShowListOfTaskGroupByLocationKeyActivity.this, ScanPackageActivity.class));
            }
        });
        listView.setLongClickable(true);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                TaskInfoEntity taskInfoEntity = taskInfoEntityList.get(position);

                if (taskInfoEntity.getWorkStatus().equals(Constants.TASK_INFO_WORK_STATUS_COMPLETED)) {
                    scanPackageBtn.setEnabled(false);
                    return false;
                }


                String taskInfoEntityJsonString = new Gson().toJson(taskInfoEntity);

                PreferenceManager.getDefaultSharedPreferences(ShowListOfTaskGroupByLocationKeyActivity.this).edit()
                        .putString(Constants.SELECTED_TASK, taskInfoEntityJsonString)
                        .apply();
                TaskInfoRepository mTaskInfoRepository = new TaskInfoRepository((Application) getApplicationContext());
                taskInfoEntity.setRecordStatus(Constants.PARTIAL_MODIFIED);
                mTaskInfoRepository.update(taskInfoEntity);
                Intent intent = new Intent(ShowListOfTaskGroupByLocationKeyActivity.this, ShipmentActivity.class);
                startActivity(intent);
                return true;
            }
        });

//        continue_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ShowListOfTaskGroupByLocationKeyActivity.this, ScannerActivity.class);
//
//                startActivity(intent);
//            }
//        });
        taskInfoGroupByLocationKey = new Gson().fromJson(taskInfoGroupByLocation, TaskInfoGroupByLocationKey.class);
        String locationKey = taskInfoGroupByLocationKey.getLocationKey();
        supportMapFragment.getMapAsync(this::onMapReady);


        try {
            taskInfoViewModel.getTaskInfoByLocationKey(locationKey).observe(this, new Observer<List<TaskInfoEntity>>() {
                @Override
                public void onChanged(List<TaskInfoEntity> taskInfoEntities) {
                    taskInfoEntityList.clear();
                    taskInfoEntityList.addAll(taskInfoEntities);
                    listViewAdapter.notifyDataSetChanged();
                    Log.e(TAG, "onChanged: " + taskInfoEntityList.toString());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        registerReceiver(completedCheckReceiver, new IntentFilter(Constants.COMPLETED_CHECK_RECVER));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(completedCheckReceiver);
    }

    private BroadcastReceiver completedCheckReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<TaskInfoEntity> completed = new ArrayList<>();

            for (TaskInfoEntity task : taskInfoEntityList) {
                if (task.getWorkStatus().equals(Constants.TASK_INFO_WORK_STATUS_COMPLETED)) {
                    completed.add(task);
                }
            }

            if (completed.size() == 0) {
                finish();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initialize() {
        taskInfoViewModel = new ViewModelProvider(this).get(TaskInfoViewModel.class);
        taskInfoEntityList = new ArrayList<TaskInfoEntity>();
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapfragment);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        double lat = Double.parseDouble(taskInfoGroupByLocationKey.getLatitude());
        double lon = Double.parseDouble(taskInfoGroupByLocationKey.getLongitude());
        map.addMarker(new MarkerOptions().position(new LatLng(lat, lon))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lon)));
        map.animateCamera(CameraUpdateFactory.zoomTo(15));

    }
}