package com.doozycod.axs.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doozycod.axs.POJO.RunInfo;
import com.doozycod.axs.R;

import java.util.List;

public class RunTaskAdapter extends RecyclerView.Adapter<RunTaskAdapter.RecyclerHolder> {
    List<RunInfo> runInfoEntities;
    Context context;
    OnItemClickListener onItemClickListener;
    int pos;

    public RunTaskAdapter(List<RunInfo> runInfoEntities, Context context, int pos) {
        this.runInfoEntities = runInfoEntities;
        this.context = context;
        this.pos = pos;
    }


    public interface OnItemClickListener {
        void onGetRunPosition(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void updatePosition(int pos) {
        this.pos = pos;
    }

    @NonNull
    @Override
    public RunTaskAdapter.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.run_list_view, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RunTaskAdapter.RecyclerHolder holder, int position) {
        holder.runTxt.setText("RUN #" + runInfoEntities.get(position).getRunNo());
        holder.batchIdTxt.setText("Batch Id - " + runInfoEntities.get(position).getBatchId());
        if (pos == position) {
            holder.onRunClick.setBackgroundColor(Color.parseColor("#BDE1E5"));

        } else {
            holder.onRunClick.setBackgroundColor(Color.parseColor("#ffffff"));

        }
        holder.onRunClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onGetRunPosition(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return runInfoEntities.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView runTxt, batchIdTxt;
        LinearLayout onRunClick;

        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            batchIdTxt = itemView.findViewById(R.id.batchId);
            runTxt = itemView.findViewById(R.id.runId);
            onRunClick = itemView.findViewById(R.id.getRunSelected);
        }
    }
}
