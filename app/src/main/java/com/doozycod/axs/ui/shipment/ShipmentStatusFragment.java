package com.doozycod.axs.ui.shipment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

import com.doozycod.axs.Activity.ShipmentActivity;
import com.doozycod.axs.Database.Entities.ReasonEntity;
import com.doozycod.axs.Database.Entities.StatusEntity;
import com.doozycod.axs.Database.ViewModel.ShipmentStatusViewModel;
import com.doozycod.axs.R;
import com.doozycod.axs.Utils.Constants;


import java.util.List;

public class ShipmentStatusFragment extends Fragment {

    private View root;
    private RadioGroup shipmentStatusRadioGroup;
    private ShipmentStatusViewModel viewModel;
    private ToggleButton statusToggleBtn;

//    private int isException = 0;

    public static ShipmentStatusFragment newInstance() {
        return new ShipmentStatusFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.shipment_status_fragment, container, false);
        setHasOptionsMenu(true);
        viewModel = new ViewModelProvider(this).get(ShipmentStatusViewModel.class);
        shipmentStatusRadioGroup = root.findViewById(R.id.shipment_status_radio_group);
        statusToggleBtn = root.findViewById(R.id.delivery_status_toggle_btn);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        statusToggleBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    PreferenceManager.getDefaultSharedPreferences(getActivity()).edit()
                            .putString(Constants.DELIVERY_STATUS, "completed")
                            .apply();
                    Log.e("TAG", "onCheckedChanged: Successful");
                    shipmentStatusRadioGroup.removeAllViews();
                    addShipmentStatus(view, 0);
                } else {
                    Log.e("TAG", "onCheckedChanged: Unsuccessful");
                    PreferenceManager.getDefaultSharedPreferences(getActivity()).edit()
                            .putString(Constants.DELIVERY_STATUS, "problem")
                            .apply();
                    shipmentStatusRadioGroup.removeAllViews();
                    addShipmentStatus(view, 1);
                }
            }
        });

        statusToggleBtn.setChecked(true);
    }

    private void addShipmentStatus(final View view, int isException) {

        this.viewModel.getStatusList(ShipmentActivity.selectedTask.getTaskType(), isException).observe(getActivity(), new Observer<List<StatusEntity>>() {
            @Override
            public void onChanged(List<StatusEntity> shipmentStatuses) {

                for (StatusEntity status : shipmentStatuses) {
                    RadioButton radio = new RadioButton(getActivity());
                    radio.setId(View.generateViewId());
                    radio.setText(status.getStatusName());
                    radio.setTag(status);

                    RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(getContext(), null);
                    params.width = RadioGroup.LayoutParams.MATCH_PARENT;
                    params.setMargins(16, 16, 16, 16);
                    radio.setLayoutParams(params);

                    radio.setPadding(16, 16, 16, 16);
                    radio.setTextColor(Color.parseColor("#000000"));

                    shipmentStatusRadioGroup.addView(radio);
                }
            }
        });

        shipmentStatusRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radio = radioGroup.findViewById(i);

                final StatusEntity shipmentStatus = (StatusEntity) radio.getTag();
                ShipmentActivity.selectedTask.setStatusId(shipmentStatus.getStatusId());

                viewModel.getReasonList(
                        shipmentStatus.getStatusId()
                ).observe(getActivity(), new Observer<List<ReasonEntity>>() {
                    @Override
                    public void onChanged(List<ReasonEntity> shipmentReasons) {
                        if (shipmentReasons.size() > 0) {
                            Navigation.findNavController(view).navigate(R.id.action_shipment_status_to_shipment_reason);
                        } else {
                            Navigation.findNavController(view).navigate(R.id.action_shipment_status_to_shipment_recipient);

//                            switch (shipmentStatus.getStatusRule()) {
//                                case "C":
//                                    Navigation.findNavController(view).navigate(R.id.action_shipment_status_to_shipment_comment);
//                                    break;
//                                case "P":
//                                    break;
//                                case "S":
//                                    Navigation.findNavController(view).navigate(R.id.action_shipment_status_to_shipment_signature);
//                                    break;
//                                default:
//                                    Navigation.findNavController(view).navigate(R.id.action_shipment_status_to_shipment_recipient);
//                                    break;
//                            }

                        }
                    }
                });

//                Navigation.findNavController(view).navigate(R.id.action_shipment_status_to_shipment_recipient);
            }
        });

    }

}