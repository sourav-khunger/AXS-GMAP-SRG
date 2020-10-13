package com.doozycod.axs.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.doozycod.axs.Database.Entities.TaskInfoEntity;
import com.doozycod.axs.Database.Repository.TaskInfoRepository;
import com.doozycod.axs.Database.ViewModel.TaskInfoViewModel;
import com.doozycod.axs.POJO.TaskInfoGroupByLocationKey;
import com.doozycod.axs.Utils.Constants;
import com.google.gson.Gson;
import com.google.zxing.Result;
import com.doozycod.axs.R;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanPackageActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;

    private TaskInfoViewModel taskInfoViewModel;
    private List<TaskInfoEntity> taskInfoEntityList;
    TaskInfoRepository mTaskInfoRepository;
    TaskInfoGroupByLocationKey taskInfoGroupByLocationKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);

        taskInfoViewModel = new ViewModelProvider(this).get(TaskInfoViewModel.class);
        taskInfoEntityList = new ArrayList<TaskInfoEntity>();
        String taskInfoGroupByLocation = PreferenceManager.getDefaultSharedPreferences(this).getString(Constants.SELECTED_LOCATION, "");

        taskInfoGroupByLocationKey = new Gson().fromJson(taskInfoGroupByLocation, TaskInfoGroupByLocationKey.class);
        String locationKey = taskInfoGroupByLocationKey.getLocationKey();
        mTaskInfoRepository = new TaskInfoRepository((Application) getApplicationContext());

        try {
            taskInfoViewModel.getTaskInfoByLocationKey(locationKey).observe(this, new Observer<List<TaskInfoEntity>>() {
                @Override
                public void onChanged(List<TaskInfoEntity> taskInfoEntities) {
                    taskInfoEntityList.clear();
                    taskInfoEntityList.addAll(taskInfoEntities);
                    String batchId = taskInfoEntities.get(0).getBatchId();

                    Log.e("TAG", "onChanged: " + taskInfoEntities);
                    PreferenceManager.getDefaultSharedPreferences(ScanPackageActivity.this).edit()
                            .putString(Constants.SELECTED_BATCH_ID, batchId)
                            .apply();


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        String barcode = rawResult.getText().toString();

        checkDelivery(barcode);
        Intent returnIntent = new Intent();
//        returnIntent.putExtra("barcode", barcode);
//        setResult(Activity.RESULT_OK, returnIntent);
//        finish();
    }

    void checkDelivery(String barcode) {

//                String taskInfoEntityJsonString = new Gson().toJson(taskInfoEntity);

//                PreferenceManager.getDefaultSharedPreferences(ScanPackageActivity.this).edit()
//                        .putString(Constants.SELECTED_TASK, taskInfoEntityJsonString)
//                        .apply();
//                taskInfoEntity.setRecordStatus(Constants.PARTIAL_MODIFIED);
//                String curDate = getIntent().getStringExtra("curDate");
//                taskInfoEntity.setArrivalTime(curDate);
//                mTaskInfoRepository.update(taskInfoEntity);
//                Log.e("TAG", "onItemLongClick: Updated to 1");
//                Intent intent = new Intent(ShowListOfTaskGroupByLocationKeyActivity.this, ShipmentActivity.class);
//                startActivity(intent);

        for (int i = 0; i < taskInfoEntityList.size(); i++) {
            if (barcode.equals(taskInfoEntityList.get(i).getBarcode())) {
                if (taskInfoEntityList.get(i).getWorkStatus().equals("completed") ||
                        taskInfoEntityList.get(i).getWorkStatus().equals("problem")) {
                    showDialog();
                } else {
                    TaskInfoEntity taskInfoEntity = taskInfoEntityList.get(i);

                    String taskInfoEntityJsonString = new Gson().toJson(taskInfoEntityList.get(i));

                    PreferenceManager.getDefaultSharedPreferences(ScanPackageActivity.this).edit()
                            .putString(Constants.SELECTED_TASK, taskInfoEntityJsonString)
                            .apply();
                    taskInfoEntity.setRecordStatus(Constants.PARTIAL_MODIFIED);
                    String curDate = getIntent().getStringExtra("curDate");
                    taskInfoEntity.setArrivalTime(curDate);
                    mTaskInfoRepository.update(taskInfoEntity);
                    Intent intent = new Intent(ScanPackageActivity.this, ShipmentActivity.class);
                    startActivity(intent);
                    finish();

                }
                Log.e("TAG", "checkDelivery: Delivery Found");

                break;
            }
            if (i == (taskInfoEntityList.size() - 1))
                if (!barcode.equals(taskInfoEntityList.get(i).getBarcode())) {
                    Log.e("TAG", "checkDelivery: Delivery not Found");
                    finish();
                    Toast.makeText(this,
                            "Scanned package not found.\n\t\t\t\t\tPlease try again!",
                            Toast.LENGTH_LONG).show();
//                    mScannerView.startCamera();

                }
        }
//        mScannerView.startCamera();

    }

    void showDialog() {
        Dialog dialog = new Dialog(this, R.style.MyDialogTheme);
        dialog.setContentView(R.layout.parcel_error_dialog);
        Button closeBtn = dialog.findViewById(R.id.closeButton);
        dialog.setCancelable(false);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
    }


}