package com.doozycod.axs.Activity;

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

import com.google.gson.Gson;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.mapping.AndroidXMapFragment;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapMarker;
import com.doozycod.axs.Adapter.TaskInfoGroupByListViewAdapter;
import com.doozycod.axs.Database.Entities.TaskInfoEntity;
import com.doozycod.axs.Database.ViewModel.TaskInfoViewModel;
import com.doozycod.axs.POJO.TaskInfoGroupByLocationKey;
import com.doozycod.axs.R;
import com.doozycod.axs.Utils.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ShowListOfTaskGroupByLocationKeyActivity extends AppCompatActivity {

    // map embedded in the map fragment
    private Map map = null;

    // map fragment embedded in this activity
    private AndroidXMapFragment mapFragment = null;

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

                if (taskInfoEntity.getWorkStatus().equals(Constants.TASK_INFO_WORK_STATUS_COMPLETED))
                    return false;

                String taskInfoEntityJsonString = new Gson().toJson(taskInfoEntity);

                PreferenceManager.getDefaultSharedPreferences(ShowListOfTaskGroupByLocationKeyActivity.this).edit()
                        .putString(Constants.SELECTED_TASK, taskInfoEntityJsonString)
                        .apply();

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

        mapFragment = (AndroidXMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapfragment);

        // Set up disk map cache path for this application
        // Use path under your application folder for storing the disk cache
        com.here.android.mpa.common.MapSettings.setDiskCacheRootPath(
                getApplicationContext().getExternalFilesDir(null) + File.separator + ".here-maps");


        mapFragment.init(new OnEngineInitListener() {
            @Override
            public void onEngineInitializationCompleted(Error error) {
                if (error == Error.NONE) {
                    // retrieve a reference of the map from the map fragment
                    map = mapFragment.getMap();

                    double lat = Double.parseDouble(taskInfoGroupByLocationKey.getLatitude());
                    double lon = Double.parseDouble(taskInfoGroupByLocationKey.getLongitude());
                    // Set the map center to the Vancouver region (no animation)
                    map.setCenter(new GeoCoordinate(lat, lon, 0.0),
                            Map.Animation.NONE);
                    // Set the zoom level to the average between min and max
                    map.setZoomLevel(16);

                    MapMarker defaultMarker = new MapMarker();
                    defaultMarker.setCoordinate(new GeoCoordinate(lat, lon, 0.0));
                    map.addMapObject(defaultMarker);

                } else {
                    System.out.println("ERROR: Cannot initialize Map Fragment");
                }
            }
        });
    }
}