package com.doozycod.axs.gmap.turn;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.doozycod.axs.Activity.ShowListOfTaskGroupByLocationKeyActivity;
import com.doozycod.axs.Database.Repository.TaskInfoRepository;
import com.doozycod.axs.Utils.DirectionsJSONParser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.doozycod.axs.Database.Entities.TaskInfoEntity;
import com.doozycod.axs.POJO.TaskInfoGroupByLocationKey;
import com.doozycod.axs.R;
import com.doozycod.axs.Utils.Constants;

import android.os.PowerManager;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Main activity which launches map view and handles Android run-time requesting permission.
 */

public class GmapTurnNavActivity extends AppCompatActivity implements LocationListener, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private static final String[] RUNTIME_PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE
    };

    private MapFragmentView m_mapFragmentView;
    TaskInfoGroupByLocationKey taskInfoGroupByLocationKey;
    //    TaskInfoEntity taskInfoEntity;
    Toolbar mActionBarToolbar;
    private PowerManager.WakeLock mWakeLock;
    TextView dilverPackagesTV;
    public static TaskInfoEntity selectedTask;
    SupportMapFragment supportMapFragment;
    TextView distanceTxt, speedTxt, turnTxt, nxtDisTxt;
    private GoogleMap map;
    LatLng origin;
    private double longitude;
    private double latitude;
    private GoogleApiClient googleApiClient;
    Intent intent;
    private LocationManager locationManager;
    boolean isNetworkEnabled = false;

    Location location;

    boolean isArrived = false;

    Button m_arrivedButton;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String taskInfoGroupByLocation = PreferenceManager.getDefaultSharedPreferences(this).getString(Constants.SELECTED_LOCATION, "");
        String taskInfoStr = PreferenceManager.getDefaultSharedPreferences(this).getString(Constants.SELECTED_TASK, "");
        Log.e("TAG", "onCreate: " + taskInfoStr);
        selectedTask = new Gson().fromJson(taskInfoStr, TaskInfoEntity.class);
        taskInfoGroupByLocationKey = new Gson().fromJson(taskInfoGroupByLocation, TaskInfoGroupByLocationKey.class);
//        taskInfoEntity = new Gson().fromJson(taskInfoGroupByLocation, TaskInfoEntity.class);

        setContentView(R.layout.activity_main_here_turn_nav);

        mActionBarToolbar = findViewById(R.id.toolbar);
        dilverPackagesTV = findViewById(R.id.dilver_packagesTV);
        m_arrivedButton = findViewById(R.id.arrivedButton);

        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle(taskInfoGroupByLocationKey.getAddress() + " " + taskInfoGroupByLocationKey.getCity() + " " + taskInfoGroupByLocationKey.getPostalCode());
        mWakeLock = ((PowerManager) getSystemService(Context.POWER_SERVICE))
                .newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, getClass().getName());
        mWakeLock.acquire();
        if (selectedTask == null) {
            String taskINfo = getIntent().getStringExtra("task");
            selectedTask = new Gson().fromJson(taskINfo, TaskInfoEntity.class);
            if (selectedTask.getQuantity() == 1) {
                dilverPackagesTV.setText("Deliver " + selectedTask.getQuantity() + " package");

            } else {
                dilverPackagesTV.setText("Deliver " + selectedTask.getQuantity() + " packages");
            }
        } else {
            if (selectedTask.getQuantity() == 1) {
                dilverPackagesTV.setText("Deliver " + selectedTask.getQuantity() + " package");

            } else {
                dilverPackagesTV.setText("Deliver " + selectedTask.getQuantity() + " packages");
            }
        }

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapfragment);
        supportMapFragment.getMapAsync(this);

        //Initializing googleApiClient
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        origin = new LatLng(43.527040495048, -79.718987583467);

        m_arrivedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isArrived) {
                    AlertDialog alert;
                    AlertDialog.Builder builder = new AlertDialog.Builder(GmapTurnNavActivity.this);
                    builder.setMessage("You have reached your destination.")
                            .setCancelable(false)
                            .setTitle("Arrived!")
                            .setPositiveButton("Yes ! ",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            TaskInfoRepository mTaskInfoRepository = new TaskInfoRepository((Application) getApplicationContext());
                                            String curDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                                            mTaskInfoRepository.updateLocation(taskInfoGroupByLocationKey.getLocationKey(), curDateTime);

                                            Intent intent = new Intent(GmapTurnNavActivity.this, ShowListOfTaskGroupByLocationKeyActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    })
                            .setNegativeButton("Not yet",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // cancel the dialog box
                                            dialog.cancel();
                                        }
                                    });
                    alert = builder.create();
                    alert.show();
                } else
                    Toast.makeText(GmapTurnNavActivity.this, "You haven't reached yet!", Toast.LENGTH_SHORT).show();

            }
        });
        //        startActivity(intent);
//        getLocation();

//        if (hasPermissions(this, RUNTIME_PERMISSIONS)) {
//            setupMapFragmentView();
//        } else {
//            ActivityCompat
//                    .requestPermissions(this, RUNTIME_PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
//        }
    }


    private void moveMap() {
        /**
         * Creating the latlng object to store lat, long coordinates
         * adding marker to map
         * move the camera with animation
         */
        LatLng latLng = new LatLng(latitude, longitude);
        map.addMarker(new MarkerOptions()
                .position(latLng)
                .draggable(true)
                .title("Marker in India"));


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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        double lat = Double.parseDouble(selectedTask.getLatitude());
        double lon = Double.parseDouble(selectedTask.getLongitude());
        LatLng dest = new LatLng(lat, lon);

        if (map != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            map.setMyLocationEnabled(true);
            map.animateCamera(CameraUpdateFactory.zoomTo(16));

            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            String locationProvider = LocationManager.NETWORK_PROVIDER;
            // I suppressed the missing-permission warning because this wouldn't be executed in my
            // case without location services being enabled
            @SuppressLint("MissingPermission")
            android.location.Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
            double userLat = lastKnownLocation.getLatitude();
            double userLong = lastKnownLocation.getLongitude();
            MarkerOptions marker = new MarkerOptions();
            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                    .position(dest);
            map.moveCamera(CameraUpdateFactory.newLatLng(dest));
            map.animateCamera(CameraUpdateFactory.zoomTo(15));
            map.getUiSettings().setZoomControlsEnabled(true);
            latitude = userLat;
            longitude = userLong;

        }

//        String url = getDirectionsUrl(origin, dest);
//        getDirectionsUrl(origin, dest);
//        DownloadTask downloadTask = new DownloadTask();
// Start downloading json data from Google Directions API
//        downloadTask.execute(url);
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lon)));
        map.animateCamera(CameraUpdateFactory.zoomTo(10));
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }


    /**
     * Only when the app's target SDK is 23 or higher, it requests each dangerous permissions it
     * needs when the app is running.
     */
    private static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    /*@Override
    public void onBackPressed() {
        m_mapFragmentView.onBackPressed();
    }*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS: {
                for (int index = 0; index < permissions.length; index++) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {

                        /*
                         * If the user turned down the permission request in the past and chose the
                         * Don't ask again option in the permission request system dialog.
                         */
                        if (!ActivityCompat
                                .shouldShowRequestPermissionRationale(this, permissions[index])) {
                            Toast.makeText(this, "Required permission " + permissions[index]
                                            + " not granted. "
                                            + "Please go to settings and turn on for sample app",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(this, "Required permission " + permissions[index]
                                    + " not granted", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                setupMapFragmentView();
                break;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void setupMapFragmentView() {
        // All permission requests are being handled. Create map fragment view. Please note
        // the HERE Mobile SDK requires all permissions defined above to operate properly.
//        m_mapFragmentView = new MapFragmentView(this, taskInfoGroupByLocationKey);
    }

    private void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 5, (android.location.LocationListener) this);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (isNetworkEnabled) {

                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, (android.location.LocationListener) GmapTurnNavActivity.this);

                if (locationManager != null) {
                    location = locationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    if (location != null) {

                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        String lat = String.valueOf(latitude);
                        String logi = String.valueOf(longitude);

//
                    }
                }
                startActivity(intent);

            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
//        m_mapFragmentView.onDestroy();
        super.onDestroy();
        mWakeLock.release();
    }

}