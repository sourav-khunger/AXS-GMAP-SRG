package com.doozycod.axs.UpdateDebug.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doozycod.axs.Database.Entities.TaskInfoEntity;
import com.doozycod.axs.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShipmentAdapter extends RecyclerView.Adapter<ShipmentAdapter.RecyclerHolder> {

    List<TaskInfoEntity> taskInfoEntities;
    Context context;
    OnClickListener onClickListener;
    List<String> selectedTasks = new ArrayList<>();

    public ShipmentAdapter(List<TaskInfoEntity> taskInfoEntities, Context context) {
        this.taskInfoEntities = taskInfoEntities;
        this.context = context;
    }

    public interface OnClickListener {
        void onCheckListener(List<String> taskBarcode);
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.summary_recycler_view, parent, false);
        RecyclerHolder holder = new RecyclerHolder(view);
        holder.setIsRecyclable(false);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
//        holder.setIsRecyclable(false);

        holder.barcodeTxt.setText(taskInfoEntities.get(position).getBarcode());
        holder.quantityTxt.setText("Q. " + taskInfoEntities.get(position).getQuantity());
        holder.codTxt.setText(taskInfoEntities.get(position).getName());


    }

    //    @Override
//    public void onViewRecycled(@NonNull RecyclerHolder holder) {
//        holder.checkBox.setOnCheckedChangeListener(null);
//        super.onViewRecycled(holder);
//    }
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return taskInfoEntities.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView barcodeTxt, quantityTxt, codTxt, deliveryStatus;
        CheckBox checkBox;

        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            deliveryStatus = itemView.findViewById(R.id.deliveryStatus);
            codTxt = itemView.findViewById(R.id.codTxt);
            barcodeTxt = itemView.findViewById(R.id.barcodeTxt);
            quantityTxt = itemView.findViewById(R.id.quantityTxt);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}
