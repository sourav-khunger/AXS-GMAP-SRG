package com.doozycod.axs.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.here.android.mpa.common.ApplicationContext;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.Image;
import com.here.android.mpa.common.MapEngine;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.common.PositioningManager;
import com.here.android.mpa.common.ViewObject;
import com.here.android.mpa.mapping.AndroidXMapFragment;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapGesture;
import com.here.android.mpa.mapping.MapLabeledMarker;
import com.here.android.mpa.mapping.MapMarker;
import com.here.android.mpa.mapping.MapObject;
import com.here.android.mpa.mapping.MapRoute;
import com.here.android.mpa.mapping.MapView;
import com.here.android.mpa.routing.Route;
import com.doozycod.axs.BackgroudService.Workers.LocationService;
import com.doozycod.axs.Database.Entities.TaskInfoEntity;
import com.doozycod.axs.Database.Repository.TaskInfoRepository;
import com.doozycod.axs.Database.ViewModel.TaskInfoViewModel;
import com.doozycod.axs.POJO.LoginResponse;
import com.doozycod.axs.R;
import com.doozycod.axs.Utils.Constants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsFragment extends Fragment implements OnEngineInitListener {
    private static final String TAG = MapsFragment.class.getSimpleName();

    private AndroidXMapFragment m_mapFragment;
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
    private MapFragmentView m_mapFragmentView;
    private Route m_route;
    MapRoute mapRoute;
    private GeoCoordinate geoCoordinate;
    List<TaskInfoEntity> taskInfoEntities = new ArrayList<>();
    private TaskInfoRepository mTaskInfoRepository;
    private TextView markerTxt;
    ImageView centerMap;
    MapView mapView;
    TaskInfoViewModel taskInfoViewModel;
    ApplicationContext appContext;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            m_activity = (AppCompatActivity) activity;

        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: " + e);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appContext = new ApplicationContext(m_activity.getApplicationContext());
        // private app's path
        String path = new File(m_activity.getExternalFilesDir(null), ".here-map-data")
                .getAbsolutePath();
        // This method will throw IllegalArgumentException if provided path is not writable
        com.here.android.mpa.common.MapSettings.setDiskCacheRootPath(path);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        initUI(view);
//        m_mapFragment = new AndroidXMapFragment();
//        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.mapfragment1, m_mapFragment).commit();

//        initMapFragment();


        return view;
    }


    void initUI(View v) {
//        m_naviControlButton = (Button) m_activity.findViewById(R.id.naviCtrlButton);
        mapView = v.findViewById(R.id.here_map);
        centerMap = v.findViewById(R.id.centerMap);

//        mTaskInfoRepository = new TaskInfoRepository(getActivity().getApplication());
//        taskInfoEntities = mTaskInfoRepository.getTaskInfos1();


        taskInfoViewModel = new ViewModelProvider(m_activity).get(TaskInfoViewModel.class);

        taskInfoViewModel.getTaskInfoList().observe(m_activity, new Observer<List<TaskInfoEntity>>() {
            @Override
            public void onChanged(List<TaskInfoEntity> taskInfoEnt) {
                Log.e(TAG, "onChanged: " + taskInfoEnt.size());
                taskInfoEntities.clear();
                taskInfoEntities.addAll(taskInfoEnt);
                MapEngine.getInstance().init(appContext, MapsFragment.this);

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

        centerMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_map.setZoomLevel(13, Map.Animation.BOW);
                m_map.setCenter(PositioningManager.getInstance().getLastKnownPosition().getCoordinate(),
                        Map.Animation.BOW);
            }
        });
        if (m_map != null && !PositioningManager.getInstance().isActive()) {
            PositioningManager.getInstance().start(PositioningManager.LocationMethod.GPS_NETWORK); // use gps plus cell and wifi
        }
    }

    private AndroidXMapFragment getMapFragment() {
        return (AndroidXMapFragment) m_activity.getSupportFragmentManager().findFragmentById(R.id.mapfragment1);
    }


    private MapGesture.OnGestureListener onGestureListener = new MapGesture.OnGestureListener() {
        @Override
        public void onPanStart() {

        }

        @Override
        public void onPanEnd() {

        }

        @Override
        public void onMultiFingerManipulationStart() {

        }

        @Override
        public void onMultiFingerManipulationEnd() {

        }

        @Override
        public boolean onMapObjectsSelected(@NonNull List<ViewObject> list) {
            for (ViewObject viewObject : list) {
                if (viewObject.getBaseType() == ViewObject.Type.USER_OBJECT) {
                    MapObject mapObject = (MapObject) viewObject;

                    if (mapObject.getType() == MapObject.Type.MARKER) {

                        MapMarker window_marker = ((MapMarker) mapObject);
                        m_map.setCenter(window_marker.getCoordinate(), Map.Animation.BOW);
//                        showDialog(window_marker.getTitle());
                        Log.e("MARKER", "Title is................." + window_marker.getTitle());
                        markerTxt.setText(window_marker.getTitle());
                        return false;
                    }
                }
            }
            return false;
        }

        @Override
        public boolean onTapEvent(@NonNull PointF pointF) {
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(@NonNull PointF pointF) {
            return false;
        }

        @Override
        public void onPinchLocked() {

        }

        @Override
        public boolean onPinchZoomEvent(float v, @NonNull PointF pointF) {
            return false;
        }

        @Override
        public void onRotateLocked() {

        }

        @Override
        public boolean onRotateEvent(float v) {
            return false;
        }

        @Override
        public boolean onTiltEvent(float v) {
            return false;
        }

        @Override
        public boolean onLongPressEvent(@NonNull PointF pointF) {
            return false;
        }

        @Override
        public void onLongPressRelease() {

        }

        @Override
        public boolean onTwoFingerTapEvent(@NonNull PointF pointF) {
            return false;
        }
    };

    /**
     * create a MapCircle and add the MapCircle to active map view.
     */


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createPolyline() {
        ArrayList<GeoCoordinate> coordinates = new ArrayList<>();
        for (int i = 0; i < taskInfoEntities.size(); i++) {
            double longi = Double.parseDouble(taskInfoEntities.get(i).getLongitude());
            double lat = Double.parseDouble(taskInfoEntities.get(i).getLatitude());
            coordinates.add(new GeoCoordinate(lat, longi));
//            Log.e(TAG, "createPolyline: " + coordinates.get(i));
        }
//        coordinates.add(new GeoCoordinate(29.9753092, 77.571972));
//        coordinates.add(new GeoCoordinate(29.9685473, 77.5644495));
//        coordinates.add(new GeoCoordinate(29.9623858, 77.5483282));
//        coordinates.add(new GeoCoordinate(29.9701266, 77.5698228));

        /* Initialize a CoreRouter */
        /*CoreRouter coreRouter = new CoreRouter();

         *//* Initialize a RoutePlan *//*
        RoutePlan routePlan = new RoutePlan();
        RouteOptions routeOptions = new RouteOptions();
        *//* Other transport modes are also available e.g Pedestrian */
        /*
        routeOptions.setTransportMode(RouteOptions.TransportMode.CAR);
        */
        /* Disable highway in this route. *//*
        routeOptions.setHighwaysAllowed(false);
        */
        /* Calculate the shortest route available. *//*
        routeOptions.setRouteType(RouteOptions.Type.SHORTEST);
        */
        /* Calculate 1 route. *//*
        routeOptions.setRouteCount(1);
        */
        /* Finally set the route option */

        /*routePlan.setRouteOptions(routeOptions);*/

//The following is needed because of image acceleration in some devices such as samsung
//        Image image = new Image();
//        try {
//            image.setImageResource(R.drawable.pin);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Image image = new Image();
        try {

            image.setImageResource(R.drawable.pin);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        List<RouteWaypoint> routeWaypoints = new ArrayList<>();
        for (int i = 0; i < coordinates.size(); i++) {
//            SVG svg = SVGParser.getSVGFromString();


            Log.e(TAG, "createPolyline: " + coordinates.get(i));
            MapLabeledMarker defaultMarker = new MapLabeledMarker(coordinates.get(i), image);
            defaultMarker.setLabelText("eng", "" + (i + 1));
            defaultMarker.setFontScalingFactor(4f);
            defaultMarker.setAnchorPoint(new PointF(image.getWidth() / 2, image.getHeight()));
//            defaultMarker.setCoordinate(coordinates.get(i));
            defaultMarker.setTitle(taskInfoEntities.get(i).getName());
            defaultMarker.setVisible(true);
            m_map.addMapObject(defaultMarker);
        }

//        coreRouter.calculateRoute(coordinates, routeOptions, new Router.Listener<List<RouteResult>, RoutingError>() {
//            @Override
//            public void onProgress(int i) {
//
//            }
//
//            @Override
//            public void onCalculateRouteFinished(@Nullable List<RouteResult> routeResults, @NonNull RoutingError routingError) {
//                if (routingError == RoutingError.NONE) {
////                    m_route = routeResults.get(0).getRoute();
////                    mapRoute = new MapRoute(m_route);
////                    m_map.addMapObject(mapRoute);
//                }
//            }
//        });
    }


    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onEngineInitializationCompleted(Error error) {
        if (error == Error.NONE) {
            /*
             * If no error returned from map fragment initialization, the map will be
             * rendered on screen at this moment.Further actions on map can be provided
             * by calling Map APIs.
             */
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
            /* Set the zoom level to the average between min and max zoom level. */
            m_map.setZoomLevel(8.6);

            m_activity.supportInvalidateOptionsMenu();
            mapView.getMapGesture().addOnGestureListener(onGestureListener, 0, false);
//                        m_map =
            /*MapPolyline mapPolyline =*/
            String jsonLoginResponse = PreferenceManager.getDefaultSharedPreferences(m_activity).getString(Constants.PREF_KEY_LOGIN_RESPONSE, "");
            LoginResponse loginResponse = new Gson().fromJson(jsonLoginResponse, LoginResponse.class);
            int isOnduty = loginResponse.getDriverInfo().getOnDuty();

            if (isOnduty == 1) {
                createPolyline();
            }

//          m_map.addMapObject(mapPolyline);
            /*
             * Set up a handler for handling MapMarker drag events.
             */
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
    }

    private static class OnDragListenerHandler implements MapMarker.OnDragListener {
        @Override
        public void onMarkerDrag(MapMarker mapMarker) {

            Log.e(TAG, "onMarkerDrag: " + mapMarker.getTitle() + " -> " + mapMarker
                    .getCoordinate());
        }

        @Override
        public void onMarkerDragEnd(MapMarker mapMarker) {
            Log.e(TAG, "onMarkerDragEnd: " + mapMarker.getTitle() + " -> " + mapMarker
                    .getCoordinate());
        }

        @Override
        public void onMarkerDragStart(MapMarker mapMarker) {
            Log.e(TAG, "onMarkerDragStart: " + mapMarker.getTitle() + " -> " + mapMarker
                    .getCoordinate());
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initMapFragment() {
        /* Locate the mapFragment UI element */
        m_mapFragment = getMapFragment();

        // This will use external storage to save map cache data, it is also possible to set
        // private app's path
        String path = new File(m_activity.getExternalFilesDir(null), ".here-map-data")
                .getAbsolutePath();
        // This method will throw IllegalArgumentException if provided path is not writable
        com.here.android.mpa.common.MapSettings.setDiskCacheRootPath(path);

        if (m_mapFragment != null) {
            /* Initialize the AndroidXMapFragment, results will be given via the called back. */
            m_mapFragment.init(new OnEngineInitListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onEngineInitializationCompleted(Error error) {

                    if (error == Error.NONE) {
                        /*
                         * If no error returned from map fragment initialization, the map will be
                         * rendered on screen at this moment.Further actions on map can be provided
                         * by calling Map APIs.
                         */
                        m_map = m_mapFragment.getMap();


                        /*
                         * Set the map center to the 4350 Still Creek Dr Burnaby BC (no animation).
                         */
                        geoCoordinate = new GeoCoordinate(LocationService.latitude, LocationService.longitude);
                        geoCoordinate = PositioningManager.getInstance().getLastKnownPosition().getCoordinate();
                        m_map.setCenter(geoCoordinate,
                                Map.Animation.BOW);

                        /* Set the zoom level to the average between min and max zoom level. */
                        m_map.setZoomLevel(8.7);

                        m_activity.supportInvalidateOptionsMenu();
                        m_mapFragment.getMapGesture().addOnGestureListener(onGestureListener, 0, false);
//                        m_map =
                        /*MapPolyline mapPolyline =*/
                        createPolyline();
//                        m_map.addMapObject(mapPolyline);
                        /*
                         * Set up a handler for handling MapMarker drag events.
                         */

                        m_mapFragment.setMapMarkerDragListener(new OnDragListenerHandler());

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
                }
            });
        }
    }

}