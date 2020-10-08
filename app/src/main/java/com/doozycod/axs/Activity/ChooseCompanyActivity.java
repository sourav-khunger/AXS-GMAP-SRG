package com.doozycod.axs.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.doozycod.axs.MainActivity;
import com.google.gson.Gson;
import com.doozycod.axs.ApiService.ApiService;
import com.doozycod.axs.ApiService.ApiUtils;
import com.doozycod.axs.CoverterPOJOToEntity.DriverInfoToDriverInfoEntity;
import com.doozycod.axs.CoverterPOJOToEntity.ReasonWSToReasonEntity;
import com.doozycod.axs.CoverterPOJOToEntity.StatusWSTOStatusEntity;
import com.doozycod.axs.Database.Repository.DriverInfoRepository;
import com.doozycod.axs.Database.ViewModel.ShipmentStatusViewModel;
import com.doozycod.axs.POJO.AllowedCompanyInfo;
import com.doozycod.axs.POJO.DriverLogDutyResponse;
import com.doozycod.axs.POJO.LoginResponse;
import com.doozycod.axs.POJO.ReasonWS;
import com.doozycod.axs.POJO.StatusReasonResponse;
import com.doozycod.axs.POJO.StatusWS;
import com.doozycod.axs.R;
import com.doozycod.axs.Utils.Constants;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseCompanyActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = "ChooseCompanyActivity";
    LoginResponse loginResponse;
    private String token;
    private List<AllowedCompanyInfo> mListOfAllowedCompanyInfo;

    private int companyId;
    private int onDuty;
    private String companyName;
    private Button btnLogOn, btnLogOff;
    private Spinner spinCompanyChoose;
    private TextView tvLoggedOnCompany;
    private ApiService apiService;
    // Map<String,Integer> mMapCompanyNameId = new HashMap<String,Integer>();
    List<String> mListOfCompanyName = new ArrayList<String>();
    List<Integer> mListOfCompanyId = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_company);
        init();
        createMap();

        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mListOfCompanyName);
        mAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinCompanyChoose.setAdapter(mAdapter);

        if (onDuty == 0) {
            tvLoggedOnCompany.setText(Constants.LOGGED_OFF);
            btnLogOff.setVisibility(View.GONE);
            btnLogOn.setVisibility(View.VISIBLE);
        } else {

            btnLogOff.setVisibility(View.VISIBLE);
            btnLogOn.setVisibility(View.GONE);
            spinCompanyChoose.setSelection(mAdapter.getPosition(companyName));
            tvLoggedOnCompany.setText(Constants.LOGGED_ON + companyName);
        }

    }

    private void createMap() {
        for (int i = 0; i < mListOfAllowedCompanyInfo.size(); i++) {
            mListOfCompanyName.add(mListOfAllowedCompanyInfo.get(i).getCompanyName());
            mListOfCompanyId.add(mListOfAllowedCompanyInfo.get(i).getCompanyId());
        }
    }

    private void init() {
        String jsonLoginResponse = PreferenceManager.getDefaultSharedPreferences(this).getString(Constants.PREF_KEY_LOGIN_RESPONSE, "");
        loginResponse = new Gson().fromJson(jsonLoginResponse, LoginResponse.class);
        mListOfAllowedCompanyInfo = loginResponse.getDriverInfo().getListOfAllowedCompanyInfo();
        token = loginResponse.getToken();
        companyId = loginResponse.getDriverInfo().getCompanyId();
        onDuty = loginResponse.getDriverInfo().getOnDuty();
        companyName = loginResponse.getDriverInfo().getCompanyName();
        btnLogOn = findViewById(R.id.btnLogOn);
        btnLogOn.setOnClickListener(this);
        btnLogOff = findViewById(R.id.btnLogOff);
        btnLogOff.setOnClickListener(this);
        spinCompanyChoose = findViewById(R.id.spinCompanyChoose);
        spinCompanyChoose.setOnItemSelectedListener(this);
        tvLoggedOnCompany = findViewById(R.id.tvLoggedOnCompany);
        apiService = ApiUtils.getAPIService();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogOn:
                Log.d(TAG, "onClick: " + "btn is clicked");
                Log.d(TAG, "onClick: " + companyName + " token:  " + token);

                if (companyId == 0) return;
                apiService.driverLogDuty(Integer.toString(companyId), "ON", Constants.AUTHORIZATION_TOKEN + token).enqueue(new Callback<DriverLogDutyResponse>() {
                    @Override
                    public void onResponse(Call<DriverLogDutyResponse> call, Response<DriverLogDutyResponse> response) {
                        Log.d(TAG, "onResponse: " + response.code());
                        if (response.code() == 200) {
                            try {
                                DriverLogDutyResponse driverLogDutyResponse = response.body();
                                Log.d(TAG, "onResponse: " + response.body());
                                Log.d(TAG, "onResponse: " + new Gson().toJson(driverLogDutyResponse));
                                companyId = Integer.valueOf(driverLogDutyResponse.getCompanyId());
                                loginResponse.getDriverInfo().setCompanyId(companyId);
                                companyName = getCompanyName(companyId);
                                loginResponse.getDriverInfo().setCompanyName(companyName);
                                loginResponse.getDriverInfo().setOnDuty(1);
                                tvLoggedOnCompany.setText(Constants.LOGGED_ON + companyName);
                                String responseString = new Gson().toJson(loginResponse);
                                PreferenceManager.getDefaultSharedPreferences(ChooseCompanyActivity.this).edit()
                                        .putString(Constants.PREF_KEY_LOGIN_RESPONSE, responseString)
                                        .apply();
                                Log.d(TAG, "onResponse: " + responseString);

                                DriverInfoRepository driverInfoRepository = new DriverInfoRepository(ChooseCompanyActivity.this.getApplication());
                                driverInfoRepository.deleteAll();
                                driverInfoRepository.insert(DriverInfoToDriverInfoEntity.convertDriverInfoToEntity(loginResponse.getDriverInfo()));

                                ws_getShipmentStatues("" + companyId, loginResponse.getToken());

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<DriverLogDutyResponse> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
                break;
            case R.id.btnLogOff:
                apiService.driverLogDuty(Integer.toString(companyId), "OFF", Constants.AUTHORIZATION_TOKEN + token).enqueue(new Callback<DriverLogDutyResponse>() {
                    @Override
                    public void onResponse(Call<DriverLogDutyResponse> call, Response<DriverLogDutyResponse> response) {
                        if (response.code() == 200) {
                            try {
                                DriverLogDutyResponse driverLogDutyResponse = response.body();
                                companyId = Integer.valueOf(driverLogDutyResponse.getCompanyId());
                                loginResponse.getDriverInfo().setCompanyId(companyId);
                                companyName = getCompanyName(companyId);
                                loginResponse.getDriverInfo().setCompanyName(companyName);
                                loginResponse.getDriverInfo().setOnDuty(0);
                                String responseString = new Gson().toJson(loginResponse);
                                PreferenceManager.getDefaultSharedPreferences(ChooseCompanyActivity.this).edit()
                                        .putString(Constants.PREF_KEY_LOGIN_RESPONSE, responseString)
                                        .apply();
                                Log.e(TAG, "onResponse: " + responseString);
                                Toast.makeText(ChooseCompanyActivity.this, "You're off duty now!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ChooseCompanyActivity.this, ScreenSlidePagerActivity.class));
                                finishAffinity();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<DriverLogDutyResponse> call, Throwable t) {

                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
                break;
        }
    }

    private String getCompanyName(int companyId) {
        String companyName = "";
        for (int i = 0; i < mListOfAllowedCompanyInfo.size(); i++) {
            if (mListOfAllowedCompanyInfo.get(i).getCompanyId() == companyId) {
                companyName = mListOfAllowedCompanyInfo.get(i).getCompanyName();
            }
        }
        return companyName;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        companyName = mListOfCompanyName.get(position);
        companyId = mListOfCompanyId.get(position);
        Log.d(TAG, "onItemClick: " + companyName);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void ws_getShipmentStatues(String companyId, String token) {
        apiService.shipmentStatus(companyId, Constants.AUTHORIZATION_TOKEN + token).enqueue(new Callback<StatusReasonResponse>() {

            @Override
            public void onResponse(Call<StatusReasonResponse> call, Response<StatusReasonResponse> response) {
                if (response.code() == 200) {
                    try {
                        StatusReasonResponse statusReasonResponse = (StatusReasonResponse) response.body();

                        saveShipmentStatusListToLocalDB(statusReasonResponse);

                        startActivity(new Intent(ChooseCompanyActivity.this, ScreenSlidePagerActivity.class));

                        finishAffinity();

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
}