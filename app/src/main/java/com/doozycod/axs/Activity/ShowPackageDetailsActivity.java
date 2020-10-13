package com.doozycod.axs.Activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Camera;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.doozycod.axs.Adapter.TaskInfoGroupByListViewAdapter;
import com.doozycod.axs.ApiService.ApiService;
import com.doozycod.axs.ApiService.ApiUtils;
import com.doozycod.axs.Database.Entities.TaskInfoEntity;
import com.doozycod.axs.Database.ViewModel.TaskInfoViewModel;
import com.doozycod.axs.POJO.ConfirmNextModel;
import com.doozycod.axs.POJO.LoginResponse;
import com.doozycod.axs.POJO.TaskInfoGroupByLocationKey;
import com.doozycod.axs.R;
import com.doozycod.axs.Utils.Constants;
import com.doozycod.axs.gmap.turn.GmapTurnNavActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowPackageDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    // map embedded in the map fragment
    private GoogleMap map = null;

    // map fragment embedded in this activity
//    private AndroidXMapFragment mapFragment = null;

    private TaskInfoViewModel taskInfoViewModel;
    private List<TaskInfoEntity> taskInfoEntityList;
    private String TAG = "ShowPackageDetailsActivity";
    ListView listView;
    TaskInfoGroupByLocationKey taskInfoGroupByLocationKey;
    String taskInfoGroupByLocation;
    TaskInfoGroupByListViewAdapter listViewAdapter;
    Toolbar mActionBarToolbar;
    Button continue_btn;
    int LAUNCH_SECOND_ACTIVITY = 1;
    private Button scanPackageBtn, navigationStartButton;
    ApiService apiService;
    private SupportMapFragment supportMapFragment;
    private double longitude;
    private double latitude;
    Intent intent;
    public static TaskInfoEntity selectedTask;
    String batchId = "";

    //    private Button scanPackageBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_package);
//        initialize();
        taskInfoViewModel = new ViewModelProvider(this).get(TaskInfoViewModel.class);
        taskInfoEntityList = new ArrayList<TaskInfoEntity>();


        listView = findViewById(R.id.taskInfoGroupListView);
        navigationStartButton = findViewById(R.id.naviCtrlButton);
        continue_btn = findViewById(R.id.continue_btn);

        apiService = ApiUtils.getAPIService();
        Log.e(TAG, "onCreate: " + taskInfoEntityList.size());

        listViewAdapter = new TaskInfoGroupByListViewAdapter(ShowPackageDetailsActivity.this, taskInfoEntityList, getApplication());
        listView.setAdapter(listViewAdapter);

        String taskINfo = getIntent().getStringExtra("task");

        taskInfoGroupByLocation = PreferenceManager.getDefaultSharedPreferences(this).getString(Constants.SELECTED_LOCATION, "");

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapfragment);
//        TaskInfoGroupByLocationKey locationKey = new Gson().fromJson(taskInfoGroupByLocation, TaskInfoGroupByLocationKey.class);

        batchId = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(Constants.SELECTED_BATCH_ID, "");
        Log.e(TAG, "onCreate: " + taskInfoGroupByLocation);


        scanPackageBtn = findViewById(R.id.scan_package_btn);
        String taskInfoStr = PreferenceManager.getDefaultSharedPreferences(this).getString(Constants.SELECTED_TASK, "");
//        Log.e("TAG", "onCreate: " + taskInfoStr);
        selectedTask = new Gson().fromJson(taskInfoStr, TaskInfoEntity.class);

        taskInfoGroupByLocationKey = new Gson().fromJson(taskInfoGroupByLocation, TaskInfoGroupByLocationKey.class);
        String locationKey = taskInfoGroupByLocationKey.getLocationKey();

        supportMapFragment.getMapAsync(this::onMapReady);

        navigationStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (batchId != null && !batchId.equals("")) {
                    confirmNextStopAPI(taskINfo, batchId, locationKey);

                }
                /*if (!selectedTask.getBatchId().equals("") || selectedTask != null) {
                    confirmNextStopAPI(taskINfo, selectedTask.getBatchId(), locationKey);

                }*/


            }
        });

        try {
            taskInfoViewModel.getTaskInfoByLocationKey(locationKey).observe(this, new Observer<List<TaskInfoEntity>>() {
                @Override
                public void onChanged(List<TaskInfoEntity> taskInfoEntities) {
                    taskInfoEntityList.clear();
                    taskInfoEntityList.addAll(taskInfoEntities);
                    batchId = taskInfoEntities.get(0).getBatchId();
                    PreferenceManager.getDefaultSharedPreferences(ShowPackageDetailsActivity.this).edit()
                            .putString(Constants.SELECTED_BATCH_ID, batchId)
                            .apply();
                    listViewAdapter.notifyDataSetChanged();
//                    Log.e(TAG, "onChanged: " + taskInfoEntityList.toString());
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

    void confirmNextStopAPI(String taskInfo, String batchId, String locationKey) {
        Log.e(TAG, "confirmNextStopAPI: " + batchId + locationKey);
        String jsonLoginResponse = PreferenceManager.getDefaultSharedPreferences(this).getString(Constants.PREF_KEY_LOGIN_RESPONSE, "");
        LoginResponse loginResponse = new Gson().fromJson(jsonLoginResponse, LoginResponse.class);
        String token = loginResponse.getToken();
        apiService.confirmNextStop(batchId, locationKey, Constants.AUTHORIZATION_TOKEN + token).enqueue(new Callback<ConfirmNextModel>() {
            @Override
            public void onResponse(Call<ConfirmNextModel> call, Response<ConfirmNextModel> response) {
                if (response.code() == 200) {
                    Log.e(TAG, "onResponse: " + response.body().getLocationKey() + batchId);
                    if (locationKey.equals(response.body().getLocationKey())) {
                        Log.e(TAG, "onResponse: Location is same");
                        Intent intent = new Intent(ShowPackageDetailsActivity.this, ArrivalTimeMapTypeActivity.class);
                        intent.putExtra("task", taskInfo);
//                        intent.putExtra("bylocation", taskInfoGroupByLocation);
                        intent.putExtra("current", latitude + "," + longitude);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<ConfirmNextModel> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        double lat = Double.parseDouble(taskInfoGroupByLocationKey.getLatitude());
        double lon = Double.parseDouble(taskInfoGroupByLocationKey.getLongitude());
        map.addMarker(new MarkerOptions().position(new LatLng(lat, lon))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lon)));
        map.animateCamera(CameraUpdateFactory.zoomTo(16));
        Location location = getCurrentLocation();
        latitude = location.getLatitude();
        longitude = location.getLongitude();

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
//    private void initialize() {
//        taskInfoViewModel = new ViewModelProvider(this).get(TaskInfoViewModel.class);
//        taskInfoEntityList = new ArrayList<TaskInfoEntity>();
//
//        mapFragment = (AndroidXMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapfragment);
//
//        // Set up disk map cache path for this application
//        // Use path under your application folder for storing the disk cache
//        com.here.android.mpa.common.MapSettings.setDiskCacheRootPath(
//                getApplicationContext().getExternalFilesDir(null) + File.separator + ".here-maps");
//        mapFragment.init(new OnEngineInitListener() {
//            @Override
//            public void onEngineInitializationCompleted(Error error) {
//                if (error == Error.NONE) {
//                    // retrieve a reference of the map from the map fragment
//                    map = mapFragment.getMap();
//
//                    double lat = Double.parseDouble(taskInfoGroupByLocationKey.getLatitude());
//                    double lon = Double.parseDouble(taskInfoGroupByLocationKey.getLongitude());
//                    // Set the map center to the Vancouver region (no animation)
//                    map.setCenter(new GeoCoordinate(lat, lon, 0.0),
//                            Map.Animation.NONE);
//                    // Set the zoom level to the average between min and max
//                    map.setZoomLevel(16);

//                    MapMarker defaultMarker = new MapMarker();
//                    defaultMarker.setCoordinate(new GeoCoordinate(lat, lon, 0.0));
//                    map.addMapObject(defaultMarker);
//
//                } else {
//                    System.out.println("ERROR: Cannot initialize Map Fragment");
//                }
//            }
//        });
//    }
}