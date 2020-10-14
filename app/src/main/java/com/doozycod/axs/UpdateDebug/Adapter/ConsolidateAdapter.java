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

public class ConsolidateAdapter extends RecyclerView.Adapter<ConsolidateAdapter.RecyclerHolder> {

    List<TaskInfoEntity> taskInfoEntities;
    Context context;
    OnClickListener onClickListener;
    List<String> selectedTasks = new ArrayList<>();
    private HashMap<TaskInfoEntity, Boolean> mChecked;

    public ConsolidateAdapter(List<TaskInfoEntity> taskInfoEntities, Context context, OnClickListener onClickListener) {
        this.taskInfoEntities = taskInfoEntities;
        this.context = context;
        this.onClickListener = onClickListener;
        this.mChecked = new HashMap<>();
    }

    public interface OnClickListener {
        void onCheckListener(List<String> taskBarcode);
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.consolidate_recycler_view, parent, false);
        RecyclerHolder holder = new RecyclerHolder(view);
        holder.setIsRecyclable(false);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
//        holder.setIsRecyclable(false);

        holder.barcodeTxt.setText(taskInfoEntities.get(position).getBarcode());
        holder.quantityTxt.setText("Q. " + taskInfoEntities.get(position).getQuantity());
        holder.nameTxt.setText(taskInfoEntities.get(position).getName());
        holder.addressTxt.setText(taskInfoEntities.get(position).getAddress());
        holder.checkBox.setTag(position);
        holder.checkBox.setOnCheckedChangeListener(null);
        if (mChecked.containsKey(taskInfoEntities.get(position))) {
            holder.checkBox.setChecked(mChecked.get(taskInfoEntities.get(position)));
        } else {
            holder.checkBox.setChecked(false);
        }

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mChecked.put(taskInfoEntities.get(holder.getAdapterPosition()), b);

                if (b) {
                    holder.checkBox.setChecked(true);
                    selectedTasks.add(taskInfoEntities.get(position).getBarcode());
                    Log.e("TAG", "onCheckedChanged: Added ");
                    onClickListener.onCheckListener(selectedTasks);
                } else {
                    holder.checkBox.setChecked(false);

                    Log.e("TAG", "onCheckedChanged: removed!");
                    selectedTasks.remove(taskInfoEntities.get(position).getBarcode());
                    onClickListener.onCheckListener(selectedTasks);
                }
            }
        });
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
        TextView barcodeTxt, quantityTxt, nameTxt, addressTxt;
        CheckBox checkBox;

        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            addressTxt = itemView.findViewById(R.id.addressTxt);
            nameTxt = itemView.findViewById(R.id.nameTxt);
            barcodeTxt = itemView.findViewById(R.id.barcodeTxt);
            quantityTxt = itemView.findViewById(R.id.quantityTxt);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}
