package com.doozycod.axs.Fragments;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Parcel;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.phappytech.library.CustomSeekBar;
import com.phappytech.library.ProgressSegment;
import com.doozycod.axs.Activity.ScreenSlidePagerActivity;
import com.doozycod.axs.ApiService.ApiService;
import com.doozycod.axs.ApiService.ApiUtils;
import com.doozycod.axs.Database.Entities.RunInfoEntity;
import com.doozycod.axs.Database.Entities.TaskInfoEntity;
import com.doozycod.axs.Database.Repository.TaskInfoRepository;
import com.doozycod.axs.Database.ViewModel.TaskInfoViewModel;
import com.doozycod.axs.R;
import com.doozycod.axs.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SummaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SummaryFragment extends Fragment {

    public static final String SUMMARY_FRAGMENT_TITLE = "Summary";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TaskInfoViewModel taskInfoViewModel;
    private TaskInfoRepository mTaskInfoRepository;
    List<TaskInfoEntity> listOfTaskInfo = new ArrayList<TaskInfoEntity>();
    CustomSeekBar customSeekBar;
    TextView successfulTV, textCountProblems, textCountToDo;
    int completed = 0, pending = 0, problem = 0;
    ScreenSlidePagerActivity activity;
    ApiService apiService;
    List<RunInfoEntity> runInfoEntities;

    public SummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.activity = (ScreenSlidePagerActivity) activity;

        } catch (ClassCastException e) {
            throw new RuntimeException(activity.toString()
                    + " must implement ItemClickListener");
        }
    }

    public static SummaryFragment newInstance(String param1, String param2) {
        SummaryFragment fragment = new SummaryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_summary, container, false);

        successfulTV = view.findViewById(R.id.textCount);
        textCountProblems = view.findViewById(R.id.textCountProblems);
        textCountToDo = view.findViewById(R.id.textCountToDo);
        customSeekBar = view.findViewById(R.id.customSeekBar);

        taskInfoViewModel = new ViewModelProvider(activity).get(TaskInfoViewModel.class);
        apiService = ApiUtils.getAPIService();

        getDataFromDB(view);

//        getSummaryData(0);

        return view;
    }

    void getDataFromDB(View view) {
        String batchId = PreferenceManager.getDefaultSharedPreferences(activity)
                .getString(Constants.SELECTED_BATCH_ID, "");
        taskInfoViewModel.getTaskInfoList().observe(activity, new Observer<List<TaskInfoEntity>>() {
            @Override
            public void onChanged(List<TaskInfoEntity> taskInfoEntities) {
                listOfTaskInfo.clear();
                listOfTaskInfo.addAll(taskInfoEntities);
//                Log.e("TAG", "onChanged: ");
                for (int i = 0; i < listOfTaskInfo.size(); i++) {
                    if (batchId.equals(listOfTaskInfo.get(i).getBatchId())) {
                        if (listOfTaskInfo.get(i).getWorkStatus().equals("completed")) {
                            completed = completed + 1;
                        }
                        if (listOfTaskInfo.get(i).getWorkStatus().equals("problem")) {
                            problem = problem + 1;
                        }
                        if (listOfTaskInfo.get(i).getWorkStatus().equals("pending")) {
                            pending = pending + 1;
                        }
                    }
                }

                successfulTV.setText("" + completed);
                textCountProblems.setText("" + problem);
                textCountToDo.setText("" + pending);

                getSummaryData(completed, problem, pending);


            }
        });
    }

    void getSummaryData(int index, int problem, int pending) {
        ArrayList<ProgressSegment> progressSegments = new ArrayList<>();
        int[] colors = {Color.GRAY, Color.RED, Color.WHITE};
        for (int i = 0; i < 3; i++) {
            ProgressSegment progressSegment = new ProgressSegment(Parcel.obtain());
            progressSegment.name = "progress" + i;
            if (i == 0) {
//            progressSegment.progress = (i + 1) * 10;
                if (index != 0) {
                    progressSegment.progress = (1 + index) * 10;
                }
            }
            if (i == 1) {
                if (problem != 0) {
                    progressSegment.progress = (1 + problem) * 10;
                }
//                progressSegment.progress = (i + 0) * 10;
            }
            if (i == 2) {
                if (pending != 0) {
                    progressSegment.progress = (1 + pending) * 10;
                }
            }

            progressSegment.color = colors[i];
            progressSegments.add(progressSegment);
        }
        customSeekBar.setProgressSegments(progressSegments);
    }

//    private void pullData() {
//        //mTaskInfoRepository.deleteAll();
//
//        String jsonLoginResponse = PreferenceManager.getDefaultSharedPreferences(activity).getString(Constants.PREF_KEY_LOGIN_RESPONSE, "");
//        LoginResponse loginResponse = new Gson().fromJson(jsonLoginResponse, LoginResponse.class);
//        String token = loginResponse.getToken();
//       /* Date date = new Date();
//        String curDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
//        String selectedDate = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(Constants.PREF_KEY_SELECTED_DATE, curDate);
//       */
//        String selectedDate = PreferenceManager.getDefaultSharedPreferences(activity).getString(Constants.PREF_KEY_SELECTED_DATE, "");
//
//        String compId = "" + loginResponse.getDriverInfo().getCompanyId();
//        // compId = "34";
//
//        apiService.getTaskList(compId, selectedDate, Constants.AUTHORIZATION_TOKEN + token).enqueue(new Callback<TaskInfoResponse>() {
//            @Override
//            public void onResponse(Call<TaskInfoResponse> call, Response<TaskInfoResponse> response) {
//
//                if (response.code() == 200) {
//                    taskInfoEntities = response.body().getListOfTaskInfo();
//                    runInfoList = response.body().getListOfRunList();
//
////                    getTasks();
//                } else {
//                    Toast.makeText(getContext(), "error", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TaskInfoResponse> call, Throwable t) {
//                Log.d(TAG, "onFailure: " + t.getMessage());
//                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

}