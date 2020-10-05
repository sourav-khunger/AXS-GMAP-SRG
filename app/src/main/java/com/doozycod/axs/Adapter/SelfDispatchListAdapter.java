package com.doozycod.axs.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doozycod.axs.Database.Repository.ShipmentStatusRepository;
import com.doozycod.axs.POJO.TaskInfo;
import com.doozycod.axs.R;

import java.util.List;

public class SelfDispatchListAdapter extends RecyclerView.Adapter<SelfDispatchListAdapter.RecyclerHolder> {

    private List<TaskInfo> taskInfoEntityList;
    private String TAG = "self dispatch adapter";
    private OnItemLongClickListener listener;
    ShipmentStatusRepository shipmentStatusRepository;

    Context context;

    public SelfDispatchListAdapter(Context context, List<TaskInfo> taskInfoEntityList) {
        this.context = context;
        this.taskInfoEntityList = taskInfoEntityList;
//        this.taskInfoEntityList = taskInfoEntityList;
//        shipmentStatusRepository = new ShipmentStatusRepository(application);
    }

    @NonNull
    @Override
    public SelfDispatchListAdapter.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scanned_package_list, parent, false);
        return new RecyclerHolder(view);
    }

    public interface OnItemLongClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemLongClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull SelfDispatchListAdapter.RecyclerHolder holder, int position) {
        holder.barcodeTxt.setText(taskInfoEntityList.get(position).getBarcode());
        holder.barcodeTxt.setText(taskInfoEntityList.get(position).getArrivalTime());
    }

    @Override
    public int getItemCount() {
        return taskInfoEntityList.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {

        TextView barcodeTxt, timeTxt;

        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            barcodeTxt = itemView.findViewById(R.id.barcodeTxt);
            timeTxt = itemView.findViewById(R.id.timeTxt);
        }
    }
}
