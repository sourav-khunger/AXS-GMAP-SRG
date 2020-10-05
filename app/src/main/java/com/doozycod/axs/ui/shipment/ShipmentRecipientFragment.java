package com.doozycod.axs.ui.shipment;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.doozycod.axs.Activity.ShipmentActivity;
import com.doozycod.axs.Database.Entities.ReasonEntity;
import com.doozycod.axs.Database.Entities.StatusEntity;
import com.doozycod.axs.Database.Entities.TaskInfoEntity;
import com.doozycod.axs.Database.Repository.ShipmentStatusRepository;
import com.doozycod.axs.R;
import com.doozycod.axs.ui.shipment.ViewModel.ShipmentRecipientViewModel;

public class ShipmentRecipientFragment extends Fragment {

    private ShipmentRecipientViewModel mViewModel;

    private TextView signatureName, quantity, reff, comment;
    private TextView shipInfo;

    private ReasonEntity reason;
    private StatusEntity status;

    private Button sigLinkBtn, camLinkBtn;

    public static ShipmentRecipientFragment newInstance() {
        return new ShipmentRecipientFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shipment_recipient_fragment, container, false);

        Button continueBtn = view.findViewById(R.id.continue_btn);
//

        shipInfo = view.findViewById(R.id.shipment_details);

        signatureName = view.findViewById(R.id.sigName);
        quantity = view.findViewById(R.id.qtyEntered);
        reff = view.findViewById(R.id.reffNo);
        comment = view.findViewById(R.id.commentsEntered);
        sigLinkBtn = view.findViewById(R.id.sigLinkBtn);
        camLinkBtn = view.findViewById(R.id.camLinkBtn);


        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(quantity.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "Quantity is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(status.getStatusRule().equals("S") && signatureName.equals("")) {
                    Toast.makeText(getActivity(), "Recipient Name is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(status.getStatusRule().equals("S")
                        && (ShipmentActivity.selectedTask.getSignature() == null
                            || ShipmentActivity.selectedTask.getSignature().equals(""))) {
                    Toast.makeText(getActivity(), "Recipient Signature is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(((status != null && status.getStatusRule().equals("C"))
                        || (reason != null && reason.getReasonRule().equals("C"))) && comment.equals("")) {
                    Toast.makeText(getActivity(), "Recipient is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                int qtyEnterbed = Integer.parseInt(quantity.getText().toString());

                ShipmentActivity.selectedTask.setQtyEntered(qtyEnterbed);
                ShipmentActivity.selectedTask.setDriverComment(comment.getText().toString());
                ShipmentActivity.selectedTask.setReffNo(reff.getText().toString());
                ShipmentActivity.selectedTask.setSignatureName(signatureName.getText().toString());

                Navigation.findNavController(view).navigate(R.id.action_shipment_recipient_to_shipment_delivered_to);
            }
        });

        sigLinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveDataEntered();
                Navigation.findNavController(view).navigate(R.id.action_shipment_recipient_to_shipment_signature);
            }
        });

        camLinkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDataEntered();
                Navigation.findNavController(view).navigate(R.id.action_shipment_recipient_to_shipmentPhotoFragment);
            }
        });

        if(ShipmentActivity.selectedTask.getSignature() != null
                    && !ShipmentActivity.selectedTask.getSignature().equals("")) {
            sigLinkBtn.setText("Signature Taken. Retake");
        }

        if(ShipmentActivity.selectedTask.getImageTaken() == 1) {
            camLinkBtn.setText("Image Taken. Take More");
        }

        TaskInfoEntity theTask = ShipmentActivity.selectedTask;

        ShipmentStatusRepository shipmentStatusRepository = new ShipmentStatusRepository(getActivity().getApplication());

        status = shipmentStatusRepository.getStatus(theTask.getStatusId());
        reason = shipmentStatusRepository.getReason(theTask.getReasonId());

        String shAllInfo = theTask.getBarcode();
        if(status != null) shAllInfo +=  "\n" + status.getStatusName();
        if(reason != null) shAllInfo +=  "\n" + reason.getReasonName();

        shipInfo.setText(shAllInfo);
        reff.setText(theTask.getReffNo());
        if(theTask.getQtyEntered() > 0) quantity.setText("" + theTask.getQtyEntered());

        comment.setText(theTask.getDriverComment());
        signatureName.setText(theTask.getSignatureName());

        /*Button addCommentBtn = view.findViewById(R.id.add_comment_btn);
        addCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_shipment_recipient_to_shipment_comment);
            }
        }); */

        return view;
    }

    public void saveDataEntered() {
        if(!quantity.getText().toString().trim().equals("")) {
            int qtyEnterbed = Integer.parseInt(quantity.getText().toString());
            ShipmentActivity.selectedTask.setQtyEntered(qtyEnterbed);
        }
        ShipmentActivity.selectedTask.setDriverComment(comment.getText().toString());
        ShipmentActivity.selectedTask.setReffNo(reff.getText().toString());
        ShipmentActivity.selectedTask.setSignatureName(signatureName.getText().toString());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        status.setText(ShipmentActivity.selectedTask.getStatusId());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ShipmentRecipientViewModel.class);
        // TODO: Use the ViewModel
    }

    public void goToSignatureFragment(View view) {

    }
}