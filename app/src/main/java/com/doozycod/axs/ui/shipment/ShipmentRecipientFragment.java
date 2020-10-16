package com.doozycod.axs.ui.shipment;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.doozycod.axs.Activity.ConsolicateBillsDetailsActivity;
import com.doozycod.axs.Activity.ShipmentActivity;
import com.doozycod.axs.Database.Entities.ReasonEntity;
import com.doozycod.axs.Database.Entities.StatusEntity;
import com.doozycod.axs.Database.Entities.TaskInfoEntity;
import com.doozycod.axs.Database.Repository.ShipmentStatusRepository;
import com.doozycod.axs.Database.ViewModel.TaskInfoViewModel;
import com.doozycod.axs.R;
import com.doozycod.axs.UpdateDebug.Adapter.SelectedConsolidateAdapter;
import com.doozycod.axs.UpdateDebug.CosolidateBillsActivity;
import com.doozycod.axs.ui.shipment.ViewModel.ShipmentRecipientViewModel;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ShipmentRecipientFragment extends Fragment {

    private ShipmentRecipientViewModel mViewModel;

    private TextView signatureName, quantity, reff, comment;
    private TextView shipInfo;

    private ReasonEntity reason;
    private StatusEntity status;

    private Button sigLinkBtn, camLinkBtn, consolidateBtn, selectedBillsBtn;
    String statusType = "", reasonType = "";

    private TaskInfoViewModel taskInfoViewModel;
    List<TaskInfoEntity> taskInfoEntityList;
    String taskBarcode;


    Spinner paymentTypeSpinner, accessorialSpinner;
    List<String> paymentCOD = new ArrayList<>();
    List<Integer> paymentIntCOD = new ArrayList<Integer>();
    String paymentRadio = "", areaType = "", accessorialRadio = "";
    RadioGroup paymentGroup, areaGroup, accessorialGroup;
    RadioButton cadRadio, usdRadio, resiRadio, comRadio, yesRadio, noRadio;


    public static ShipmentRecipientFragment newInstance() {
        return new ShipmentRecipientFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shipment_recipient_fragment, container, false);

        Button continueBtn = view.findViewById(R.id.continue_btn);
        accessorialGroup = view.findViewById(R.id.accessorial);
        areaGroup = view.findViewById(R.id.radioGroup);
        comRadio = view.findViewById(R.id.commercialRadio);
        noRadio = view.findViewById(R.id.noRadio);
        yesRadio = view.findViewById(R.id.yesRadio);
        resiRadio = view.findViewById(R.id.residentRadio);
        usdRadio = view.findViewById(R.id.paymentUSD);
        cadRadio = view.findViewById(R.id.paymentCAD);
        paymentGroup = view.findViewById(R.id.paymentCurrency);

        shipInfo = view.findViewById(R.id.shipment_details);
        selectedBillsBtn = view.findViewById(R.id.selectedConsolidateBills);

        signatureName = view.findViewById(R.id.sigName);
        quantity = view.findViewById(R.id.qtyEntered);
        reff = view.findViewById(R.id.reffNo);
        comment = view.findViewById(R.id.commentsEntered);
        sigLinkBtn = view.findViewById(R.id.sigLinkBtn);
        camLinkBtn = view.findViewById(R.id.camLinkBtn);
        consolidateBtn = view.findViewById(R.id.consoBillsBtn);
        paymentTypeSpinner = view.findViewById(R.id.paymentTypeSpinner);

//
        taskInfoViewModel = new ViewModelProvider(this).get(TaskInfoViewModel.class);
        taskInfoEntityList = new ArrayList<TaskInfoEntity>();


//        payment type list
        paymentCOD.add("    NO");
        paymentCOD.add("    CASH");
        paymentCOD.add("    CHEQUE");
        paymentCOD.add("    CREDIT CARD");
        paymentCOD.add("    ON ACCOUNT");

        paymentIntCOD.add(0);
        paymentIntCOD.add(1);
        paymentIntCOD.add(2);
        paymentIntCOD.add(3);
        paymentIntCOD.add(4);


        ArrayAdapter paymentAdapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, paymentCOD);

        paymentTypeSpinner.setAdapter(paymentAdapter);


        taskBarcode = PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString("ConsolidateBills", "");

//        fetch task details
        TaskInfoEntity theTask = ShipmentActivity.selectedTask;


        if (theTask.getCodCurrency() != null) {
            if (theTask.getCodCurrency().equals("0")) {
                cadRadio.setChecked(true);
                usdRadio.setChecked(false);
                paymentRadio = "0";

            }
            if (theTask.getCodCurrency().equals("1")) {
                usdRadio.setChecked(true);
                cadRadio.setChecked(false);
                paymentRadio = "1";

            }
        }
        if (theTask.getAccessorial() != null) {
            if (theTask.getAccessorial().equals("0")) {
                noRadio.setChecked(true);
                yesRadio.setChecked(false);
                accessorialRadio = "0";

            }
            if (theTask.getAccessorial().equals("1")) {
                yesRadio.setChecked(true);
                noRadio.setChecked(false);
                accessorialRadio = "1";

            }
        }
        if (theTask.getAreaType() != null) {
            if (theTask.getAreaType().equals("R")) {
                resiRadio.setChecked(true);
                comRadio.setChecked(false);
                areaType = "R";

            }
            if (theTask.getAreaType().equals("C")) {
                comRadio.setChecked(true);
                resiRadio.setChecked(false);
                areaType = "C";

            }
        }
        int cod = (int) theTask.getCod();
        Log.e("TAG", "onCreateView: " + cod);
        if (cod == 0) {
            paymentTypeSpinner.setSelection(0);
        }
        if (cod == 1) {
            paymentTypeSpinner.setSelection(1);
        }
        if (cod == 2) {
            paymentTypeSpinner.setSelection(2);
        }
        if (cod == 3) {
            paymentTypeSpinner.setSelection(3);
        }
        if (cod == 4) {
            paymentTypeSpinner.setSelection(4);
        }


        paymentTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        paymentGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (cadRadio.isChecked()) {
                    paymentRadio = "0";
                }
                if (usdRadio.isChecked()) {
                    paymentRadio = "1";
                }
            }
        });
        areaGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (resiRadio.isChecked()) {
                    areaType = "R";
                }
                if (comRadio.isChecked()) {
                    areaType = "C";
                }
            }
        });
        accessorialGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (yesRadio.isChecked()) {
                    accessorialRadio = "1";
                }
                if (noRadio.isChecked()) {
                    accessorialRadio = "0";
                }
            }
        });


        ShipmentStatusRepository shipmentStatusRepository = new ShipmentStatusRepository(getActivity().getApplication());

        status = shipmentStatusRepository.getStatus(theTask.getStatusId());
        reason = shipmentStatusRepository.getReason(theTask.getReasonId());
        String tempStatus = new Gson().toJson(status);
        String tempReason = new Gson().toJson(reason);
//        Log.e("TAGTAG", "onCreateView: REASON " + tempReason);
//        Log.e("TAGTAG", "onCreateView: STATUS " + tempStatus);

        if (status != null) statusType = status.getStatusRule();

        if (reason != null) reasonType = reason.getReasonRule();
//            Log.e("TAGTAG", "onCreateView: " + reason.getReasonRule());

        consolidateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CosolidateBillsActivity.class);
                intent.putExtra("locationKey", ShipmentActivity.selectedTask.getLocationKey());
                intent.putExtra("name", ShipmentActivity.selectedTask.getName());
                startActivityForResult(intent, 123);
            }
        });


        selectedBillsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskBarcode = PreferenceManager.getDefaultSharedPreferences(getActivity())
                        .getString("ConsolidateBills", "");

                if (taskBarcode.equals("")) {
                    Toast.makeText(getActivity(), "No Selected bills found!", Toast.LENGTH_SHORT).show();
                } else {
                    showSelectedBills();
                }
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (quantity.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "Quantity is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (statusType.equals("S") && signatureName.equals("")) {
                    Toast.makeText(getActivity(), "Recipient Name is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (statusType.equals("S")
                        && (ShipmentActivity.selectedTask.getSignature() == null
                        || ShipmentActivity.selectedTask.getSignature().equals(""))) {
                    Toast.makeText(getActivity(), "Recipient Signature is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if ((status != null && statusType.equals("C"))) {
                    Toast.makeText(getActivity(), "Comment is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if ((reason != null && reasonType.equals("C")) && comment.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Comment is required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ((status != null && statusType.equals("P")) &&
                        ShipmentActivity.selectedTask.getImageTaken() == 0 || (reason != null && reasonType.equals("P")) &&
                        ShipmentActivity.selectedTask.getImageTaken() == 0) {
                    Toast.makeText(getActivity(), "Photo is required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (accessorialRadio.equals("")) {
                    Toast.makeText(getActivity(), "Please Select Accessorial!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (areaType.equals("")) {
                    Toast.makeText(getActivity(), "Please select Area Type!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (paymentRadio.equals("")) {
                    Toast.makeText(getActivity(), "Please select payment currency!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int qtyEnterbed = Integer.parseInt(quantity.getText().toString());

                ShipmentActivity.selectedTask.setAccessorial(accessorialRadio);
                ShipmentActivity.selectedTask.setAreaType(areaType);
                ShipmentActivity.selectedTask.setCod(paymentIntCOD.get(paymentTypeSpinner.getSelectedItemPosition()));
                ShipmentActivity.selectedTask.setCodCurrency(paymentRadio);
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

        if (ShipmentActivity.selectedTask.getSignature() != null
                && !ShipmentActivity.selectedTask.getSignature().equals("")) {
            sigLinkBtn.setText("Signature Taken. Retake");
        }

        if (ShipmentActivity.selectedTask.getImageTaken() == 1) {
            camLinkBtn.setText("Image Taken. Take More");
        }


        String shAllInfo = theTask.getBarcode();
        if (status != null) shAllInfo += "\n" + status.getStatusName();
        if (reason != null) shAllInfo += "\n" + reason.getReasonName();

        shipInfo.setText(shAllInfo);
        reff.setText(theTask.getReffNo());
        if (theTask.getQtyEntered() > 0) quantity.setText("" + theTask.getQtyEntered());

        comment.setText(theTask.getDriverComment());
        signatureName.setText(theTask.getSignatureName());

        return view;
    }


    void showSelectedBills() {
        Dialog dialog = new Dialog(getActivity(), R.style.MyDialogTheme);
        dialog.setContentView(R.layout.selected_consolidate_dialog);
        RecyclerView recyclerView = dialog.findViewById(R.id.selectedBillsRecyclerView);
        Button closeBtn = dialog.findViewById(R.id.closeDialogButton);
        dialog.setCancelable(false);

        if (!taskBarcode.equals("")) {
            String[] strings = TextUtils.split(taskBarcode, ",");
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(new SelectedConsolidateAdapter(Arrays.asList(strings), getActivity()));
        }
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
//                finish();
            }
        });
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 123) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // get the list of strings here
                ArrayList<String> myHosts = (ArrayList<String>) data.getSerializableExtra("myHosts"); // key must be matching
                // do operations on the list
                Log.e("TAG", "onActivityResult: " + myHosts);
            }
        }
    }

    public void saveDataEntered() {
        if (!quantity.getText().toString().trim().equals("")) {
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