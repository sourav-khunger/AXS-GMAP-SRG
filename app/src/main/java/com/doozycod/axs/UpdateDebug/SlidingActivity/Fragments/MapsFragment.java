package com.doozycod.axs.UpdateDebug.SlidingActivity.Fragments;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.doozycod.axs.Database.Entities.TaskInfoEntity;
import com.doozycod.axs.Database.Repository.TaskInfoRepository;
import com.doozycod.axs.Database.ViewModel.TaskInfoViewModel;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapsFragment extends Fragment {
    private static final String TAG = MapsFragment.class.getSimpleName();

    private AppCompatActivity m_activity;
    private Map m_map;
    public static final String MAP_FRAGMENT_TITLE = "Map";
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private static final String[] RUNTIME_PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE
    };
    //    private GeoCoordinate geoCoordinate;
    List<TaskInfoEntity> taskInfoEntities = new ArrayList<>();
    private TaskInfoRepository mTaskInfoRepository;
    private TextView markerTxt;
    ImageView centerMap;
    TaskInfoViewModel taskInfoViewModel;

    private GoogleMap mMap;
    private List<LatLng> coordinates = new ArrayList<>();

    @Override
    public void onAttach(@NotNull Activity activity) {
        super.onAttach(activity);
        try {
            m_activity = (AppCompatActivity) activity;

        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: " + e);
        }
    }

    SupportMapFragment mapFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // private app's path
//        String path = new File(m_activity.getExternalFilesDir(null), ".here-map-data")
//                .getAbsolutePath();
//        // This method will throw IllegalArgumentException if provided path is not writable
//        com.here.android.mpa.common.MapSettings.setDiskCacheRootPath(path);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        initUI(view);

        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.gmap);

        if (mapFragment != null) {
            Log.e(TAG, "onCreateView: not null");
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mMap = googleMap;
                    Log.e(TAG, "onMapReady: map added");
                    mMap.getUiSettings().setCompassEnabled(true);
                    mMap.getUiSettings().setRotateGesturesEnabled(true);
                    // Add a marker in Sydney and move the camera
                  /*  LatLng sydney = new LatLng(-34, 151);

                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
                }
            });
        }

//        m_mapFragment = new AndroidXMapFragment();
//        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mapfragment1, m_mapFragment).commit();

//        initMapFragment();

        return view;
    }


    void initUI(View v) {
//        m_naviControlButton = (Button) m_activity.findViewById(R.id.naviCtrlButton);
//        mapView = v.findViewById(R.id.here_map);
//        centerMap = v.findViewById(R.id.centerMap);

//        mTaskInfoRepository = new TaskInfoRepository(getActivity().getApplication());
//        taskInfoEntities = mTaskInfoRepository.getTaskInfos1();


        taskInfoViewModel = new ViewModelProvider(m_activity).get(TaskInfoViewModel.class);

        taskInfoViewModel.getTaskInfoList().observe(m_activity, new Observer<List<TaskInfoEntity>>() {
            @Override
            public void onChanged(List<TaskInfoEntity> taskInfoEnt) {
//                Log.e(TAG, "onChanged: " + taskInfoEnt.size());
                taskInfoEntities.clear();
                taskInfoEntities.addAll(taskInfoEnt);
                for (int i = 0; i < taskInfoEntities.size(); i++) {
                    double longi = Double.parseDouble(taskInfoEntities.get(i).getLongitude());
                    double lat = Double.parseDouble(taskInfoEntities.get(i).getLatitude());
                    coordinates.add(new LatLng(lat, longi));
                }

                if (taskInfoEntities.size() > 0) {

                    double longi = Double.parseDouble(taskInfoEntities.get(0).getLongitude());
                    double lat = Double.parseDouble(taskInfoEntities.get(0).getLatitude());
//                    geoCoordinate = new GeoCoordinate(lat, longi);
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(lat, longi)).zoom(9).build();

                    mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    drawPolyLineOnMap(coordinates, taskInfoEntities);

                } else {

                }

//                MapEngine.getInstance().init(appContext, MapsFragment.this);

//                Collections.sort(taskInfoEntities, new Comparator<TaskInfoEntity>() {
//                    public int compare(TaskInfoEntity obj1, TaskInfoEntity obj2) {
//                        // ## Ascending order
//                        return Integer.valueOf(obj1.getSeqNo()).compareTo(obj2.getSeqNo());// To compare string values
//                        // return Integer.valueOf(obj1.empId).compareTo(Integer.valueOf(obj2.empId)); // To compare integer values
//
//                        // ## Descending order
//                        // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
//                        // return Integer.valueOf(obj2.empId).compareTo(Integer.valueOf(obj1.empId)); // To compare integer values
//                    }
//                });
            }
        });
  /*      routeSelectionList.add("    Show All   ");
        routeSelectionList.add("    Show DC   ");
        for (int i = 0; i < taskInfoEntities.size(); i++) {

            routeSelectionList.add(" " + (i + 1) + ". " + taskInfoEntities.get(i).getName());
        }*/

//        centerMap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                m_map.setZoomLevel(13, Map.Animation.BOW);
//                m_map.setCenter(PositioningManager.getInstance().getLastKnownPosition().getCoordinate(),
//                        Map.Animation.BOW);
//            }
//        });
//        if (m_map != null && !PositioningManager.getInstance().isActive()) {
//            PositioningManager.getInstance().start(PositioningManager.LocationMethod.GPS_NETWORK); // use gps plus cell and wifi
//        }
    }


    /**
     * create a MapCircle and add the MapCircle to active map view.
     */


    // Draw polyline on map
    public void drawPolyLineOnMap(List<LatLng> list, List<TaskInfoEntity> taskInfoEntities) {
//        PolylineOptions polyOptions = new PolylineOptions();
//        polyOptions.color(Color.BLUE);
//        polyOptions.width(10);
//        polyOptions.addAll(list);

        mMap.clear();
//        mMap.addPolyline(polyOptions);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (int i = 0; i < taskInfoEntities.size(); i++) {
            IconGenerator iconFactory = new IconGenerator(m_activity);
            // iconFactory.setColor(Color.RED);
            iconFactory.setStyle(IconGenerator.STYLE_RED);

            MarkerOptions markerOptions = new MarkerOptions()
                    .title((i + 1) + ". " + taskInfoEntities.get(i).getName())
                    .icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon((i + 1) + ""))).
                            position(list.get(i)).
                            anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());

            mMap.addMarker(markerOptions);
            builder.include(markerOptions.getPosition());
//            mMap.addMarker(new MarkerOptions()
//                    .position(latLng).icon(BitmapDescriptorFactory
//                            .defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
        }

        final LatLngBounds bounds = builder.build();
        //BOUND_PADDING is an int to specify padding of bound.. try 100.
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 50);
        mMap.animateCamera(cu);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
//        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
//        mapView.;
    }

    /* @RequiresApi(api = Build.VERSION_CODES.O)
     @Override
     public void onEngineInitializationCompleted(Error error) {
         if (error == Error.NONE) {
             *//*
     * If no error returned from map fragment initialization, the map will be
     * rendered on screen at this moment.Further actions on map can be provided
     * by calling Map APIs.
     *//*
            m_map = new Map();

            mapView.setMap(m_map);
            double longi, lat;
            if (taskInfoEntities.size() > 0) {

                longi = Double.parseDouble(taskInfoEntities.get(0).getLongitude());
                lat = Double.parseDouble(taskInfoEntities.get(0).getLatitude());
                geoCoordinate = new GeoCoordinate(lat, longi);
            } else {
                geoCoordinate = PositioningManager.getInstance().getLastKnownPosition().getCoordinate();
            }


            m_map.setCenter(geoCoordinate, Map.Animation.NONE);
            *//* Set the zoom level to the average between min and max zoom level. *//*
            m_map.setZoomLevel(8.6);

            m_activity.supportInvalidateOptionsMenu();
            mapView.getMapGesture().addOnGestureListener(onGestureListener, 0, false);
//                        m_map =
            *//*MapPolyline mapPolyline =*//*
            String jsonLoginResponse = PreferenceManager.getDefaultSharedPreferences(m_activity).getString(Constants.PREF_KEY_LOGIN_RESPONSE, "");
            LoginResponse loginResponse = new Gson().fromJson(jsonLoginResponse, LoginResponse.class);
            int isOnduty = loginResponse.getDriverInfo().getOnDuty();

            if (isOnduty == 1) {
                createPolyline();
            }

//          m_map.addMapObject(mapPolyline);
            *//*
     * Set up a handler for handling MapMarker drag events.
     *//*
            mapView.setMapMarkerDragListener(new OnDragListenerHandler());

        } else {
            Log.e(this.getClass().toString(), "onEngineInitializationCompleted: " +
                    "ERROR=" + error.getDetails(), error.getThrowable());
            new AlertDialog.Builder(m_activity).setMessage(
                    "Error : " + error.name() + "\n\n" + error.getDetails())
                    .setTitle(R.string.engine_init_error)
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(
                                        DialogInterface dialog,
                                        int which) {
                                    m_activity.finish();
                                }
                            }).create().show();
        }
    }*/
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}