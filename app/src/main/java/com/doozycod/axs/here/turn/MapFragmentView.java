package com.doozycod.axs.here.turn;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.here.android.mpa.ar.ARController;
import com.here.android.mpa.common.GeoBoundingBox;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.GeoPosition;
import com.here.android.mpa.common.Image;
import com.here.android.mpa.common.MapEngine;
import com.here.android.mpa.common.MatchedGeoPosition;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.common.PositioningManager;
import com.here.android.mpa.common.ViewObject;
import com.here.android.mpa.guidance.NavigationManager;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.AndroidXMapFragment;
import com.here.android.mpa.mapping.MapGesture;
import com.here.android.mpa.mapping.MapLocalModel;
import com.here.android.mpa.mapping.MapMarker;
import com.here.android.mpa.mapping.MapRoute;
import com.here.android.mpa.mapping.MapState;
import com.here.android.mpa.mapping.MapView;
import com.here.android.mpa.mapping.OnMapRenderListener;
import com.here.android.mpa.prefetcher.MapDataPrefetcher;
import com.here.android.mpa.routing.CoreRouter;
import com.here.android.mpa.routing.Maneuver;
import com.here.android.mpa.routing.Route;
import com.here.android.mpa.routing.RouteOptions;
import com.here.android.mpa.routing.RoutePlan;
import com.here.android.mpa.routing.RouteResult;
import com.here.android.mpa.routing.RouteWaypoint;
import com.here.android.mpa.routing.Router;
import com.here.android.mpa.routing.RoutingError;
import com.doozycod.axs.Activity.ShowListOfTaskGroupByLocationKeyActivity;
import com.doozycod.axs.BackgroudService.Workers.LocationService;
import com.doozycod.axs.Database.Repository.TaskInfoRepository;
import com.doozycod.axs.POJO.TaskInfoGroupByLocationKey;
import com.doozycod.axs.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigator;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PointF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationProvider;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

/**
 * This class encapsulates the properties and functionality of the Map view.It also triggers a
 * turn-by-turn navigation from HERE Burnaby office to Langley BC.There is a sample voice skin
 * bundled within the SDK package to be used out-of-box, please refer to the Developer's guide for
 * the usage.
 */
public class MapFragmentView {
    private AndroidXMapFragment m_mapFragment;
    private AppCompatActivity m_activity;
    private Button m_naviControlButton, m_arrivedButton;
    boolean isArrived = false;
    TextView distanceTxt, speedTxt, turnTxt, nxtDisTxt;
    ImageView turnIV, compass;
    private Map m_map;
    private NavigationManager m_navigationManager;
    private GeoBoundingBox m_geoBoundingBox;
    private Route m_route;
    private boolean m_foregroundServiceStarted;
    private TaskInfoGroupByLocationKey m_taskInfoGroupByLocationKey;
    GeoCoordinate geoCoordinate;
    Maneuver maneuver;
    private PositioningManager positioningManager = null;
    private PositioningManager.OnPositionChangedListener positionListener;
    private boolean fetchingDataInProgress = false;
    private double m_lastZoomLevelInRoadViewMode = 0.0;
    private PointF m_mapTransformCenter;
    private MapRoute m_currentRoute;
    private MapMarker m_positionIndicatorFixed = null;

    // helper for the very first fix after startup (we want to jump to that position then)
    private boolean firstPositionSet = false;
    private boolean m_returningToRoadViewMode = false;

    // custom position marker
//    private MapCircle m_PositionMarker;
//    private MapCircle m_PositionAccuracyIndicator;

    private MapLocalModel m_PositionMesh;

    // compass sensors
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mMagnetometer;
    MapRoute mapRoute;
    // compass data
    private float mAzimuth;
    private float[] mGravity = new float[3];
    private float[] mGeomagnetic = new float[3];
    // listen for sensor updates
    private SensorEventListener sensorHandler = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            final float alpha = (float) 0.8;
            synchronized (this) {

                if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    // Isolate the force of gravity with the low-pass filter. See Android documentation for details:
                    // http://developer.android.com/guide/topics/sensors/sensors_motion.html#sensors-motion-accel
                    mGravity[0] = alpha * mGravity[0] + (1 - alpha) * sensorEvent.values[0];
                    mGravity[1] = alpha * mGravity[1] + (1 - alpha) * sensorEvent.values[1];
                    mGravity[2] = alpha * mGravity[2] + (1 - alpha) * sensorEvent.values[2];
                }

                if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                    mGeomagnetic = sensorEvent.values.clone();
                }
            }

            if (mGravity != null && mGeomagnetic != null) {
                float R[] = new float[9];
                float I[] = new float[9];

                if (SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic)) {
                    float[] mOrientation = new float[3];
                    SensorManager.getOrientation(R, mOrientation); // mOrientation contains: azimuth, pitch and roll

                    mAzimuth = (float) Math.toDegrees(mOrientation[0]);
                    //float mPitch = (float) Math.toDegrees(mOrientation[1]);
                    //float mRoll = (float) Math.toDegrees(mOrientation[2]);
                    //float mInclination = (float) Math.toDegrees(SensorManager.getInclination(I));

                    if (mAzimuth < 0.0f) {
                        mAzimuth += 360.0f;
                    }

//                    Log.v(TAG, "Rotate to " + mAzimuth);

                    // set yaw of our 3D position indicator to indicate compass direction
                    if (m_PositionMesh != null)
                        m_PositionMesh.setYaw(-mAzimuth);
//                    m_map.setOrientation(-mAzimuth);
                    // Think about animation and less updates here in production environments
                }
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
            Log.d(TAG, "Accuracy changed for " + sensor.getName() + " to " + i);
        }
    };
    // listen for positioning events
    private PositioningManager.OnPositionChangedListener mapPositionHandler = new PositioningManager.OnPositionChangedListener() {
        @Override
        public void onPositionUpdated(PositioningManager.LocationMethod method, GeoPosition position, boolean isMapMatched) {

            if (!position.isValid())
                return;

            if (!firstPositionSet) {
                m_map.setCenter(position.getCoordinate(), Map.Animation.BOW);
                firstPositionSet = true;
            }

            // get the new coordinate
            GeoCoordinate pos = position.getCoordinate();

            // set custom position indicator and accuracy indicator
//            m_PositionMarker.setCenter(pos);
//            m_PositionMesh.setAnchor(pos);
//            m_PositionAccuracyIndicator.setCenter(pos);
//            m_PositionAccuracyIndicator.setRadius(position.getLatitudeAccuracy());
        }

        @Override
        public void onPositionFixChanged(PositioningManager.LocationMethod method, PositioningManager.LocationStatus status) {
            Log.i(TAG, "Position fix changed : " + status.name() + " / " + method.name());
        }
    };
    ARController arController;
    MapView mapView;
    Navigator navigator;
    LocationProvider locationProvider;

    public MapFragmentView(AppCompatActivity activity, TaskInfoGroupByLocationKey taskInfoGroupByLocationKey) {
        m_activity = activity;
        m_taskInfoGroupByLocationKey = taskInfoGroupByLocationKey;
        initMapFragment();
        initNaviControlButton();

        //LocationService.latitude = 45.4867726;
        //LocationService.longitude =-73.6444014;
    }

    MapDataPrefetcher.Adapter prefetcherListener = new MapDataPrefetcher.Adapter() {
        @Override
        public void onStatus(int requestId, PrefetchStatus status) {
            if (status != PrefetchStatus.PREFETCH_IN_PROGRESS) {
                fetchingDataInProgress = false;
            }
        }
    };

    private int meterPerSecToKmPerHour(double speed) {
        return (int) (speed * 3.6);
    }

    public void startListeners() {
        PositioningManager.getInstance().addListener(new WeakReference<>(positionLister));
        MapDataPrefetcher.getInstance().addListener(prefetcherListener);
    }

    private AndroidXMapFragment getMapFragment() {
        return (AndroidXMapFragment) m_activity.getSupportFragmentManager().findFragmentById(R.id.mapfragment);
    }

    private void initMapFragment() {
        /* Locate the mapFragment UI element */
        m_mapFragment = getMapFragment();
        String path = new File(m_activity.getExternalFilesDir(null), ".here-map-data")
                .getAbsolutePath();
        // This method will throw IllegalArgumentException if provided path is not writable
        com.here.android.mpa.common.MapSettings.setDiskCacheRootPath(path);
//        MapImage mapImage = MapImageFactory.fromResource(getApplicationContext().getResources(), R.drawable.home);
        if (m_mapFragment != null) {
            /* Initialize the AndroidXMapFragment, results will be given via the called back. */
            m_mapFragment.init(new OnEngineInitListener() {
                @Override
                public void onEngineInitializationCompleted(OnEngineInitListener.Error error) {

                    if (error == Error.NONE) {
                        m_map = m_mapFragment.getMap();
                        geoCoordinate = new GeoCoordinate(LocationService.latitude, LocationService.longitude);
                        m_mapFragment.getMapGesture().addOnGestureListener(gestureListener, 100, true);

                        m_map.setCenter(geoCoordinate,
                                Map.Animation.BOW);
//                        mapView.setMap(m_map);

                        //Put this call in Map.onTransformListener if the animation(Linear/Bow)
                        //is used in setCenter()
                        m_map.setZoomLevel(16);
//                        m_map.setTrafficInfoVisible(true);
//                        m_mapFragment.getMapGesture().setRotateEnabled(true);
                        // the arController is also ready to be used now
//                        arController = m_mapFragment.getARController();
                        m_mapFragment.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                float orient = m_map.getOrientation() + 0.0F;
                                compass.setRotation(orient);
                                compass.animate();
                                return false;
                            }
                        });
                        m_map.addTransformListener(new Map.OnTransformListener() {
                            @Override
                            public void onMapTransformStart() {
                                float orient = m_map.getOrientation() + 0.0F;
                                compass.setRotation(orient);
                                compass.animate();

                            }

                            @Override
                            public void onMapTransformEnd(MapState mapState) {

                            }
                        });

                        m_mapFragment.addOnMapRenderListener(new OnMapRenderListener() {
                            @Override
                            public void onPreDraw() {
                                if (m_positionIndicatorFixed != null) {
                                    if (NavigationManager.getInstance()
                                            .getMapUpdateMode().equals(NavigationManager
                                                    .MapUpdateMode.ROADVIEW)) {
                                        if (!m_returningToRoadViewMode) {
                                            // when road view is active, we set the position indicator to align
                                            // with the current map transform center to synchronize map and map
                                            // marker movements.
                                            m_positionIndicatorFixed
                                                    .setCoordinate(m_map.pixelToGeo(m_mapTransformCenter));
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onPostDraw(boolean var1, long var2) {
                            }

                            @Override
                            public void onSizeChanged(int var1, int var2) {
                            }

                            @Override
                            public void onGraphicsDetached() {
                            }

                            @Override
                            public void onRenderBufferCreated() {
                            }
                        });

//                        Image img = new Image();
//                        try {
//                            img.setImageResource(R.drawable.pickup);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }


                        // setup positioning and event listener
                        // start() is also called in onResume() but this can't be called before init is done, so we also do it after initialization
                        PositioningManager.getInstance().addListener(new WeakReference<>(mapPositionHandler));
                        PositioningManager.getInstance().start(PositioningManager.LocationMethod.GPS_NETWORK);
                        // use gps plus cell and wifi

                        // set to last known position, if available
                        GeoPosition lkp = PositioningManager.getInstance().getLastKnownPosition();
//                        MapMarker mapMarker = new MapMarker(lkp.getCoordinate(), img);
//                        m_map.addMapObject(mapMarker);
                        m_map.getPositionIndicator().setVisible(true);
//                        m_PositionMesh.setTexture(img);
//                        m_map.getPositionIndicator().setMarker(img);

                        m_navigationManager = NavigationManager.getInstance();
//                        m_mapFragment.getARController()..setVisible(true);


                    } else {
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


    private MapGesture.OnGestureListener gestureListener = new MapGesture.OnGestureListener() {
        @Override
        public void onPanStart() {
            pauseRoadView();
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
        public boolean onMapObjectsSelected(List<ViewObject> objects) {
            return false;
        }

        @Override
        public boolean onTapEvent(PointF p) {
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(PointF p) {
            return false;
        }

        @Override
        public void onPinchLocked() {
        }

        @Override
        public boolean onPinchZoomEvent(float scaleFactor, PointF p) {
            pauseRoadView();
            return false;
        }

        @Override
        public void onRotateLocked() {
        }

        @Override
        public boolean onRotateEvent(float rotateAngle) {
            return false;
        }

        @Override
        public boolean onTiltEvent(float angle) {
            pauseRoadView();
            return false;
        }

        @Override
        public boolean onLongPressEvent(PointF p) {
            return false;
        }

        @Override
        public void onLongPressRelease() {
        }

        @Override
        public boolean onTwoFingerTapEvent(PointF p) {
            return false;
        }
    };

    // Resume positioning listener on wake up
//    @Override
//    public void onResume() {
//        super.onResume();
//
//        Log.d(TAG, "onResume");
//
//        // See: http://stackoverflow.com/questions/17337504/need-to-read-android-sensors-with-fixed-sampling-rate
//
//    }

    private void createRoute() {
        /* Initialize a CoreRouter */
        CoreRouter coreRouter = new CoreRouter();

        /* Initialize a RoutePlan */
        RoutePlan routePlan = new RoutePlan();

        /*
         * Initialize a RouteOption. HERE Mobile SDK allow users to define their own parameters for the
         * route calculation,including transport modes,route types and route restrictions etc.Please
         * refer to API doc for full list of APIs
         */
        RouteOptions routeOptions = new RouteOptions();
        /* Other transport modes are also available e.g Pedestrian */
        routeOptions.setTransportMode(RouteOptions.TransportMode.CAR);
        /* Disable highway in this route. */
        routeOptions.setHighwaysAllowed(false);
        /* Calculate the shortest route available. */
        routeOptions.setRouteType(RouteOptions.Type.SHORTEST);
        /* Calculate 1 route. */
        routeOptions.setRouteCount(1);
        /* Finally set the route option */
        routePlan.setRouteOptions(routeOptions);

        /* Define waypoints for the route */
        /* START: 4350 Still Creek Dr */
        // RouteWaypoint startPoint = new RouteWaypoint(new GeoCoordinate(49.259149, -123.008555));

        GeoPosition geoPosition = PositioningManager.getInstance().getLastKnownPosition();
        RouteWaypoint startPoint = new RouteWaypoint(
                geoPosition.getCoordinate()/*new GeoCoordinate(LocationService.latitude, LocationService.longitude)*/);
        /* END: Langley BC */

        double destLat = 0;
        double destLon = 0;
        try {
            destLat = Double.parseDouble(m_taskInfoGroupByLocationKey.getLatitude());
            destLon = Double.parseDouble(m_taskInfoGroupByLocationKey.getLongitude());
        } catch (Exception e) {
            Toast.makeText(m_activity, "Lat Lon Invalid", Toast.LENGTH_SHORT).show();
        }

        RouteWaypoint destination = new RouteWaypoint(new GeoCoordinate(destLat, destLon));

        /* Add both waypoints to the route plan */
        routePlan.addWaypoint(startPoint);
        routePlan.addWaypoint(destination);
        /* Trigger the route calculation,results will be called back via the listener */
        coreRouter.calculateRoute(routePlan,
                new Router.Listener<List<RouteResult>, RoutingError>() {

                    @Override
                    public void onProgress(int i) {
                        /* The calculation progress can be retrieved in this callback. */
                    }

                    @Override
                    public void onCalculateRouteFinished(List<RouteResult> routeResults,
                                                         RoutingError routingError) {
                        if (routingError == RoutingError.NONE) {
                            m_route = routeResults.get(0).getRoute();

                            mapRoute = new MapRoute(m_route);
                            m_map.addMapObject(mapRoute);

                            // move the map to the first waypoint which is starting point of
                            // the route
                            m_map.setCenter(routePlan.getWaypoint(0).getNavigablePosition(),
                                    Map.Animation.NONE);

                            // setting MapUpdateMode to RoadView will enable automatic map
                            // movements and zoom level adjustments
                            NavigationManager navigationManager =
                                    NavigationManager.getInstance();
                            navigationManager.setMapUpdateMode(
                                    NavigationManager.MapUpdateMode.ROADVIEW);

                            // adjust tilt to show 3D view
                            m_map.setTilt(70);

                            // adjust transform center for navigation experience in portrait
                            // view
                            m_mapTransformCenter = new PointF(m_map.getTransformCenter().x, (m_map
                                    .getTransformCenter().y * 85 / 50));
                            m_map.setTransformCenter(m_mapTransformCenter);
                            ;
                            /* Show the maneuver number on top of the route */
                            mapRoute.setManeuverNumberVisible(true);

                            /* Add the MapRoute to the map */
                            m_map.addMapObject(mapRoute);

                            /*
                             * We may also want to make sure the map view is orientated properly
                             * so the entire route can be easily seen.
                             */
                            m_geoBoundingBox = routeResults.get(0).getRoute().getBoundingBox();
                            m_map.zoomTo(m_geoBoundingBox, Map.Animation.BOW,
                                    Map.MOVE_PRESERVE_ORIENTATION);
                            if (routingError == RoutingError.NONE && routeResults != null && !routeResults.isEmpty()) {
                                // length in meters
                                int length = routeResults.get(0).getRoute().getLength();
                                float len = length / 1000F;
                                distanceTxt.setText(len + "Kms");
                                Log.e(TAG, "onCalculateRouteFinished() HELLO: " + len);
                                Maneuver maneuver = m_navigationManager.getNextManeuver();
                                if (maneuver != null) {
                                    if (maneuver.getAction() == Maneuver.Action.END) {
                                        // notify the user that the route is complete
                                    }
                                    Log.e(TAG, "onNewInstructionEvent: " + maneuver.getTurn());

//                turnIV.setImageResource(maneuver.getIcon().value());
                                    nxtDisTxt.setText(maneuver.getDistanceToNextManeuver() + "m");
                                    if (maneuver.getTurn().name().equals("QUITE_RIGHT")) {

                                        turnTxt.setText("Turn Right");
                                    }
                                    if (maneuver.getTurn().name().equals("QUITE_LEFT")) {

                                        turnTxt.setText("Turn Left");
                                    } else {
                                        turnTxt.setText(maneuver.getTurn().name());
                                    }
                                }
                            }
                            startNavigation();


                            // create a map marker to show current position
                            Image icon = new Image();
                            m_positionIndicatorFixed = new MapMarker();
                            try {
                                icon.setImageResource(R.drawable.pickup);
                                m_positionIndicatorFixed.setIcon(icon);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            m_positionIndicatorFixed.setVisible(true);
                            m_positionIndicatorFixed.setCoordinate(m_map.getCenter());
                            m_map.addMapObject(m_positionIndicatorFixed);

                            m_mapFragment.getPositionIndicator().setVisible(false);

                            navigationManager.setMap(m_map);

                            // listen to real position updates. This is used when RoadView is
                            // not active.
                            PositioningManager.getInstance().addListener(
                                    new WeakReference<>(mapPositionHandler));

                            // listen to updates from RoadView which tells you where the map
                            // center should be situated. This is used when RoadView is active.
                            navigationManager.getRoadView().addListener(new WeakReference<>(roadViewListener));

                            // listen to navigation manager events.
                            navigationManager.addNavigationManagerEventListener(
                                    new WeakReference<>(
                                            navigationManagerEventListener));

                            // start navigation simulation travelling at 13 meters per second
                            navigationManager.startNavigation(m_route);


//                            locationProvider = new LocationProvider(getMapFragment().getContext());
//                            locationProvider.start();

                        } else {
                            Toast.makeText(m_activity,
                                    "Error:route calculation returned error code: " + routingError,
                                    Toast.LENGTH_LONG).show();

                        }


                        /* *//* Calculation is done.Let's handle the result *//*
                        if (routingError == RoutingError.NONE) {
                            if (routeResults.get(0).getRoute() != null) {

                                m_route = routeResults.get(0).getRoute();
                                *//* Create a MapRoute so that it can be placed on the map *//*
                                MapRoute mapRoute = new MapRoute(routeResults.get(0).getRoute());
                                *//* Show the maneuver number on top of the route *//*
                                mapRoute.setManeuverNumberVisible(true);

                                *//* Add the MapRoute to the map *//*
                                m_map.addMapObject(mapRoute);

                                *//*
                         * We may also want to make sure the map view is orientated properly
                         * so the entire route can be easily seen.
                         *//*
                                m_geoBoundingBox = routeResults.get(0).getRoute().getBoundingBox();
                                m_map.zoomTo(m_geoBoundingBox, Map.Animation.NONE,
                                        Map.MOVE_PRESERVE_ORIENTATION);
                                if (routingError == RoutingError.NONE && routeResults != null && !routeResults.isEmpty()) {
                                    // length in meters
                                    int length = routeResults.get(0).getRoute().getLength();
                                    float len = length / 1000F;
                                    distanceTxt.setText(len + "Kms");
                                    Log.e(TAG, "onCalculateRouteFinished() HELLO: " + len);
                                    Maneuver maneuver = m_navigationManager.getNextManeuver();
                                    if (maneuver != null) {
                                        if (maneuver.getAction() == Maneuver.Action.END) {
                                            // notify the user that the route is complete
                                        }
                                        Log.e(TAG, "onNewInstructionEvent: " + maneuver.getTurn());

//                turnIV.setImageResource(maneuver.getIcon().value());
                                        nxtDisTxt.setText(maneuver.getDistanceToNextManeuver() + "m");
                                        if (maneuver.getTurn().name().equals("QUITE_RIGHT")) {

                                            turnTxt.setText("Turn Right");
                                        }
                                        if (maneuver.getTurn().name().equals("QUITE_LEFT")) {

                                            turnTxt.setText("Turn Left");
                                        } else {
                                            turnTxt.setText(maneuver.getTurn().name());
                                        }
                                    }
                                }
                                startNavigation();
                            } else {
                                Toast.makeText(m_activity,
                                        "Error:route results returned is not valid",
                                        Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(m_activity,
                                    "Error:route calculation returned error code: " + routingError,
                                    Toast.LENGTH_LONG).show();

                        }*/
                    }
                });
    }

    private void initNaviControlButton() {
        m_naviControlButton = (Button) m_activity.findViewById(R.id.naviCtrlButton);
        m_arrivedButton = (Button) m_activity.findViewById(R.id.arrivedButton);
        distanceTxt = m_activity.findViewById(R.id.distanceTxt);
        nxtDisTxt = m_activity.findViewById(R.id.nxtDisTxt);
        turnTxt = m_activity.findViewById(R.id.turnTxt);
        turnIV = m_activity.findViewById(R.id.turnIV);
        compass = m_activity.findViewById(R.id.compass);
        speedTxt = m_activity.findViewById(R.id.speedTxt);
        m_naviControlButton.setText(R.string.start_navi);

        // get the system sensors for compass
        mSensorManager = (SensorManager) getMapFragment().getContext().getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSensorManager.registerListener(sensorHandler, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(sensorHandler, mMagnetometer, SensorManager.SENSOR_DELAY_NORMAL);

        if (m_map != null && !PositioningManager.getInstance().isActive()) {
            PositioningManager.getInstance().start(PositioningManager.LocationMethod.GPS_NETWORK); // use gps plus cell and wifi
        }

        m_naviControlButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                /*
                 * To start a turn-by-turn navigation, a concrete route object is required.We use
                 * the same steps from Routing sample app to create a route from 4350 Still Creek Dr
                 * to Langley BC without going on HWY.
                 *
                 * The route calculation requires local map data.Unless there is pre-downloaded map
                 * data on device by utilizing MapLoader APIs,it's not recommended to trigger the
                 * route calculation immediately after the MapEngine is initialized.The
                 * INSUFFICIENT_MAP_DATA error code may be returned by CoreRouter in this case.
                 *
                 */
                if (m_route == null) {
                    createRoute();
                } else {
                    m_navigationManager.stop();
                    /*
                     * Restore the map orientation to show entire route on screen
                     */
                    m_map.zoomTo(m_geoBoundingBox, Map.Animation.BOW, 0f);
                    m_naviControlButton.setText(R.string.start_navi);
                    m_route = null;
                }
            }
        });

        if (m_route == null) {
            createRoute();
        } else {
            m_navigationManager.stop();
            /*
             * Restore the map orientation to show entire route on screen
             */
            m_map.zoomTo(m_geoBoundingBox, Map.Animation.BOW, 0f);
            m_naviControlButton.setText(R.string.start_navi);
            m_route = null;
        }
        compass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GeoPosition geoPosition = PositioningManager.getInstance().getLastKnownPosition();
                m_map.setCenter(geoPosition.getCoordinate(), Map.Animation.BOW);
                m_map.setZoomLevel(15);
                m_map.setTilt(0);
                m_map.setOrientation(0, Map.Animation.BOW);
            }
        });
        m_arrivedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isArrived) {
                    AlertDialog alert;
                    AlertDialog.Builder builder = new AlertDialog.Builder(m_activity);
                    builder.setMessage("You have reached your destination.")
                            .setCancelable(false)
                            .setTitle("Arrived!")
                            .setPositiveButton("Yes ! ",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            TaskInfoRepository mTaskInfoRepository = new TaskInfoRepository((Application) m_activity.getApplicationContext());
                                            String curDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                                            mTaskInfoRepository.updateLocation(m_taskInfoGroupByLocationKey.getLocationKey(), curDateTime);

                                            Intent intent = new Intent(m_activity, ShowListOfTaskGroupByLocationKeyActivity.class);
                                            m_activity.startActivity(intent);
                                            m_activity.finish();
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
                    Toast.makeText(m_activity, "You haven't reached yet!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    /*
     * Android 8.0 (API level 26) limits how frequently background apps can retrieve the user's
     * current location. Apps can receive location updates only a few times each hour.
     * See href="https://developer.android.com/about/versions/oreo/background-location-limits.html
     * In order to retrieve location updates more frequently start a foreground service.
     * See https://developer.android.com/guide/components/services.html#Foreground
     */

    private void startForegroundService() {
        if (!m_foregroundServiceStarted) {
            m_foregroundServiceStarted = true;
            Intent startIntent = new Intent(m_activity, HereTurnForegroundService.class);
            startIntent.setAction(HereTurnForegroundService.START_ACTION);
            m_activity.getApplicationContext().startService(startIntent);
        }
    }

    private void stopForegroundService() {
        if (m_foregroundServiceStarted) {
            m_foregroundServiceStarted = false;
            Intent stopIntent = new Intent(m_activity, HereTurnForegroundService.class);
            stopIntent.setAction(HereTurnForegroundService.STOP_ACTION);
            m_activity.getApplicationContext().startService(stopIntent);
        }
    }

    private void startNavigation() {
        m_naviControlButton.setText(R.string.stop_navi);
        /* Configure Navigation manager to launch navigation on current map */
        m_navigationManager.setMap(m_map);
        startForegroundService();
        /*
         * Start the turn-by-turn navigation.Please note if the transport mode of the passed-in
         * route is pedestrian, the NavigationManager automatically triggers the guidance which is
         * suitable for walking. Simulation and tracking modes can also be launched at this moment
         * by calling either simulate() or startTracking()
         */

        /* Choose navigation modes between real time navigation and simulation */
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(m_activity);
//        alertDialogBuilder.setTitle("Navigation");
//        alertDialogBuilder.setMessage("Choose Mode");
//        alertDialogBuilder.setNegativeButton("Navigation", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialoginterface, int i) {
//                m_navigationManager.startNavigation(m_route);
//                m_map.setTilt(60);
//                startForegroundService();
////                locationProvider = new LocationProvider(getMapFragment().getContext());
////                locationProvider.start();
//
//            }
//
//            ;
//        });
//        alertDialogBuilder.setPositiveButton("Simulation", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialoginterface, int i) {
//                m_navigationManager.simulate(m_route, 60);//Simualtion speed is set to 60 m/s
//                m_map.setTilt(60);
//                startForegroundService();
//            }
//
//            ;
//        });
//        AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialog.show();
        /*
         * Set the map update mode to ROADVIEW.This will enable the automatic map movement based on
         * the current location.If user gestures are expected during the navigation, it's
         * recommended to set the map update mode to NONE first. Other supported update mode can be
         * found in HERE Mobile SDK for Android (Premium) API doc
         */
        m_navigationManager.setMapUpdateMode(NavigationManager.MapUpdateMode.ROADVIEW);

        NavigationManager navigationManager = NavigationManager.getInstance();

//set the map where the navigation will be performed
        navigationManager.setMap(m_map);

// if user wants to start real navigation, submit calculated route
// for more information on calculating a route, see the "Directions" section
//        NavigationManager.Error error = navigationManager.simulate(m_route, 30);
//        Log.e(TAG, "startNavigation: ." + error.toString());
        /*
         * NavigationManager contains a number of listeners which we can use to monitor the
         * navigation status and getting relevant instructions.In this example, we will add 2
         * listeners for demo purpose,please refer to HERE Android SDK API documentation for details
         */
        addNavigationListeners();
    }

    private void resumeRoadView() {
        // move map back to it's current position.
        m_map.setCenter(PositioningManager.getInstance().getPosition().getCoordinate(), Map
                        .Animation.BOW, m_lastZoomLevelInRoadViewMode, Map.MOVE_PRESERVE_ORIENTATION,
                80);
        // do not start RoadView and its listener until the map movement is complete.
        m_returningToRoadViewMode = true;
    }

    private void addNavigationListeners() {

        /*
         * Register a NavigationManagerEventListener to monitor the status change on
         * NavigationManager
         */
        m_navigationManager.addNavigationManagerEventListener(
                new WeakReference<NavigationManager.NavigationManagerEventListener>(
                        m_navigationManagerEventListener));
        m_navigationManager.addRerouteListener(new WeakReference<NavigationManager.RerouteListener>(rerouteListener));

        /* Register a PositionListener to monitor the position updates */
        m_navigationManager.addPositionListener(
                new WeakReference<NavigationManager.PositionListener>(m_positionListener));
        /* Register a PositionListener to monitor the position updates */
        m_navigationManager.addNewInstructionEventListener(
                new WeakReference<NavigationManager.NewInstructionEventListener>(listener));
        startListeners();
    }

    private NavigationManager.RerouteListener rerouteListener = new NavigationManager.RerouteListener() {
        @Override
        public void onRerouteBegin() {
            super.onRerouteBegin();
//            Toast.makeText(m_activity, "Rerouted", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onRerouteEnd(@NonNull RouteResult routeResult, RoutingError routingError) {
            super.onRerouteEnd(routeResult, routingError);
//            Toast.makeText(m_activity, "Reroute End", Toast.LENGTH_SHORT).show();

        }
    };

    private NavigationManager.PositionListener m_positionListener = new NavigationManager.PositionListener() {
        @Override
        public void onPositionUpdated(GeoPosition geoPosition) {
            m_map.setCenter(geoPosition.getCoordinate(),
                    Map.Animation.BOW);
            /* Current position information can be retrieved in this callback */
        }
    };

    private NavigationManager.NewInstructionEventListener listener = new NavigationManager.NewInstructionEventListener() {
        @Override
        public void onNewInstructionEvent() {
            super.onNewInstructionEvent();
            Maneuver maneuver = m_navigationManager.getNextManeuver();
            if (maneuver != null) {
                if (maneuver.getAction() == Maneuver.Action.END) {
                    // notify the user that the route is complete
                }
                Log.e(TAG, "onNewInstructionEvent: " + maneuver.getTurn());

//                turnIV.setImageResource(maneuver.getIcon().value());
                nxtDisTxt.setText(maneuver.getDistanceToNextManeuver() + "m");
                if (maneuver.getTurn().name().equals("QUITE_RIGHT")) {

                    turnTxt.setText("Turn Right");
                }
                if (maneuver.getTurn().name().equals("QUITE_LEFT")) {

                    turnTxt.setText("Turn Left");
                } /*else {
                    turnTxt.setText(maneuver.getTurn().name());
                }*/
                /*if (maneuver.getTurn().equals("QUITE_RIGHT")) {

                    turnTxt.setText(maneuver.getTurn() + "");
                }
                if (maneuver.getTurn().equals("QUITE_RIGHT")) {

                    turnTxt.setText(maneuver.getTurn() + "");
                }*/
                // display current or next road information
                // display maneuver.getDistanceToNextManeuver()
            }
        }
    };
    private NavigationManager.NavigationManagerEventListener m_navigationManagerEventListener = new NavigationManager.NavigationManagerEventListener() {
        @Override
        public void onRunningStateChanged() {
//            Toast.makeText(m_activity, "Running state changed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNavigationModeChanged() {
//            Toast.makeText(m_activity, "Navigation mode changed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onEnded(NavigationManager.NavigationMode navigationMode) {
//            Toast.makeText(m_activity, navigationMode + " was ended", Toast.LENGTH_SHORT).show();
            m_arrivedButton.setEnabled(true);
            speedTxt.setText("-");
            distanceTxt.setText("-");
            nxtDisTxt.setText("-");
            turnTxt.setText("Reached");
            stopForegroundService();
        }

        @Override
        public void onMapUpdateModeChanged(NavigationManager.MapUpdateMode mapUpdateMode) {
            if (mapUpdateMode.equals(NavigationManager
                    .MapUpdateMode.ROADVIEW)) {
                m_map.setTilt(70);
            }

//            Toast.makeText(m_activity, "Map update mode is changed to " + mapUpdateMode,
//                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRouteUpdated(Route route) {

//            Toast.makeText(m_activity, "Route updated", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCountryInfo(String s, String s1) {
//            Toast.makeText(m_activity, "Country info updated from " + s + " to " + s1,
//                    Toast.LENGTH_SHORT).show();
        }
    };

    PositioningManager.OnPositionChangedListener positionLister = new PositioningManager.OnPositionChangedListener() {
        @Override
        public void onPositionUpdated(PositioningManager.LocationMethod locationMethod,
                                      GeoPosition geoPosition, boolean b) {

            if (PositioningManager.getInstance().getRoadElement() == null && !fetchingDataInProgress) {
                GeoBoundingBox areaAround = new GeoBoundingBox(geoPosition.getCoordinate(), 500, 500);
                MapDataPrefetcher.getInstance().fetchMapData(areaAround);
                fetchingDataInProgress = true;
            }

            if (geoPosition.isValid() && geoPosition instanceof MatchedGeoPosition) {

                MatchedGeoPosition mgp = (MatchedGeoPosition) geoPosition;

                int currentSpeedLimitTransformed = 0;
                int currentSpeed = meterPerSecToKmPerHour(mgp.getSpeed());
                if (currentSpeed < 300) {
                    speedTxt.setText(currentSpeed + "Km/H");

                } else {
                    speedTxt.setText(0 + "Km/H");

                }
                if (mgp.getRoadElement() != null) {
                    double currentSpeedLimit = mgp.getRoadElement().getSpeedLimit();
                    currentSpeedLimitTransformed = meterPerSecToKmPerHour(currentSpeedLimit);
                }
//                if()
                m_navigationManager.setMapUpdateMode(NavigationManager.MapUpdateMode.ROADVIEW);

                Log.e(TAG, "onPositionUpdated: " + currentSpeed);
//                Log.e(TAG, "onPositionUpdated: currentSpeedLimitTransformed " + currentSpeedLimitTransformed);
//                speedTxt.setText(currentSpeed + "Km/H");
//                updateCurrentSpeedView(currentSpeed, currentSpeedLimitTransformed);
//                updateCurrentSpeedLimitView(currentSpeedLimitTransformed);

            } else {
                //handle error
            }
        }

        @Override
        public void onPositionFixChanged(PositioningManager.LocationMethod locationMethod,
                                         PositioningManager.LocationStatus locationStatus) {

        }
    };

    private void pauseRoadView() {
        // pause RoadView so that map will stop moving, the map marker will use updates from
        // PositionManager callback to update its position.

        if (NavigationManager.getInstance().getMapUpdateMode().equals(NavigationManager.MapUpdateMode.ROADVIEW)) {
            NavigationManager.getInstance().setMapUpdateMode(NavigationManager.MapUpdateMode.NONE);
            NavigationManager.getInstance().getRoadView().removeListener(roadViewListener);
            m_lastZoomLevelInRoadViewMode = m_map.getZoomLevel();
        }
    }

    final private NavigationManager.RoadView.Listener roadViewListener = new NavigationManager.RoadView.Listener() {
        @Override
        public void onPositionChanged(GeoCoordinate geoCoordinate) {
            // an active RoadView provides coordinates that is the map transform center of it's
            // movements.
            m_mapTransformCenter = m_map.projectToPixel
                    (geoCoordinate).getResult();
        }
    };

    final private Map.OnTransformListener onTransformListener = new Map.OnTransformListener() {
        @Override
        public void onMapTransformStart() {
        }

        @Override
        public void onMapTransformEnd(MapState mapsState) {
            // do not start RoadView and its listener until moving map to current position has
            // completed
            if (m_returningToRoadViewMode) {
                NavigationManager.getInstance().setMapUpdateMode(NavigationManager.MapUpdateMode
                        .ROADVIEW);
                NavigationManager.getInstance().getRoadView()
                        .addListener(new WeakReference<>(roadViewListener));
                m_returningToRoadViewMode = false;
            }
        }
    };
//
//    private void createPositionMarkerMesh() {
//
//        FloatBuffer buff = FloatBuffer.allocate(9);
//
//        buff.put(0);
//        buff.put(0.5f);
//        buff.put(0);
//        buff.put(0.2f);
//        buff.put(-0.3f);
//        buff.put(0);
//        buff.put(-0.2f);
//        buff.put(-0.3f);
//        buff.put(0);
//
//        IntBuffer vertIndicieBuffer = IntBuffer.allocate(3);
//        vertIndicieBuffer.put(2);
//        vertIndicieBuffer.put(1);
//        vertIndicieBuffer.put(0);
//
//        LocalMesh myMesh = new LocalMesh();
//        myMesh.setVertices(buff);
//        myMesh.setVertexIndices(vertIndicieBuffer);
//
//        m_PositionMesh = new MapLocalModel();
//        m_PositionMesh.setMesh(myMesh);
//        m_PositionMesh.setScale(7.0f);
//        m_PositionMesh.setDynamicScalingEnabled(true); // keep size when zooming
//
//        m_map.addMapObject(m_PositionMesh); // add mesh to map. we set position later when we have the first reliable information
//    }

    final private NavigationManager.NavigationManagerEventListener navigationManagerEventListener =
            new NavigationManager.NavigationManagerEventListener() {
                @Override
                public void onRouteUpdated(Route route) {
                    m_map.removeMapObject(m_currentRoute);
                    m_currentRoute = new MapRoute(route);
                    m_map.addMapObject(m_currentRoute);
                }
            };

    public void onDestroy() {
        /* Stop the navigation when app is destroyed */
        if (MapEngine.isInitialized()) {
            PositioningManager.getInstance().removeListener(positionLister);
        }
        if (m_navigationManager != null) {
            stopForegroundService();
            m_navigationManager.stop();
        }
    }

    void onBackPressed() {
        if (NavigationManager.getInstance().getMapUpdateMode().equals(NavigationManager
                .MapUpdateMode.NONE)) {
            resumeRoadView();
        } else {
            m_activity.finish();
        }
    }
}
