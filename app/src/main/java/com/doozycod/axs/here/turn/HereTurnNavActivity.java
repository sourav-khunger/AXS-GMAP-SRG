package com.doozycod.axs.here.turn;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.doozycod.axs.Database.Entities.TaskInfoEntity;
import com.doozycod.axs.POJO.TaskInfoGroupByLocationKey;
import com.doozycod.axs.R;
import com.doozycod.axs.Utils.Constants;

import android.os.PowerManager;

/**
 * Main activity which launches map view and handles Android run-time requesting permission.
 */

public class HereTurnNavActivity extends AppCompatActivity {

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
        }else{
            if (selectedTask.getQuantity() == 1) {
                dilverPackagesTV.setText("Deliver " + selectedTask.getQuantity() + " package");

            } else {
                dilverPackagesTV.setText("Deliver " + selectedTask.getQuantity() + " packages");
            }
        }

        if (hasPermissions(this, RUNTIME_PERMISSIONS)) {
            setupMapFragmentView();
        } else {
            ActivityCompat
                    .requestPermissions(this, RUNTIME_PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
        }
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
        m_mapFragmentView = new MapFragmentView(this, taskInfoGroupByLocationKey);
    }

    @Override
    public void onDestroy() {
        m_mapFragmentView.onDestroy();
        super.onDestroy();
        mWakeLock.release();
    }
}