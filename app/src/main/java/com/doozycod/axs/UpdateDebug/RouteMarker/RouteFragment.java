package com.doozycod.axs.UpdateDebug.RouteMarker;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import com.doozycod.axs.ApiService.ApiService;
import com.doozycod.axs.ApiService.ApiUtils;
import com.doozycod.axs.Database.Entities.TaskInfoEntity;
import com.doozycod.axs.Database.Repository.TaskInfoRepository;
import com.doozycod.axs.POJO.BatchRoutePath;
import com.doozycod.axs.POJO.LoginResponse;
import com.doozycod.axs.POJO.RunInfo;

import com.doozycod.axs.POJO.TaskInfoResponse;
import com.doozycod.axs.R;
import com.doozycod.axs.Utils.Constants;
import com.doozycod.axs.Utils.CustomProgressBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class RouteFragment extends Fragment {

    Button showRoute, filterTaskList;
    Spinner routeSpinner;
    ArrayList<String> routeSelectionList = new ArrayList<>();
    ArrayList<String> stringArrayList = new ArrayList<>();
    private TaskInfoRepository mTaskInfoRepository;
    List<TaskInfoEntity> taskInfoEntities;
    List<RunInfo> runInfoList;
    ApiService apiService;
    ArrayList<String> routeList;
    double lat, lon;
    String DC = "";
    CustomProgressBar customProgressBar;

    public RouteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_route, container, false);
        initUI(view);

        mTaskInfoRepository = new TaskInfoRepository(getActivity().getApplication());
        taskInfoEntities = mTaskInfoRepository.getTaskInfos1();
        apiService = ApiUtils.getAPIService();
        customProgressBar = new CustomProgressBar(getActivity());

        String batchId = PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString(Constants.SELECTED_BATCH_ID, "");

        pullData();
//        batchRoutePathAPI(batchId);
////        stringArrayList.add("    Run No. 1  ");
//        for (int i = 0; i < taskInfoEntities.size(); i++) {
//
//            routeSelectionList.add(" " + (i + 1) + ". " + taskInfoEntities.get(i).getName());
//        }


        routeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e(TAG, "onItemSelected: " + adapterView.getItemAtPosition(i).toString());
//                routeSelectionList.clear();
                routeSelectionList = new ArrayList<>();
                for (int a = 0; a < taskInfoEntities.size(); a++) {
                    if (runInfoList.get(i).getRunNo() == taskInfoEntities.get(a).getRunNo()) {
                        routeSelectionList.add(taskInfoEntities.get(a).getName());
//                        Log.e(TAG, "onItemClick: " + routeSelectionList.get(a));
                    }
                }
                batchRoutePathAPI(runInfoList.get(i).getBatchId());

//                Log.e(TAG, "onItemSelected: " + routeSelectionList.size());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        showRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (routeSelectionList.size() > 0) {
                    if (routeList != null) {

                        Intent intent = new Intent(getContext(), MapRouteActivity.class);
                        intent.putExtra("list", routeSpinner.getSelectedItem().toString().replaceAll("Run #", ""));
                        intent.putStringArrayListExtra("list", routeSelectionList);
                        intent.putStringArrayListExtra("routeList", routeList);
                        intent.putExtra("dcName", DC);
                        intent.putExtra("dcLat", lat);
                        intent.putExtra("dcLon", lon);

//                        Log.e(TAG, "onClick: " + routeSpinner.getSelectedItem().toString().replaceAll("Run #", ""));

                        startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), "Routes not found!", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getContext(), "you have no deliveries", Toast.LENGTH_SHORT).show();
                }

            }
        });
        filterTaskList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        return view;
    }

    private void pullData() {
        //mTaskInfoRepository.deleteAll();

        String jsonLoginResponse = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(Constants.PREF_KEY_LOGIN_RESPONSE, "");
        LoginResponse loginResponse = new Gson().fromJson(jsonLoginResponse, LoginResponse.class);
        String token = loginResponse.getToken();
       /* Date date = new Date();
        String curDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        String selectedDate = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(Constants.PREF_KEY_SELECTED_DATE, curDate);
       */
        String compId = "" + loginResponse.getDriverInfo().getCompanyId();
        String date = "" + PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString(Constants.PREF_KEY_SELECTED_DATE, "");
        // compId = "34";
        if (date.equals("") || date == null) {
//            Date date = new Date();
            date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        }
        apiService.getTaskList(compId, date
                , Constants.AUTHORIZATION_TOKEN + token).enqueue(new Callback<TaskInfoResponse>() {
            @Override
            public void onResponse(Call<TaskInfoResponse> call, Response<TaskInfoResponse> response) {
                if (response.code() == 200) {
                    runInfoList = response.body().getListOfRunList();
                    for (int i = 0; i < runInfoList.size(); i++) {
                        stringArrayList.add("Run #" + runInfoList.get(i).getRunNo());
                    }

                    ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(),
                            android.R.layout.simple_spinner_dropdown_item, stringArrayList);


                    String run = PreferenceManager.getDefaultSharedPreferences(getActivity())
                            .getString(Constants.PREF_KEY_SELECTED_RUN, "");
                    routeSpinner.setAdapter(arrayAdapter);
                    if (run.equals("")) {

                    } else {
                        routeSpinner.setSelection(Integer.parseInt(run));
                    }

//                    getTasks();
                } else {
                    Toast.makeText(getContext(), "error " + response.body().getStatus(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TaskInfoResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void batchRoutePathAPI(String batchId) {
        customProgressBar.showProgress();
        String jsonLoginResponse = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(Constants.PREF_KEY_LOGIN_RESPONSE, "");
        LoginResponse loginResponse = new Gson().fromJson(jsonLoginResponse, LoginResponse.class);

        apiService.batchRoutePath(batchId, Constants.AUTHORIZATION_TOKEN + loginResponse.getToken()).enqueue(new Callback<BatchRoutePath>() {
            @Override
            public void onResponse(Call<BatchRoutePath> call, Response<BatchRoutePath> response) {
                customProgressBar.hideProgress();

                if (response.code() == 200) {
                    if (response.body().getRoute() != null) {
                        List<String> routes = Arrays.asList(response.body().getRoute().split("\\|"));
                        routeList = new ArrayList<>();
                        routeList.addAll(routes);
                        DC = response.body().getDcName();
                        lat = response.body().getDcLat();
                        lon = response.body().getDcLon();
                        Log.e(TAG, "onResponse: Size" + routes.size());
                    }
                }

            }

            @Override
            public void onFailure(Call<BatchRoutePath> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                customProgressBar.hideProgress();

            }
        });
    }

    private void initUI(View view) {
        routeSpinner = view.findViewById(R.id.routeSpinner);
        showRoute = view.findViewById(R.id.showRoute);
        filterTaskList = view.findViewById(R.id.filterTaskList);
    }
}