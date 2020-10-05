package com.doozycod.axs.ui.shipment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.doozycod.axs.Activity.ShipmentActivity;
import com.doozycod.axs.Database.Entities.ReasonEntity;
import com.doozycod.axs.Database.ViewModel.ShipmentStatusViewModel;
import com.doozycod.axs.R;

import java.util.List;

public class ShipmentReasonFragment extends Fragment {

    private View root;
    private ShipmentStatusViewModel viewModel;
    private RadioGroup shipmentReasonRadioGroup;

    public static ShipmentReasonFragment newInstance() {
        return new ShipmentReasonFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.shipment_reason_fragment, container, false);
        viewModel = new ViewModelProvider(this).get(ShipmentStatusViewModel.class);

        addShipmentReasonRadios(root);

        Button continueBtn = root.findViewById(R.id.continue_btn);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToNext(view);
            }
        });

        return root;
    }

    private void addShipmentReasonRadios(final View view) {
        shipmentReasonRadioGroup = view.findViewById(R.id.shipment_reason_radio_group);


        this.viewModel.getReasonList(ShipmentActivity.selectedTask.getStatusId()).observe(getActivity(), new Observer<List<ReasonEntity>>() {
            @Override
            public void onChanged(List<ReasonEntity> shipmentReasons) {
                for(ReasonEntity reason: shipmentReasons){
                    RadioButton radio = new RadioButton(getActivity());
                    radio.setId(View.generateViewId());
                    radio.setText(reason.getReasonName());
                    radio.setTag(reason);

                    RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(getContext(), null);
                    params.width = RadioGroup.LayoutParams.MATCH_PARENT;
                    params.setMargins(16,16,16,16);
                    radio.setLayoutParams(params);

                    radio.setPadding(16,16,16,16);
                    radio.setTextColor(Color.parseColor("#000000"));

                    shipmentReasonRadioGroup.addView(radio);

                }
            }
        });

        this.shipmentReasonRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                final RadioButton radio = shipmentReasonRadioGroup.findViewById(shipmentReasonRadioGroup.getCheckedRadioButtonId());
                ReasonEntity reason = (ReasonEntity) radio.getTag();

                ShipmentActivity.selectedTask.setReasonId(reason.getReasonId());
                Navigation.findNavController(view).navigate(R.id.action_shipment_reason_to_shipment_recipient);
            }
        });

    }

    private void navigateToNext(View view) {
        Navigation.findNavController(view).navigate(R.id.action_shipment_reason_to_shipment_recipient);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ShipmentStatusViewModel.class);
        // TODO: Use the ViewModel
    }

//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_help, menu);
//    }
}