package com.doozycod.axs.BackgroudService.Workers;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.doozycod.axs.Activity.GPS_StatusActivity;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by zubaer on 4/22/18.
 */

public class LocationService extends Service {
    private static final int TWO_MINUTES = 1000 * 60 * 1;
    private LocationManager locationManager;
    private MyLocationListener listener;
    private Location previousBestLocation = null;

    public static boolean mNmeaSet = false;
    public static String checknmeaTrue, previuos_checknmeaTrue, id_imei;
    public static double latitude, longitude;

    @Override
    public void onCreate() {
        super.onCreate();

        id_imei = FetchIMEInumber();


        boolean flag = displayGpsStatus();
        if (flag) {

        } else {
            // GPS Status Dialog Box
            Intent dialogIntent = new Intent(getBaseContext(), GPS_StatusActivity.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplication().startActivity(dialogIntent);
        }

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onStart(Intent intent, int startId) {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        listener = new MyLocationListener();


        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location == null) {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 4000, 0, listener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 0, listener);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }


    /**
     * Checks whether two providers are the same
     */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }


    @Override
    public void onDestroy() {
        // handler.removeCallbacks(sendUpdatesToUI);
        super.onDestroy();
        Log.v("STOP_SERVICE", "DONE");
        if (locationManager != null)
            locationManager.removeUpdates(listener);
    }

    /*public static Thread performOnBackgroundThread(final Runnable runnable) {
        final Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } finally {
                }
            }
        };
        t.start();
        return t;
    }*/

    public class MyLocationListener implements LocationListener {

        public void onLocationChanged(final Location loc) {
            if (isBetterLocation(loc, previousBestLocation)) {

                LocationService.latitude = loc.getLatitude();
                LocationService.longitude = loc.getLongitude();

                LocationService.checknmeaTrue = getNMEAString(loc);
                LocationService.mNmeaSet = true;

                previousBestLocation = loc;
            }
        }

        public void onProviderDisabled(String provider) {
            Toast.makeText(getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT).show();
        }


        public void onProviderEnabled(String provider) {
            Toast.makeText(getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
        }


        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    }

    @SuppressLint("MissingPermission")
    public String FetchIMEInumber() {
        /*TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();*/

        String deviceId;

        if (android.os.Build.VERSION.SDK_INT >= 29) {
            deviceId = Settings.Secure.getString(
                    getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } else {
            final TelephonyManager mTelephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephony.getDeviceId() != null) {
                deviceId = mTelephony.getDeviceId();
            } else {
                deviceId = Settings.Secure.getString(
                        getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }
        }

        return deviceId;
    }

    private String getNMEAString(Location loc) {

        double latt = loc.getLatitude();
        double lonn = loc.getLongitude();

        String NS = "";
        if (latt > 0) NS = "N";
        else {
            NS = "S";
            latt = -1 * latt;
        }

        String EW = "";
        if (latt > 0) EW = "E";
        else {
            EW = "W";
            lonn = -1 * lonn;
        }

        float speed = (loc.getSpeed() * 3600) / 1000; // converting from m/s to km/h
        float ang = loc.getBearing();


        String ctimedate = convertTimeWithTimeZome(loc.getTime());
        return "*TS01," + id_imei + "," + ctimedate + ","
                + "GPS:" + "3;" + NS + latt + ";" + EW + lonn + ";" + speed + ";" + ang + ";1" + ",EVT:1#";
    }

    public String convertTime(long time) {
        Date date = new Date(time);
        Format format = new SimpleDateFormat("HHmmssddMMyy");
        return format.format(date);
    }

    public String convertTimeWithTimeZome(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT"));
        cal.setTimeInMillis(time);

        String hour = String.format("%02d", cal.get(Calendar.HOUR_OF_DAY));
        String min = String.format("%02d", cal.get(Calendar.MINUTE));
        String sec = String.format("%02d", cal.get(Calendar.SECOND));

        int y = cal.get(Calendar.YEAR) % 100;
        String year = String.format("%02d", y);

        int m = cal.get(Calendar.MONTH) + 1;
        String month = String.format("%02d", m);

        String day = String.format("%02d", cal.get(Calendar.DAY_OF_MONTH));

        return hour + min + sec + day + month + year;

    }

    /*----------Method to Check GPS is enable or disable ------------- */
    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = getBaseContext().getContentResolver();
        boolean gpsStatus = Settings.Secure.isLocationProviderEnabled(
                contentResolver, LocationManager.GPS_PROVIDER);
        if (gpsStatus) {
            return true;
        } else {
            return false;
        }
    }

}