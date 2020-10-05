package com.doozycod.axs.ui.shipment;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doozycod.axs.R;
import com.doozycod.axs.ui.shipment.ViewModel.ShipmentCommentViewModel;

public class ShipmentCommentFragment extends Fragment {

    private ShipmentCommentViewModel mViewModel;

    public static ShipmentCommentFragment newInstance() {
        return new ShipmentCommentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.shipment_comment_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ShipmentCommentViewModel.class);
        // TODO: Use the ViewModel
    }

}