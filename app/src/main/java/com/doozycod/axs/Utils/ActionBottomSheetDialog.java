package com.doozycod.axs.Utils;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.doozycod.axs.Adapter.RunTaskAdapter;
import com.doozycod.axs.Database.Entities.RunInfoEntity;
import com.doozycod.axs.R;

import java.util.ArrayList;
import java.util.List;

public class ActionBottomSheetDialog extends BottomSheetDialogFragment {
    public static final String TAG = "ActionBottomDialog";
    private ItemClickListener mListener;
    ActionBottomSheetDialog actionBottomSheetDialog;

    public static ActionBottomSheetDialog newInstance() {
        return new ActionBottomSheetDialog();
    }

    RecyclerView recyclerView;
    RunTaskAdapter runTaskAdapter;
    List<RunInfoEntity> runInfoEntities = new ArrayList<>();
    //    ItemClickListener itemClickListener;
//    ToDoTaskListFragment toDoTaskListFragment;

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface ItemClickListener {
        void onItemClick(String item, int pos);
    }

    public void getList(List<RunInfoEntity> runInfoEntities) {
        this.runInfoEntities = runInfoEntities;

    }

    public void setOnClickListner(ItemClickListener itemClickListener) {
        mListener = itemClickListener;
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        try {
            mListener = (ItemClickListener) context;
        } catch (ClassCastException e) {
            throw new RuntimeException(context.toString()
                    + " must implement ItemClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_view, container, false);
//        actionBottomSheetDialog = ActionBottomSheetDialog.newInstance();
        recyclerView = view.findViewById(R.id.listViewBtmSheet);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setItemDe(new LinearLayoutManager(getContext()));
//        toDoTaskListFragment = new ToDoTaskListFragment();
//        runTaskAdapter.setOnItemClickListener(new RunTaskAdapter.OnItemClickListener() {
//            @Override
//            public void onGetRunPosition(int position) {
//                Log.e(TAG, "onGetRunPosition: "+position);
////                actionBottomSheetDialog.dismiss();
//                runTaskAdapter.updatePosition(position);
//                runTaskAdapter.notifyDataSetChanged();
////                toDoTaskListFragment.getPostionOfRun(position);
//                mListener.onItemClick("Selected Run #" + runInfoEntities.get(position).getRunNo(), position);
//            }
//        });
//        recyclerView.setAdapter(runTaskAdapter);
        return view;
    }
}
