package com.doozycod.axs.UpdateDebug.RouteMarker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.doozycod.axs.Database.Entities.TaskInfoEntity;
import com.doozycod.axs.Database.Repository.TaskInfoRepository;
import com.doozycod.axs.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapRouteActivity extends AppCompatActivity implements OnMapReadyCallback {
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private static final String[] RUNTIME_PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE
    };
    private MapFragmentView m_mapFragmentView;
    ArrayList<String> taskInfoEntityList = new ArrayList<>();
    ArrayList<String> routeList = new ArrayList<>();
    private TaskInfoRepository mTaskInfoRepository;
    List<TaskInfoEntity> taskInfoEntities;
    ArrayList<String> getRouteList;
    double lat, lon;
    String dcName;
    SupportMapFragment supportMapFragment;
    private GoogleMap mMap;
    List<String> routeNameList = new ArrayList<>();
    Spinner routeSpinner;
    private TextView markerTxt;
    List<LatLng> latLngList = new ArrayList<>();
    List<LatLng> geoCoordinates = new ArrayList<>();
    LatLngBounds.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_route);
        if (getIntent() != null) {
            taskInfoEntityList = getIntent().getStringArrayListExtra("list");
            getRouteList = getIntent().getStringArrayListExtra("routeList");
            dcName = getIntent().getStringExtra("dcName");
            lat = getIntent().getDoubleExtra("dcLat", 0);
            lon = getIntent().getDoubleExtra("dcLon", 0);

        }

        routeSpinner = findViewById(R.id.routeSpinner);
        markerTxt = findViewById(R.id.markerTxt);
        mTaskInfoRepository = new TaskInfoRepository(getApplication());
        taskInfoEntities = mTaskInfoRepository.getTaskInfos1();

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapfragment1);
        supportMapFragment.getMapAsync(this::onMapReady);


//        add to lat long
        for (int i = 0; i < getRouteList.size(); i++) {
            String[] latLong = getRouteList.get(i).split(",");
            double lat = Double.parseDouble(latLong[0]);
            double longi = Double.parseDouble(latLong[1]);
            latLngList.add(new LatLng(lat, longi));
//            Log.e(TAG, "createPolyline: " + geoCoordinates.get(i));
        }
//        latLngList

        routeNameList.add("   Show All");
        routeNameList.add("   Show DC");
        for (int i = 0; i < taskInfoEntityList.size(); i++) {
            routeNameList.add(" " + (i + 1) + ". " + taskInfoEntityList.get(i));
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, routeNameList);
        routeSpinner.setAdapter(arrayAdapter);

        routeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (supportMapFragment != null && mMap != null) {
                    if (i == 0) {
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 9));
//                        mMap.setZoomLevel(8.6, Map.Animation.BOW);
//                        m_map.setCenter(new GeoCoordinate(lat, lon), Map.Animation.BOW);
                        markerTxt.setVisibility(View.GONE);
                        LatLngBounds bounds = builder.build();

//        mMap.addMarker(new MarkerOptions()
//                .position(sydney)
//                .title("Marker in Sydney").icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(dc));


                        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 50);
                        mMap.moveCamera(cu);
                    }
                    if (i == 1) {
//                        mMap.setZoomLevel(14.3, Map.Animation.BOW);
//                        m_map.setCenter(new GeoCoordinate(lat, lon), Map.Animation.BOW);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 16));

                        Log.e("TAG", "onItemSelected: " + lat + " " + lon);
                        markerTxt.setVisibility(View.VISIBLE);
                        markerTxt.setText(dcName);
                    }
                    if (i > 1) {
                        markerTxt.setVisibility(View.VISIBLE);
                        i = i - 2;
//                        m_map.setZoomLevel(14.6, Map.Animation.BOW);
                        double lat = Double.parseDouble(taskInfoEntities.get(i).getLatitude());
                        double longi = Double.parseDouble(taskInfoEntities.get(i).getLongitude());
//                        m_map.setCenter(geoCoordinates.get(i)/*new GeoCoordinate(lat, longi)*/,
//                                Map.Animation.BOW);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(geoCoordinates.get(i), 16));

                        markerTxt.setText(routeSpinner.getSelectedItem().toString());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
//                m_map.setCenter(PositioningManager.getInstance().getLastKnownPosition().getCoordinate(),
//                        Map.Animation.BOW);
            }
        });


//        if (hasPermissions(this, RUNTIME_PERMISSIONS)) {
//            setupMapFragmentView();
//        } else {
//            ActivityCompat
//                    .requestPermissions(this, RUNTIME_PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
//        }
    }

    // Draw polyline on map
    public void drawPolyLineOnMap(List<LatLng> list) {
        PolylineOptions polyOptions = new PolylineOptions();
        polyOptions.color(Color.BLUE);
        polyOptions.width(10);
        polyOptions.addAll(list);

        mMap.clear();
        mMap.addPolyline(polyOptions);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : list) {
            builder.include(latLng);
        }

        final LatLngBounds bounds = builder.build();

        //BOUND_PADDING is an int to specify padding of bound.. try 100.
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 20);
        mMap.animateCamera(cu);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.e("TAG", "onMapReady: map added");
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        // Add a marker in Sydney and move the camera
        LatLng dc = new LatLng(lat, lon);


//        create polyline
        drawPolyLineOnMap(latLngList);
        builder = new LatLngBounds.Builder();
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon))
                .title(dcName)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
//
        for (int a = 0; a < routeNameList.size(); a++) {
            for (int i = 0; i < taskInfoEntities.size(); i++) {
                if (routeNameList.get(a).contains(taskInfoEntities.get(i).getName())) {
                    double longi = Double.parseDouble(taskInfoEntities.get(i).getLongitude());
                    double lat = Double.parseDouble(taskInfoEntities.get(i).getLatitude());
                    geoCoordinates.add(new LatLng(lat, longi));
                    mMap.addMarker(new MarkerOptions().position(new LatLng(lat, longi))
                            .title(taskInfoEntities.get(i).getName())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
                    builder.include(new LatLng(lat, longi));

                }
            }
        }
        LatLngBounds bounds = builder.build();

//        mMap.addMarker(new MarkerOptions()
//                .position(sydney)
//                .title("Marker in Sydney").icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(dc));


        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 50);
        mMap.moveCamera(cu);

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
//        m_mapFragmentView = new MapFragmentView(this, getRouteList, taskInfoEntityList,
//                taskInfoEntities, lon, lat, dcName);
    }


}