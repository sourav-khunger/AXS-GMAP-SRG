package com.doozycod.axs;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.doozycod.axs.Activity.ScreenSlidePagerActivity;
import com.doozycod.axs.ApiService.ApiService;
import com.doozycod.axs.ApiService.ApiUtils;
import com.doozycod.axs.BackgroudService.Workers.LocationService;
import com.doozycod.axs.BackgroudService.Workers.SyncRemoteServerService;
import com.doozycod.axs.BackgroudService.Workers.UploadImageService;
import com.doozycod.axs.CoverterPOJOToEntity.DriverInfoToDriverInfoEntity;
import com.doozycod.axs.CoverterPOJOToEntity.ReasonWSToReasonEntity;
import com.doozycod.axs.CoverterPOJOToEntity.StatusWSTOStatusEntity;
import com.doozycod.axs.Database.Entities.DriverInfoEntity;
import com.doozycod.axs.Database.ViewModel.DriverInfoViewModel;
import com.doozycod.axs.Database.ViewModel.ShipmentStatusViewModel;
import com.doozycod.axs.Database.ViewModel.TaskInfoViewModel;
import com.doozycod.axs.POJO.DriverInfo;
import com.doozycod.axs.POJO.LoginResponse;
import com.doozycod.axs.POJO.ReasonWS;
import com.doozycod.axs.POJO.StatusReasonResponse;
import com.doozycod.axs.POJO.StatusWS;
import com.doozycod.axs.Utils.Constants;
import com.doozycod.axs.Utils.ServiceChecker;
import com.doozycod.axs.ui.NavigationViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements LifecycleOwner {

    private String IMEI;
    private ApiService apiService;
    private DriverInfoViewModel driverInfoViewModel;
    private TaskInfoViewModel taskInfoViewModel;
    private ProgressBar prgressbar;
    private TextView imeidisp, versiondisp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiService = ApiUtils.getAPIService();
        taskInfoViewModel = new ViewModelProvider(this).get(TaskInfoViewModel.class);
        taskInfoViewModel.syncRemoteDBWithLocalDB();
        driverInfoViewModel = new ViewModelProvider(this).get(DriverInfoViewModel.class);

        prgressbar = findViewById(R.id.prgressbar);
        imeidisp = findViewById(R.id.imeidisp);
        versiondisp = findViewById(R.id.version_name);

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            versiondisp.setText("version " + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Log.d("imei", "onCreate: " + IMEI);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);

            imeidisp.setVisibility(View.VISIBLE);
            imeidisp.setText("Permissions required. \nPlease enable all required permissions");
            prgressbar.setVisibility(View.GONE);
        } else {
            PreferenceManager.getDefaultSharedPreferences(this).edit().remove(Constants.SELECTED_BATCH_ID).apply();
            PreferenceManager.getDefaultSharedPreferences(this).edit().remove(Constants.PREF_KEY_SELECTED_RUN).apply();
//            IMEI = "866946038948979";
            IMEI = ServiceChecker.getUniqueIMEIId(getApplicationContext());
            login();
        }
    }


    private void login() {
        Log.d("onTest", "login: ");
        apiService.getLoginResponse(IMEI).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.e("onTest", "onResponse: ");
                if (response.code() == 200) {
                    try {
                        Log.e("onTest", "onResponse: " + response.body());
                        LoginResponse loginResponse = (LoginResponse) response.body();
                        Log.e("onTest", "onResponse: " + response.body().getDriverInfo().getImei());

                        saveDriverInfoIntoDatabase(loginResponse);
                        String responseString = new Gson().toJson(loginResponse);
                        readDatabase();
                        PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit()
                                .putString(Constants.PREF_KEY_LOGIN_RESPONSE, responseString)
                                .apply();

                        /*String curDate= new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                        String imageRemotePath = "/scan/Drivers/" + loginResponse.getDriverInfo().getImei() + "/" + curDate + "/";

                        PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit()
                                .putString(Constants.PREF_KEY_IMG_REMOTE_DIR, imageRemotePath)
                                .apply(); */

                        NavigationViewModel navigationViewModel = new ViewModelProvider(MainActivity.this).get(NavigationViewModel.class);
                        navigationViewModel.setDriver(loginResponse.getDriverInfo());

//                        Toast.makeText(MainActivity.this, "" + response.body().getStatus(), Toast.LENGTH_LONG).show();


                        if (loginResponse.getDriverInfo().getOnDuty() == 1) {
                            ws_getShipmentStatues("" + loginResponse.getDriverInfo().getCompanyId(), loginResponse.getToken());
                        }

                        startService(new Intent(getApplicationContext(), SyncRemoteServerService.class));
                        startService(new Intent(getApplicationContext(), LocationService.class));
                        startService(new Intent(getApplicationContext(), UploadImageService.class));

                        startActivity(new Intent(MainActivity.this, ScreenSlidePagerActivity.class));
                        finish();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {

                    imeidisp.setVisibility(View.VISIBLE);
                    imeidisp.setText("Device has not been setup" + "\n\n" + IMEI);
                    prgressbar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("onTest", "onFailure: " + t.getMessage());
                Toast.makeText(MainActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ws_getShipmentStatues(String companyId, String token) {
        apiService.shipmentStatus(companyId, Constants.AUTHORIZATION_TOKEN + token).enqueue(new Callback<StatusReasonResponse>() {

            @Override
            public void onResponse(Call<StatusReasonResponse> call, Response<StatusReasonResponse> response) {
                if (response.code() == 200) {
                    try {
                        StatusReasonResponse statusReasonResponse = (StatusReasonResponse) response.body();

                        saveShipmentStatusListToLocalDB(statusReasonResponse);

                        //startActivity(new Intent(MainActivity.this, ShipmentActivity.class));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<StatusReasonResponse> call, Throwable t) {

                t.printStackTrace();
            }
        });
    }

    private void readDatabase() {
        // Log.d("onTest", "readDatabase: " + driverInfoViewModel.getDriverInfos().getValue().get(0).getListOfAllowedCompanyInfo());
    }

    private void saveDriverInfoIntoDatabase(LoginResponse loginResponse) {
        driverInfoViewModel.deleteAll();
        DriverInfo driverInfo = loginResponse.getDriverInfo();
        DriverInfoEntity driverInfoEntity = DriverInfoToDriverInfoEntity.convertDriverInfoToEntity(driverInfo);
        Log.d("driverInfo", "saveDriverInfoIntoDatabase: " + driverInfoEntity.getCompanyName());
        driverInfoViewModel.insertDriverInfoEntity(driverInfoEntity);
    }

    private void saveShipmentStatusListToLocalDB(StatusReasonResponse statusReasonResponse) {
        ShipmentStatusViewModel shipmentStatusViewModel = new ViewModelProvider(this).get(ShipmentStatusViewModel.class);
        shipmentStatusViewModel.deleteAll();

        for (StatusWS statusWS : statusReasonResponse.getStatusList()) {
            shipmentStatusViewModel.insertStatusEntity(StatusWSTOStatusEntity.convert(statusWS));
        }
        for (ReasonWS reasonWS : statusReasonResponse.getReasonList()) {
            shipmentStatusViewModel.insertReasonEntity(ReasonWSToReasonEntity.convert(reasonWS));
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    if (hasPermissions(this, PERMISSIONS)) {
                        IMEI = ServiceChecker.getUniqueIMEIId(getApplicationContext());
//                        IMEI = "866946038948979";
                        login();
                        Intent i = getBaseContext().getPackageManager().
                                getLaunchIntentForPackage(getBaseContext().getPackageName());
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied. ", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    // The request code used in ActivityCompat.requestPermissions()
    // and returned in the Activity's onRequestPermissionsResult()
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            android.Manifest.permission.RECEIVE_BOOT_COMPLETED,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.WAKE_LOCK,
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.VIBRATE,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.ACCESS_NETWORK_STATE,
            android.Manifest.permission.RECORD_AUDIO,
            // Manifest.permission.ACCESS_WIFI_STATE
    };

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}