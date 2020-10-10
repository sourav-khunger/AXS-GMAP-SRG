package com.doozycod.axs.Adapter;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.doozycod.axs.UpdateDebug.PhoneCallActivity;
import com.doozycod.axs.Database.Entities.StatusEntity;
import com.doozycod.axs.Database.Entities.TaskInfoEntity;
import com.doozycod.axs.Database.Repository.ShipmentStatusRepository;
import com.doozycod.axs.R;
import com.doozycod.axs.Utils.Constants;

import java.util.List;

public class TaskInfoGroupByListViewAdapter extends BaseAdapter {

    private Context context;
    private List<TaskInfoEntity> taskInfoEntityList;
    private TextView tvTaskAddressRow, tvTaskClientName, tvTaskQuantityRow, tvTaskBarcodeRow, tvStatus;
    private ImageView phoneCallIV;

    ShipmentStatusRepository shipmentStatusRepository;

    public TaskInfoGroupByListViewAdapter(Context context, List<TaskInfoEntity> taskInfoEntityList, Application application) {
        this.context = context;
        this.taskInfoEntityList = taskInfoEntityList;
        shipmentStatusRepository = new ShipmentStatusRepository(application);
    }

    @Override
    public int getCount() {
        return taskInfoEntityList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.to_do_task_list_row, parent, false);
        tvTaskAddressRow = convertView.findViewById(R.id.tvTaskAddressRow);
        tvTaskClientName = convertView.findViewById(R.id.tvTaskClientName);
        tvTaskQuantityRow = convertView.findViewById(R.id.tvTaskQuantityRow);
        tvTaskBarcodeRow = convertView.findViewById(R.id.package_text);
        tvStatus = convertView.findViewById(R.id.tvTaskBarcodeRow);
        phoneCallIV = convertView.findViewById(R.id.phoneCallIV);

        tvTaskAddressRow.setText(" " + taskInfoEntityList.get(position).getAddress());
        tvTaskClientName.setText(" " + taskInfoEntityList.get(position).getName());
        tvTaskQuantityRow.setText(" " + taskInfoEntityList.get(position).getQuantity());
        tvTaskBarcodeRow.setText(" " + taskInfoEntityList.get(position).getBarcode());

        phoneCallIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PhoneCallActivity.class);
                intent.putExtra("name", taskInfoEntityList.get(position).getName());
                intent.putExtra("number", taskInfoEntityList.get(position).getPhoneNo());
                context.startActivity(intent);
                Toast.makeText(context, taskInfoEntityList.get(position).getPhoneNo(), Toast.LENGTH_SHORT).show();
            }
        });

        tvStatus.setText("");
        if (taskInfoEntityList.get(position).getWorkStatus() != null
                && taskInfoEntityList.get(position).getWorkStatus()
                .equals(Constants.TASK_INFO_WORK_STATUS_COMPLETED) ||
                taskInfoEntityList.get(position).getWorkStatus() != null
                        && taskInfoEntityList.get(position).getWorkStatus()
                        .equals(Constants.TASK_INFO_WORK_STATUS_PROBLEM)) {

            StatusEntity status = shipmentStatusRepository.getStatus(taskInfoEntityList.get(position).getStatusId());
            tvStatus.setText(status.getStatusName());
        }
        return convertView;
    }
}
