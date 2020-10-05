package com.doozycod.axs.BackgroudService.Workers;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;


import com.doozycod.axs.R;


public class Foreground_GPS_Service extends Service {
    private static final String TAG = "GPS_StarterService";

    public static final String ACTION_START_FOREGROUND_SERVICE = "ACTION_START_FOREGROUND_SERVICE";

    public static final String ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE";

    public static final String ACTION_PAUSE = "ACTION_PAUSE";

    public static final String ACTION_PLAY = "ACTION_PLAY";

    Handler mHandler=new Handler();

    /**
     * starts the AlarmManager.
     */

    @Override
    public void onCreate() {
        super.onCreate();
        //TODO: Start ongoing notification here to make service foreground

        Log.d(TAG, "GPS_StarterService onStart");
    }


    @Override
    public void onDestroy() {

        try {
            wakeLock.release();
        } catch (Exception e) {
            // ignoring this exception, probably wakeLock was already released
        }

        // TODO Auto-generated method stub
        mHandler.removeCallbacks(taskUpdater);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    PowerManager powerManager;
    PowerManager.WakeLock wakeLock;


    @SuppressLint("InvalidWakeLockTag")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent != null)
        {
            powerManager = (PowerManager) getSystemService(POWER_SERVICE);
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    "MyWakelockTag");

            String action = intent.getAction();

            switch (action)
            {
                case ACTION_START_FOREGROUND_SERVICE:
                    startForegroundService();
                    //Toast.makeText(getApplicationContext(), "Foreground service is started.", Toast.LENGTH_LONG).show();

                    wakeLock.acquire();

                    try {
                        runa();
                    }
                    catch (Exception e) {}

                    break;
                case ACTION_STOP_FOREGROUND_SERVICE:
                    stopForegroundService();
                    //Toast.makeText(getApplicationContext(), "Foreground service is stopped.", Toast.LENGTH_LONG).show();
                    break;
                case ACTION_PLAY:
                    Toast.makeText(getApplicationContext(), "You click Play button.", Toast.LENGTH_LONG).show();
                    break;
                case ACTION_PAUSE:
                    Toast.makeText(getApplicationContext(), "You click Pause button.", Toast.LENGTH_LONG).show();
                    break;
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    public Runnable taskUpdater;
    public void runa() throws Exception{
        taskUpdater = new Runnable(){
            public void run(){

                Log.d("Alert:", "GPS 45 sec interval.");

                Intent intent = new Intent(Foreground_GPS_Service.this, LocationService.class);
                startService(intent);

                /*if(NetworkUtil.getConnectivityStatusString(getApplicationContext()).equalsIgnoreCase("connected")){

                    if(LocationService.mNmeaSet) {

                        if(previuos_checknmeaTrue!=null && previuos_checknmeaTrue.equals(LocationService.checknmeaTrue))
                        {
                            return;
                        }
                        else {

                            String url = "http://gps.fleetoptics.biz/portal/handle_gps_android/access_gps.php?";
                            String URL = params[0] + "gps=" + URLEncoder.encode(LocationService.checknmeaTrue, "UTF-8") + "&client=" + LocationService.id_imei;
                            connect(url, 1215);

                            previuos_checknmeaTrue = LocationService.checknmeaTrue;
                            //Toast.makeText(getApplicationContext(), "Sending gps ping.", Toast.LENGTH_SHORT).show();
                        }
                    }

                } */

                mHandler.postDelayed(this, 45000);

            }
        };

        mHandler.post(taskUpdater);
    }




    /* Used to build and start foreground service. */
    private void startForegroundService()
    {
        Log.d(TAG, "Start foreground service.");

        // Create notification default intent.
        Intent intent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Create notification builder.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        // Make notification show big text.
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle("GPS Tracking by Access.");
        // Set big text style.
        builder.setStyle(bigTextStyle);

        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.drawable.axs_logo);
        Bitmap largeIconBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.axs_logo);
        builder.setLargeIcon(largeIconBitmap);
        // Make the notification max priority.
        builder.setPriority(Notification.PRIORITY_MAX);
        // Make head-up notification.
        builder.setFullScreenIntent(pendingIntent, true);

        // Add Play button intent in notification.
        /*Intent playIntent = new Intent(this, Foreground_GPS_Service.class);
        playIntent.setAction(ACTION_PLAY);
        PendingIntent pendingPlayIntent = PendingIntent.getService(this, 0, playIntent, 0);
        NotificationCompat.Action playAction = new NotificationCompat.Action(android.R.drawable.ic_media_play, "Play", pendingPlayIntent);
        builder.addAction(playAction);
        // Add Pause button intent in notification.
        Intent pauseIntent = new Intent(this, Foreground_GPS_Service.class);
        pauseIntent.setAction(ACTION_PAUSE);
        PendingIntent pendingPrevIntent = PendingIntent.getService(this, 0, pauseIntent, 0);
        NotificationCompat.Action prevAction = new NotificationCompat.Action(android.R.drawable.ic_media_pause, "Pause", pendingPrevIntent);
        builder.addAction(prevAction); */

        // Build the notification.
        Notification notification = builder.build();

        // Start foreground service.
        startForeground(1, notification);
    }

    private void stopForegroundService()
    {
        Log.d(TAG, "Stop foreground service.");

        // Stop foreground service and remove the notification.
        stopForeground(true);

        // Stop the foreground service.
        stopSelf();
    }

    public static String previuos_checknmeaTrue;
}
