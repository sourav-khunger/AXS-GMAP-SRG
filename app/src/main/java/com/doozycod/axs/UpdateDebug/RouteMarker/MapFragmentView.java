/*
 * Copyright (c) 2011-2020 HERE Europe B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.doozycod.axs.UpdateDebug.RouteMarker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class encapsulates the properties and functionality of the Map view.
 */
public class MapFragmentView {
//    private static final String TAG = MapFragmentView.class.getSimpleName();
//
////    private AndroidXMapFragment m_mapFragment;
//    private AppCompatActivity m_activity;
////    private Map m_map;
//    private int mapMarkerCount = 0;
//
////    MapPolyline addMapPolyline;
////    private Route m_route;
////    MapRoute mapRoute;
////    private GeoCoordinate geoCoordinate;
//    List<TaskInfoEntity> taskInfoEntities = new ArrayList<>();
////    List<GeoCoordinate> geoCoordinates = new ArrayList<>();
//
//    Spinner routeSpinner;
//    ArrayList<String> routeSelectionList = new ArrayList<>();
//    private TaskInfoRepository mTaskInfoRepository;
//    private TextView markerTxt;
//    List<String> routeNameList = new ArrayList<>();
//    ImageView centerMap;
//    ArrayList<String> coordinatesList = new ArrayList();
////    private ArraySet<MapPolyline> m_polylines = new ArraySet<>();
//    String dcName = "";
//    double lat;
//    double lon;
////    private Scen mapScene;
//
//    /**
//     * Initial UI button on map fragment view. It includes several buttons to add/remove map objects
//     * such as MapPolygon, MapPolyline, MapCircle and MapMarker.
//     *
//     * @param activity
//     * @param lon
//     * @param lat
//     * @param dcName
//     */
//    public MapFragmentView(AppCompatActivity activity, ArrayList<String> coordinatesList,
//                           ArrayList<String> routeSelectionList, List<TaskInfoEntity> taskInfoEntities,
//                           double lon, double lat, String dcName) {
//        m_activity = activity;
//        this.coordinatesList = coordinatesList;
//        this.routeSelectionList = routeSelectionList;
//        this.taskInfoEntities = taskInfoEntities;
//        this.lon = lon;
//        this.lat = lat;
//        this.dcName = dcName;
//        initMapFragment();
//        initUI();
//    }
//
//    void initUI() {
////        m_naviControlButton = (Button) m_activity.findViewById(R.id.naviCtrlButton);
//        centerMap = m_activity.findViewById(R.id.centerMap);
//        routeSpinner = m_activity.findViewById(R.id.routeSpinner);
//        markerTxt = m_activity.findViewById(R.id.markerTxt);
//
//        mTaskInfoRepository = new TaskInfoRepository(m_activity.getApplication());
//        taskInfoEntities = mTaskInfoRepository.getTaskInfos1();
//
//  /*      routeSelectionList.add("    Show All   ");
//        routeSelectionList.add("    Show DC   ");
//        for (int i = 0; i < taskInfoEntities.size(); i++) {
//
//            routeSelectionList.add(" " + (i + 1) + ". " + taskInfoEntities.get(i).getName());
//        }*/
//
//        routeNameList.add("   Show All");
//        routeNameList.add("   Show DC");
//        for (int i = 0; i < routeSelectionList.size(); i++) {
//            routeNameList.add(" " + (i + 1) + ". " + routeSelectionList.get(i));
//        }
//
//        ArrayAdapter arrayAdapter = new ArrayAdapter(m_activity, android.R.layout.simple_spinner_dropdown_item, routeNameList);
//        routeSpinner.setAdapter(arrayAdapter);
//
//        routeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (m_mapFragment != null && m_map != null) {
//                    if (i == 0) {
//                        m_map.setZoomLevel(8.6, Map.Animation.BOW);
//                        m_map.setCenter(new GeoCoordinate(lat, lon), Map.Animation.BOW);
//                        markerTxt.setVisibility(View.GONE);
//                    }
//                    if (i == 1) {
//                        m_map.setZoomLevel(14.3, Map.Animation.BOW);
//                        m_map.setCenter(new GeoCoordinate(lat, lon), Map.Animation.BOW);
//                        markerTxt.setVisibility(View.VISIBLE);
//                        markerTxt.setText(dcName);
//                    }
//                    if (i > 1) {
//                        markerTxt.setVisibility(View.VISIBLE);
//                        i = i - 2;
//                        m_map.setZoomLevel(14.6, Map.Animation.BOW);
////                        double lat = Double.parseDouble(taskInfoEntities.get(i).getLatitude());
////                        double longi = Double.parseDouble(taskInfoEntities.get(i).getLongitude());
//                        m_map.setCenter(geoCoordinates.get(i)/*new GeoCoordinate(lat, longi)*/,
//                                Map.Animation.BOW);
//                        markerTxt.setText(routeSpinner.getSelectedItem().toString());
//                    }
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
////                m_map.setCenter(PositioningManager.getInstance().getLastKnownPosition().getCoordinate(),
////                        Map.Animation.BOW);
//            }
//        });
//        centerMap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                m_map.setZoomLevel(13.3, Map.Animation.BOW);
//                m_map.setCenter(new GeoCoordinate(lat, lon),
//                        Map.Animation.BOW);
//            }
//        });
//        if (m_map != null && !PositioningManager.getInstance().isActive()) {
//            PositioningManager.getInstance().start(PositioningManager.LocationMethod.GPS_NETWORK); // use gps plus cell and wifi
//        }
//    }
//
//    private AndroidXMapFragment getMapFragment() {
//        return (AndroidXMapFragment) m_activity.getSupportFragmentManager().findFragmentById(R.id.mapfragment1);
//    }
//
//    private void initMapFragment() {
//        /* Locate the mapFragment UI element */
//        m_mapFragment = getMapFragment();
//
//        // This will use external storage to save map cache data, it is also possible to set
//        // private app's path
//        String path = new File(m_activity.getExternalFilesDir(null), ".here-map-data")
//                .getAbsolutePath();
//        // This method will throw IllegalArgumentException if provided path is not writable
//        com.here.android.mpa.common.MapSettings.setDiskCacheRootPath(path);
//
//        if (m_mapFragment != null) {
//            /* Initialize the AndroidXMapFragment, results will be given via the called back. */
//            m_mapFragment.init(new OnEngineInitListener() {
//                @RequiresApi(api = Build.VERSION_CODES.O)
//                @Override
//                public void onEngineInitializationCompleted(Error error) {
//
//                    if (error == Error.NONE) {
//
//                        /*
//                         * If no error returned from map fragment initialization, the map will be
//                         * rendered on screen at this moment.Further actions on map can be provided
//                         * by calling Map APIs.
//                         */
//
//                        m_map = m_mapFragment.getMap();
//
//                        /*
//                         * Set the map center to the 4350 Still Creek Dr Burnaby BC (no animation).
//                         */
//
//                        geoCoordinate = new GeoCoordinate(LocationService.latitude, LocationService.longitude);
//                        geoCoordinate = PositioningManager.getInstance().getLastKnownPosition().getCoordinate();
//                        m_map.setCenter(new GeoCoordinate(lat, lon),
//                                Map.Animation.BOW);
//
//                        /* Set the zoom level to the average between min and max zoom level. */
//                        m_map.setZoomLevel(8.5);
//
//                        m_activity.supportInvalidateOptionsMenu();
//                        m_mapFragment.getMapGesture().addOnGestureListener(onGestureListener, 0, false);
////                        m_map =
////                        MapPolyline mapPolyline;
//                        addPolylineObject();
//                        createPolyline();
//
////                        m_map.addMapObject(mapPolyline);
//                        /*
//                         * Set up a handler for handling MapMarker drag events.
//                         */
//
//                        m_mapFragment.setMapMarkerDragListener(new OnDragListenerHandler());
//
//                    } else {
//                        Log.e(this.getClass().toString(), "onEngineInitializationCompleted: " +
//                                "ERROR=" + error.getDetails(), error.getThrowable());
//                        new AlertDialog.Builder(m_activity).setMessage(
//                                "Error : " + error.name() + "\n\n" + error.getDetails())
//                                .setTitle(R.string.engine_init_error)
//                                .setNegativeButton(android.R.string.cancel,
//                                        new DialogInterface.OnClickListener() {
//                                            @Override
//                                            public void onClick(
//                                                    DialogInterface dialog,
//                                                    int which) {
//                                                m_activity.finish();
//                                            }
//                                        }).create().show();
//                    }
//                }
//            });
//        }
//    }
//
//
//    private MapGesture.OnGestureListener onGestureListener = new MapGesture.OnGestureListener() {
//        @Override
//        public void onPanStart() {
//
//        }
//
//        @Override
//        public void onPanEnd() {
//
//        }
//
//        @Override
//        public void onMultiFingerManipulationStart() {
//
//        }
//
//        @Override
//        public void onMultiFingerManipulationEnd() {
//
//        }
//
//        @Override
//        public boolean onMapObjectsSelected(@NonNull List<ViewObject> list) {
//            for (ViewObject viewObject : list) {
//                if (viewObject.getBaseType() == ViewObject.Type.USER_OBJECT) {
//                    MapObject mapObject = (MapObject) viewObject;
//
//                    if (mapObject.getType() == MapObject.Type.MARKER) {
//
//                        MapMarker window_marker = ((MapMarker) mapObject);
//                        m_map.setCenter(window_marker.getCoordinate(), Map.Animation.BOW);
////                        showDialog(window_marker.getTitle());
//                        Log.e("MARKER", "Title is................." + window_marker.getTitle());
//                        markerTxt.setText(window_marker.getTitle());
//                        return false;
//                    }
//                }
//            }
//            return false;
//        }
//
//        @Override
//        public boolean onTapEvent(@NonNull PointF pointF) {
//            return false;
//        }
//
//        @Override
//        public boolean onDoubleTapEvent(@NonNull PointF pointF) {
//            return false;
//        }
//
//        @Override
//        public void onPinchLocked() {
//
//        }
//
//        @Override
//        public boolean onPinchZoomEvent(float v, @NonNull PointF pointF) {
//            return false;
//        }
//
//        @Override
//        public void onRotateLocked() {
//        }
//
//        @Override
//        public boolean onRotateEvent(float v) {
//            return false;
//        }
//
//        @Override
//        public boolean onTiltEvent(float v) {
//            return false;
//        }
//
//        @Override
//        public boolean onLongPressEvent(@NonNull PointF pointF) {
//            return false;
//        }
//
//        @Override
//        public void onLongPressRelease() {
//
//        }
//
//        @Override
//        public boolean onTwoFingerTapEvent(@NonNull PointF pointF) {
//            return false;
//        }
//    };
//
//    /**
//     * create a MapCircle and add the MapCircle to active map view.
//     */
//    private void addPolylineObject() {
//        // add boundingBox's top left and bottom right vertices to list of GeoCoordinates
//        List<GeoCoordinate> coordinates = new ArrayList<GeoCoordinate>();
//        for (int i = 0; i < coordinatesList.size(); i++) {
//            String[] latLong = coordinatesList.get(i).split(",");
//            double lat = Double.parseDouble(latLong[0]);
//            double longi = Double.parseDouble(latLong[1]);
//            coordinates.add(new GeoCoordinate(lat, longi));
////            Log.e(TAG, "createPolyline: " + geoCoordinates.get(i));
//        }
//        // create GeoPolyline with list of GeoCoordinates
//        GeoPolyline geoPolyline = new GeoPolyline(coordinates);
//        MapPolyline polyline = new MapPolyline(geoPolyline);
//        polyline.setLineColor(Color.BLUE);
//        polyline.setLineWidth(12);
//
//        // add GeoPolyline to current active map
//        m_map.addMapObject(polyline);
//        m_polylines.add(polyline);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void createPolyline() {
//        ArrayList<GeoCoordinate> coordinates = new ArrayList<>();
//        for (int a = 0; a < routeNameList.size(); a++) {
//            for (int i = 0; i < taskInfoEntities.size(); i++) {
//                if (routeNameList.get(a).contains(taskInfoEntities.get(i).getName())) {
//
//                    double longi = Double.parseDouble(taskInfoEntities.get(i).getLongitude());
//                    double lat = Double.parseDouble(taskInfoEntities.get(i).getLatitude());
//                    coordinates.add(new GeoCoordinate(lat, longi));
////                    Log.e(TAG, "createPolyline: " + coordinates.get(i));
//
//                }
//            }
//        }
//        geoCoordinates.addAll(coordinates);
////        for (int i = 0; i < coordinatesList.size(); i++) {
////            String[] latLong = coordinatesList.get(i).split(",");
////            double lat = Double.parseDouble(latLong[0]);
////            double longi = Double.parseDouble(latLong[1]);
////            geoCoordinates.add(new GeoCoordinate(lat, longi));
////            Log.e(TAG, "createPolyline: " + geoCoordinates.get(i).toString());
////        }
//
////        coordinates.add(new GeoCoordinate(29.9753092, 77.571972));
////        coordinates.add(new GeoCoordinate(29.9685473, 77.5644495));
////        coordinates.add(new GeoCoordinate(29.9623858, 77.5483282));
////        coordinates.add(new GeoCoordinate(29.9701266, 77.5698228));
//        Image img = new Image();
//        try {
//            img.setImageResource(R.drawable.dc_pin);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
////        GeoPolyline geoPolyline;
//        MapMarker dcMarker = new MapMarker(new GeoCoordinate(lat, lon), img);
//        dcMarker.setAnchorPoint(new PointF(img.getWidth() / 2, img.getHeight()));
//        dcMarker.setCoordinate(new GeoCoordinate(lat, lon));
//        dcMarker.setTitle(dcName);
//        dcMarker.setVisible(true);
//        m_map.addMapObject(dcMarker);
//        /* Initialize a CoreRouter */
////        CoreRouter coreRouter = new CoreRouter();
////
////        /* Initialize a RoutePlan */
////        RoutePlan routePlan = new RoutePlan();
////        RouteOptions routeOptions = new RouteOptions();
////        /* Other transport modes are also available e.g Pedestrian */
////        routeOptions.setTransportMode(RouteOptions.TransportMode.CAR);
////        /* Disable highway in this route. */
////        routeOptions.setHighwaysAllowed(false);
////        /* Calculate the shortest route available. */
////        routeOptions.setRouteType(RouteOptions.Type.SHORTEST);
////        /* Calculate 1 route. */
////        routeOptions.setRouteCount(1);
////        /* Finally set the route option */
////        routePlan.setRouteOptions(routeOptions);
//        Image image = new Image();
//        try {
//            image.setImageResource(R.drawable.pin);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
////        List<RouteWaypoint> routeWaypoints = new ArrayList<>();
//        for (int i = 0; i < coordinates.size(); i++) {
//
////            RouteWaypoint destination = ;
////            routePlan.addWaypoint(new RouteWaypoint(coordinates.get(i)));
//            MapMarker defaultMarker = new MapMarker(coordinates.get(i), image);
//            defaultMarker.setAnchorPoint(new PointF(image.getWidth() / 2, image.getHeight()));
//            defaultMarker.setCoordinate(coordinates.get(i));
//            defaultMarker.setTitle(routeNameList.get(i));
//            defaultMarker.setVisible(true);
//            m_map.addMapObject(defaultMarker);
//
//        }
//
//        /* Add both waypoints to the route plan */
////        try {
////            geoPolyline = new GeoPolyline(coordinates);
////
////        } catch (ExceptionInInitializerError e) {
////            // Less than two vertices.
////            e.printStackTrace();
////            return null;
////        }
////
////        int widthInPixels = 20;
//////        Color lineColor = new Color((short) 0x00, (short) 0x90, (short) 0x8A, (short) 0xA0);
////        MapPolyline mapPolyline = new MapPolyline(geoPolyline);
////        mapPolyline.setLineColor(generateColor());
////        mapPolyline.setLineWidth(widthInPixels);
////        coreRouter.calculateRoute(coordinates, routeOptions, new Router.Listener<List<RouteResult>, RoutingError>() {
////            @Override
////            public void onProgress(int i) {
////
////            }
////
////            @Override
////            public void onCalculateRouteFinished(@Nullable List<RouteResult> routeResults, @NonNull RoutingError routingError) {
////                if (routingError == RoutingError.NONE) {
////                    m_route = routeResults.get(0).getRoute();
////                    mapRoute = new MapRoute(m_route);
////                    m_map.addMapObject(mapRoute);
////                }
////                Log.e(TAG, "onCalculateRouteFinished: " + routingError.name());
////            }
////        });
//    }
//
//
//    private class OnDragListenerHandler implements MapMarker.OnDragListener {
//        @Override
//        public void onMarkerDrag(MapMarker mapMarker) {
//
//            Log.e(TAG, "onMarkerDrag: " + mapMarker.getTitle() + " -> " + mapMarker
//                    .getCoordinate());
//        }
//
//        @Override
//        public void onMarkerDragEnd(MapMarker mapMarker) {
//            Log.e(TAG, "onMarkerDragEnd: " + mapMarker.getTitle() + " -> " + mapMarker
//                    .getCoordinate());
//        }
//
//        @Override
//        public void onMarkerDragStart(MapMarker mapMarker) {
//            Log.e(TAG, "onMarkerDragStart: " + mapMarker.getTitle() + " -> " + mapMarker
//                    .getCoordinate());
//        }
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    int generateColor() {
//        Random random = new Random();
//        return Color.argb(1.0f, random.nextFloat(), random.nextFloat(), random.nextFloat());
//    }
//
//    /**
//     * create a MapMarker and add the MapMarker to active map view.
//     */
//    void showDialog(String marker) {
//        Dialog dialog = new Dialog(m_activity, R.style.MyDialogTheme);
//        dialog.setContentView(R.layout.marker_dialog);
//        TextView markerTxt = dialog.findViewById(R.id.markerName);
//        markerTxt.setText(marker);
//        dialog.show();
//    }

}
