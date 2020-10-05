package com.doozycod.axs.UpdateDebug;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.doozycod.axs.Adapter.SelfDispatchListAdapter;
import com.doozycod.axs.ApiService.ApiService;
import com.doozycod.axs.ApiService.ApiUtils;
import com.doozycod.axs.POJO.LoginResponse;
import com.doozycod.axs.POJO.TaskInfo;
import com.doozycod.axs.POJO.TaskInfoResponse;
import com.doozycod.axs.R;
import com.doozycod.axs.Utils.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SelfdispatchFragment extends Fragment {

    ImageView scanButton;
    RecyclerView listScannedPackages;
    SelfDispatchListAdapter selfDispatchListAdapter;
    private List<TaskInfo> taskInfoEntityList;
    ApiService apiService;
    private String TAG = "SELFDISPATCH";


    public SelfdispatchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_selfdispatch, container, false);
        scanButton = view.findViewById(R.id.scanButton);
        listScannedPackages = view.findViewById(R.id.listScannedPackages);

        apiService = ApiUtils.getAPIService();
        listScannedPackages.setLayoutManager(new LinearLayoutManager(getContext()));
        listScannedPackages.setItemAnimator(new DefaultItemAnimator());
        listScannedPackages.setHasFixedSize(true);
        taskInfoEntityList = new ArrayList<TaskInfo>();
        String taskInfoGroupByLocation = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(Constants.SELECTED_LOCATION, "");

        pullData();
        return view;
    }

    private void pullData() {
        //mTaskInfoRepository.deleteAll();

        String jsonLoginResponse = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(Constants.PREF_KEY_LOGIN_RESPONSE, "");
        LoginResponse loginResponse = new Gson().fromJson(jsonLoginResponse, LoginResponse.class);
        String token = loginResponse.getToken();
        Date date = new Date();
        String curDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        String selectedDate = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(Constants.PREF_KEY_SELECTED_DATE, curDate);
        String compId = "" + loginResponse.getDriverInfo().getCompanyId();
        // compId = "34";

        apiService.getTaskList(compId, selectedDate, Constants.AUTHORIZATION_TOKEN + token).enqueue(new Callback<TaskInfoResponse>() {
            @Override
            public void onResponse(Call<TaskInfoResponse> call, Response<TaskInfoResponse> response) {
                Log.d(TAG, "onResponse: ");
                if (response.code() == 200) {
                    Log.e(TAG, "size: AccessLITE " + response.body().getListOfTaskInfo().size());
                    taskInfoEntityList = response.body().getListOfTaskInfo();
                    listScannedPackages.setAdapter(new SelfDispatchListAdapter(getContext(), taskInfoEntityList));

                } else {
                    Toast.makeText(getContext(), "error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TaskInfoResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}