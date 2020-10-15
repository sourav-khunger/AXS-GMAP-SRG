package com.doozycod.axs.UpdateDebug;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.doozycod.axs.Activity.ConsolicateBillsDetailsActivity;
import com.doozycod.axs.Database.Entities.TaskInfoEntity;
import com.doozycod.axs.Database.ViewModel.TaskInfoViewModel;
import com.doozycod.axs.POJO.TaskInfoGroupByLocationKey;
import com.doozycod.axs.R;
import com.doozycod.axs.UpdateDebug.Adapter.ConsolidateAdapter;
import com.doozycod.axs.Utils.Constants;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CosolidateBillsActivity extends AppCompatActivity implements ConsolidateAdapter.OnClickListener {
    RecyclerView consolidateRecyclerView;
    Button confirmButton;
    List<TaskInfoEntity> taskInfoEntityList;
    TaskInfoGroupByLocationKey taskInfoGroupByLocationKey;
    private TaskInfoViewModel taskInfoViewModel;
    ConsolidateAdapter consolidateAdapter;
    public static TaskInfoEntity selectedTask;
    List<String> taskBarcodes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosolidate_bills);
        consolidateRecyclerView = findViewById(R.id.consolidateRecyclerView);
        confirmButton = findViewById(R.id.confirm_button);

        String locationKey = getIntent().getStringExtra("locationKey");
        String name = getIntent().getStringExtra("name");
        taskInfoViewModel = new ViewModelProvider(this).get(TaskInfoViewModel.class);
        taskInfoEntityList = new ArrayList<TaskInfoEntity>();
        Log.e(":ssg", "onCreate: " + locationKey + name);
        consolidateRecyclerView.setHasFixedSize(true);
        consolidateRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        consolidateRecyclerView.setItemAnimator(new DefaultItemAnimator());

        String taskInfoStr = PreferenceManager.getDefaultSharedPreferences(this).getString(Constants.SELECTED_TASK, "");
        Log.e("TAG", "onCreate: " + taskInfoStr);
        selectedTask = new Gson().fromJson(taskInfoStr, TaskInfoEntity.class);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String listOfBarcode = TextUtils.join(",", taskBarcodes);
                PreferenceManager.getDefaultSharedPreferences(CosolidateBillsActivity.this).edit()
                        .putString("ConsolidateBills", listOfBarcode).apply();
//                Intent intent = new Intent();
////                intent.putExtra("myHosts", (Serializable) taskBarcodes);
//                setResult(RESULT_OK, intent);
                finish();
            }
        });
        try {
            taskInfoViewModel.getTaskInfoByLocationKey(locationKey).observe(this, new Observer<List<TaskInfoEntity>>() {
                @Override
                public void onChanged(List<TaskInfoEntity> taskInfoEntities) {
                    taskInfoEntityList.clear();
//                    taskInfoEntityList.addAll(taskInfoEntities);
                    for (int i = 0; i < taskInfoEntities.size(); i++) {
                        if (taskInfoEntities.get(i).getName().equals(name) &&
                                (!taskInfoEntities.get(i).getWorkStatus().equals(Constants.TASK_INFO_WORK_STATUS_PROBLEM) &&
                                        !taskInfoEntities.get(i).getWorkStatus().equals(Constants.TASK_INFO_WORK_STATUS_COMPLETED))) {
                            if (!selectedTask.getBarcode().equals(taskInfoEntities.get(i).getBarcode())) {
                                taskInfoEntityList.add(taskInfoEntities.get(i));
                            }
                            Log.e("TAG", "onChanged: " + taskInfoEntities.get(i).getBarcode());
                        }
                    }
                    consolidateAdapter = new ConsolidateAdapter(taskInfoEntityList,
                            CosolidateBillsActivity.this, CosolidateBillsActivity.this);
                    consolidateAdapter.setHasStableIds(true);
                    consolidateRecyclerView.setAdapter(consolidateAdapter);
                    consolidateAdapter.setOnItemClickListener(new ConsolidateAdapter.OnItemLongClickListener() {
                        @Override
                        public void onItemClick(long taskId, String locationKey) {
                            Intent intent = new Intent(CosolidateBillsActivity.this, ConsolicateBillsDetailsActivity.class);
                            intent.putExtra("taskId", taskId);
                            intent.putExtra("locationKey", locationKey);
                            startActivity(intent);
                        }
                    });
//                    consolidateAdapter.notifyDataSetChanged();
//                    listViewAdapter.notifyDataSetChanged();
//                    Log.e(TAG, "onChanged: " + taskInfoEntityList.toString());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*  @Override
      protected void onActivityResult(int requestCode, int resultCode, Intent data) {
          super.onActivityResult(requestCode, resultCode, data);
          for (Fragment fragment : getSupportFragmentManager().getFragments()) {
              fragment.onActivityResult(requestCode, resultCode, data);
          }
      }*/
    @Override
    public void onCheckListener(List<String> taskBarcode) {
        taskBarcodes = taskBarcode;
        Log.e("TAG", "onCheckListener: " + taskBarcode);
    }
}