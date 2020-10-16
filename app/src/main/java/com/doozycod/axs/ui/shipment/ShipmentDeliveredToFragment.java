package com.doozycod.axs.ui.shipment;

import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.doozycod.axs.Activity.ArrivalTimeMapTypeActivity;
import com.doozycod.axs.Activity.ShipmentActivity;
import com.doozycod.axs.Database.Entities.ReasonEntity;
import com.doozycod.axs.Database.Entities.StatusEntity;
import com.doozycod.axs.Database.Entities.TaskInfoEntity;
import com.doozycod.axs.Database.Repository.ShipmentStatusRepository;
import com.doozycod.axs.Database.Repository.TaskInfoRepository;
import com.doozycod.axs.Database.ViewModel.TaskInfoViewModel;
import com.doozycod.axs.R;
import com.doozycod.axs.UpdateDebug.Adapter.ConsolidateAdapter;
import com.doozycod.axs.UpdateDebug.Adapter.ShipmentAdapter;
import com.doozycod.axs.UpdateDebug.CosolidateBillsActivity;
import com.doozycod.axs.Utils.Constants;
import com.doozycod.axs.ui.shipment.ViewModel.ShipmentDeliveredToViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShipmentDeliveredToFragment extends Fragment {

    private ShipmentDeliveredToViewModel mViewModel;
    private Button finishBtn;

    TextView deliverred_to_name_text, deliverred_to_address_text, ShipmentHist;
    public static TaskInfoEntity taskInfo;
    RecyclerView recyclerView;
    private TaskInfoViewModel taskInfoViewModel;
    List<TaskInfoEntity> taskInfoEntityList;
    ShipmentAdapter shipmentAdapter;
    String[] strings;
    String taskBarcode = "";

    public static ShipmentDeliveredToFragment newInstance() {
        return new ShipmentDeliveredToFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.shipment_delivered_to_fragment, container, false);

        finishBtn = root.findViewById(R.id.finish_btn);
        recyclerView = root.findViewById(R.id.shipment_recycler);
        deliverred_to_name_text = root.findViewById(R.id.deliverred_to_name_text);
        deliverred_to_address_text = root.findViewById(R.id.deliverred_to_address_text);
        ShipmentHist = root.findViewById(R.id.ShipmentHist);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        TaskInfoEntity thetask = ShipmentActivity.selectedTask;
        deliverred_to_name_text.setText(thetask.getName());
        deliverred_to_address_text.setText(thetask.getAddress());

        ShipmentStatusRepository shipmentStatusRepository = new ShipmentStatusRepository(getActivity().getApplication());

        StatusEntity status = shipmentStatusRepository.getStatus(ShipmentActivity.selectedTask.getStatusId());
        ReasonEntity reason = shipmentStatusRepository.getReason(ShipmentActivity.selectedTask.getReasonId());

        taskInfoViewModel = new ViewModelProvider(this).get(TaskInfoViewModel.class);
        taskInfoEntityList = new ArrayList<TaskInfoEntity>();

        String shAllInfo = "";
        if (status != null) shAllInfo += "\n" + status.getStatusName();
        if (reason != null) shAllInfo += "\n" + reason.getReasonName();

        String shipHistTask = shAllInfo /*+ "\nProbil # " + thetask.getBarcode()*/;
//        shipHistTask += "\nReff # " + thetask.getReffNo();
//        shipHistTask += "\nQty Entered : " + thetask.getQtyEntered();
//        shipHistTask += "\nComments \n " + thetask.getDriverComment();
        taskBarcode = PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString("ConsolidateBills", "");
        if (taskBarcode.equals("")) {
            shipHistTask = shAllInfo + "\nProbil # " + thetask.getBarcode();
            shipHistTask += "\nReff # " + thetask.getReffNo();
            shipHistTask += "\nQty Entered : " + thetask.getQtyEntered();
            shipHistTask += "\nComments \n " + thetask.getDriverComment();
        } else {
            strings = TextUtils.split(taskBarcode, ",");
        }
        ShipmentHist.setText(shipHistTask);

        try {
            taskInfoViewModel.getTaskInfoByLocationKey(ShipmentActivity.selectedTask.getLocationKey()).observe(getActivity(), new Observer<List<TaskInfoEntity>>() {
                @Override
                public void onChanged(List<TaskInfoEntity> taskInfoEntities) {
                    taskInfoEntityList.clear();
//                    taskInfoEntityList.addAll(taskInfoEntities);
//                    String status = PreferenceManager.getDefaultSharedPreferences(getActivity())
//                            .getString(Constants.DELIVERY_STATUS, "");
//                    if (status.equals("")) {
//                        ShipmentActivity.selectedTask.setWorkStatus("pending");
//                    } else {
//                        ShipmentActivity.selectedTask.setWorkStatus(status);
//                    }
                    taskInfoEntityList.add(ShipmentActivity.selectedTask);
                    if (!taskBarcode.equals("")) {
                        for (int i = 0; i < taskInfoEntities.size(); i++) {
                            if (taskInfoEntities.get(i).getName().equals(ShipmentActivity.selectedTask.getName())
                                    && (!taskInfoEntities.get(i).getWorkStatus().equals(Constants.TASK_INFO_WORK_STATUS_PROBLEM) &&
                                    !taskInfoEntities.get(i).getWorkStatus().equals(Constants.TASK_INFO_WORK_STATUS_COMPLETED))) {
                                for (int a = 0; a < strings.length; a++) {
                                    if (taskInfoEntities.get(i).getBarcode().equals(strings[a])) {
                                        taskInfoEntityList.add(taskInfoEntities.get(i));
                                        Log.e("TAG", "onChanged: " + strings[a]);
                                    }
                                }
                            }
                        }
                        shipmentAdapter = new ShipmentAdapter(taskInfoEntityList, getActivity());
                        recyclerView.setAdapter(shipmentAdapter);
                    }

//                    consolidateAdapter.notifyDataSetChanged();
//                    listViewAdapter.notifyDataSetChanged();
//                    Log.e(TAG, "onChanged: " + taskInfoEntityList.toString());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (taskBarcode.equals(""))
                    save();
                else {
                    for (int i = 0; i < taskInfoEntityList.size(); i++) {
                        saveConsolidatedBills(taskInfoEntityList.get(i));
                        if (i == (taskInfoEntityList.size() - 1)) {
                            Intent intent = new Intent(Constants.COMPLETED_CHECK_RECVER);
                            getActivity().sendBroadcast(intent);
                            getActivity().finish();
                        }
                    }

                }
            }
        });
    }

//    add consolidateBills

    private void saveConsolidatedBills(TaskInfoEntity taskInfoEntity) {
        Log.d("finish", ShipmentActivity.selectedTask.toString());

        TaskInfoRepository mTaskInfoRepository = new TaskInfoRepository((Application) getActivity().getApplicationContext());
        Date date = new Date();
        String curDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        String status = PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString(Constants.DELIVERY_STATUS, "");
        if (status.equals("")) {
            taskInfoEntity.setWorkStatus("pending");
        } else {
            taskInfoEntity.setWorkStatus(status);
        }

        /*curDateTime = PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString(Constants.PREF_DELIVERY_COMPLETEDTIME, "");*/

        taskInfoEntity.setAccessorial(ShipmentActivity.selectedTask.getAccessorial());
        taskInfoEntity.setAreaType(ShipmentActivity.selectedTask.getAreaType());
        taskInfoEntity.setCodCurrency(ShipmentActivity.selectedTask.getCodCurrency());
        taskInfoEntity.setCod(ShipmentActivity.selectedTask.getCod());
        taskInfoEntity.setStatusId(ShipmentActivity.selectedTask.getStatusId());
        taskInfoEntity.setReasonId(ShipmentActivity.selectedTask.getReasonId());
        taskInfoEntity.setImageTaken(ShipmentActivity.selectedTask.getImageTaken());
        taskInfoEntity.setImagePath(ShipmentActivity.selectedTask.getImagePath());
        taskInfoEntity.setArrivalTime(ShipmentActivity.selectedTask.getArrivalTime());
        taskInfoEntity.setSignature(ShipmentActivity.selectedTask.getSignature());
        taskInfoEntity.setSignatureTime(curDateTime);
        taskInfoEntity.setSignatureName(ShipmentActivity.selectedTask.getSignatureName());
        taskInfoEntity.setDriverComment(ShipmentActivity.selectedTask.getDriverComment());
        taskInfoEntity.setQtyEntered(ShipmentActivity.selectedTask.getQtyEntered());
//        taskInfoEntity.setCod(0);
        taskInfoEntity.setCompleteTime(curDateTime);
        taskInfoEntity.setRecordStatus(2);
        mTaskInfoRepository.update(taskInfoEntity);
//        Intent intent = new Intent(Constants.COMPLETED_CHECK_RECVER);
//        getActivity().sendBroadcast(intent);
//        getActivity().finish();
    }

    private void save() {
        Log.d("finish", ShipmentActivity.selectedTask.toString());

        TaskInfoRepository mTaskInfoRepository = new TaskInfoRepository((Application) getActivity().getApplicationContext());
        Date date = new Date();
        String curDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        String status = PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString(Constants.DELIVERY_STATUS, "");
        if (status.equals("")) {
            ShipmentActivity.selectedTask.setWorkStatus("pending");
        } else {
            ShipmentActivity.selectedTask.setWorkStatus(status);
        }
        /*curDateTime = PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString(Constants.PREF_DELIVERY_COMPLETEDTIME, "");*/
        ShipmentActivity.selectedTask.setCod(0);
        ShipmentActivity.selectedTask.setCompleteTime(curDateTime);
        ShipmentActivity.selectedTask.setRecordStatus(2);
        mTaskInfoRepository.update(ShipmentActivity.selectedTask);
        Intent intent = new Intent(Constants.COMPLETED_CHECK_RECVER);
        getActivity().sendBroadcast(intent);
        getActivity().finish();
    }
}