package com.doozycod.axs.UpdateDebug.SlidingActivity.Adapter;

import android.app.Application;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doozycod.axs.Database.Entities.TaskInfoEntity;
import com.doozycod.axs.Database.Repository.TaskInfoRepository;
import com.doozycod.axs.POJO.TaskInfoGroupByLocationKey;
import com.doozycod.axs.R;
import com.doozycod.axs.Utils.Constants;

import java.util.List;

public class ToDoTaskListAdapter extends RecyclerView.Adapter<ToDoTaskListAdapter.ToDoTaskListViewHolder> {

    List<TaskInfoEntity> listsOfTaskInfoEntities;
    private List<TaskInfoGroupByLocationKey> listOfTaskInfoGroupByLocationKeys;
    private String TAG = "adapter";
    private OnItemLongClickListener listener;
    private TaskInfoRepository mTaskInfoRepository;

    public interface OnItemLongClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemLongClickListener listener) {
        this.listener = listener;
    }

    public ToDoTaskListAdapter(List<TaskInfoGroupByLocationKey> listOfTaskInfoGroupByLocationKeys,
                               Application application/*, List<TaskInfoEntity> listsOfTaskInfoEntities*/) {
        this.listOfTaskInfoGroupByLocationKeys = listOfTaskInfoGroupByLocationKeys;
//        this.listsOfTaskInfoEntities = listsOfTaskInfoEntities;
        mTaskInfoRepository = new TaskInfoRepository(application);
    }

    @NonNull
    @Override
    public ToDoTaskListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_task_item2_new, parent, false);
        ToDoTaskListViewHolder toDoTaskListViewHolder = new ToDoTaskListViewHolder(view);
        return toDoTaskListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoTaskListViewHolder holder, int position) {
        String address = listOfTaskInfoGroupByLocationKeys.get(position).getAddress();

        final String postalCode = listOfTaskInfoGroupByLocationKeys.get(position).getCity() + ", " + listOfTaskInfoGroupByLocationKeys.get(position).getPostalCode();
        int counts = listOfTaskInfoGroupByLocationKeys.get(position).getGroupCount();

       /* if (listOfTaskInfoGroupByLocationKeys.get(position).getArrivalTime() == null ||
                listOfTaskInfoGroupByLocationKeys.get(position).getArrivalTime().equals("")) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {*/
        List<TaskInfoEntity> completedTasks = mTaskInfoRepository.getTaskInfoCompleted(listOfTaskInfoGroupByLocationKeys.get(position).getLocationKey(), Constants.TASK_INFO_WORK_STATUS_COMPLETED);
        List<TaskInfoEntity> pendingTasks = mTaskInfoRepository.getTaskInfoCompleted(listOfTaskInfoGroupByLocationKeys.get(position).getLocationKey(), Constants.TASK_INFO_WORK_STATUS_PROBLEM);


//        Log.e(TAG, (position + 1) + "  onBindViewHolder: " + listOfTaskInfoGroupByLocationKeys.get(position).getWorkStatus() + "  "
//                + listOfTaskInfoGroupByLocationKeys.get(position).getRecordStatus() + " "
//                + listOfTaskInfoGroupByLocationKeys.get(position).getGroupCount());

        if (listOfTaskInfoGroupByLocationKeys.get(position).getRecordStatus() == 1
                && listOfTaskInfoGroupByLocationKeys.get(position).getWorkStatus()
                .equals(Constants.TASK_INFO_WORK_STATUS_COMPLETED)
                || listOfTaskInfoGroupByLocationKeys.get(position).getRecordStatus() == 1
                && listOfTaskInfoGroupByLocationKeys.get(position).getWorkStatus()
                .equals(Constants.TASK_INFO_WORK_STATUS_PROBLEM)) {
            holder.imageView2.setImageResource(R.drawable.ic_delivered_map);
            holder.itemView.setBackgroundColor(Color.parseColor("#BDE1E5"));
        }
        if (listOfTaskInfoGroupByLocationKeys.get(position).getRecordStatus() == 0
                && listOfTaskInfoGroupByLocationKeys.get(position).getWorkStatus()
                .equals(Constants.TASK_INFO_WORK_STATUS_COMPLETED)
                || listOfTaskInfoGroupByLocationKeys.get(position).getRecordStatus() == 0
                && listOfTaskInfoGroupByLocationKeys.get(position).getWorkStatus()
                .equals(Constants.TASK_INFO_WORK_STATUS_PROBLEM)
        ) {
            holder.imageView2.setImageResource(R.drawable.ic_delivered_map);

            holder.itemView.setBackgroundColor(Color.parseColor("#BDE1E5"));
        }
        if (listOfTaskInfoGroupByLocationKeys.get(position).getWorkStatus().equals("pending")
                && listOfTaskInfoGroupByLocationKeys.get(position).getRecordStatus() == 1) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.imageView2.setImageResource(R.drawable.location_next);
        }
        if (listOfTaskInfoGroupByLocationKeys.get(position).getWorkStatus().equals("pending")
                && listOfTaskInfoGroupByLocationKeys.get(position).getRecordStatus() == 0) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.imageView2.setImageResource(R.drawable.location_next);

        }
        if (listOfTaskInfoGroupByLocationKeys.get(position).getRecordStatus() == 2) {
            holder.itemView.setBackgroundColor(Color.parseColor("#ADADAD"));
            holder.imageView2.setImageResource(R.drawable.location_partial);

        }
//        }

        holder.tvTaskAddress.setText(address);
        holder.tvPostalCode.setText(postalCode);
        /*if (listsOfTaskInfoEntities.size()>0) {
            holder.tvUserName.setText(listsOfTaskInfoEntities.get(position).getName());
            holder.tvBarcode.setText("Barcode No. " + listsOfTaskInfoEntities.get(position).getBarcode());
        }*/


//        List<TaskInfoEntity> completedTasks = mTaskInfoRepository.getTaskInfoByLocationKey(listOfTaskInfoGroupByLocationKeys.get(position).getLocationKey());
        int completedCounts = completedTasks.size() + pendingTasks.size();
        int i = position + 1;
        holder.position.setText("" + i);
        holder.tvQuantity1.setText("Quantity - " + counts);
        holder.tvQuantity.setText(completedCounts + " / " + counts + " Shipments ");
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return listOfTaskInfoGroupByLocationKeys.size();
    }

    public class ToDoTaskListViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        // each data item is just a string in this case
        public TextView tvTaskAddress, tvUserName, tvPostalCode, tvQuantity, tvQuantity1, position, tvBarcode;
        ImageView imageView2;
        View view;

        public ToDoTaskListViewHolder(View view) {
            super(view);
            this.view = view;
            this.imageView2 = view.findViewById(R.id.imageView2);
            this.tvUserName = (TextView) view.findViewById(R.id.tvUserName);
            this.tvTaskAddress = (TextView) view.findViewById(R.id.tvTaskAddress);
            this.tvPostalCode = (TextView) view.findViewById(R.id.tvPostalCode);
            this.tvQuantity = (TextView) view.findViewById(R.id.tvQuantity);
            this.tvQuantity1 = (TextView) view.findViewById(R.id.tvQuantity1);
            this.position = (TextView) view.findViewById(R.id.position);
            this.tvBarcode = (TextView) view.findViewById(R.id.tvBarcode);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(itemView, position);
                    }
                    return true;
                }
            });
        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }
}
