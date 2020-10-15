package com.doozycod.axs.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.doozycod.axs.Database.Entities.TaskInfoEntity;
import com.doozycod.axs.Database.Repository.TaskInfoRepository;
import com.doozycod.axs.Database.ViewModel.TaskInfoViewModel;
import com.doozycod.axs.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ConsolicateBillsDetailsActivity extends AppCompatActivity {
    TextView barcodeTxt, cashCollect;
    EditText enteredQuantityET, shipRefET, weightET, comments, notice;
    Spinner paymentTypeSpinner, accessorialSpinner;
    Button saveBtn;
    private TaskInfoRepository mTaskInfoRepository;
    private TaskInfoViewModel taskInfoViewModel;
    List<String> paymentCOD = new ArrayList<>();
    List<Integer> paymentIntCOD = new ArrayList<Integer>();
    String paymentRadio = "", areaType = "", accessorialRadio = "";
    RadioGroup paymentGroup, areaGroup, accessorialGroup;
    RadioButton cadRadio, usdRadio, resiRadio, comRadio, yesRadio, noRadio;

    private void initUI() {

        mTaskInfoRepository = new TaskInfoRepository(this.getApplication());
        taskInfoViewModel = new ViewModelProvider(this).get(TaskInfoViewModel.class);


        cashCollect = findViewById(R.id.cashCollect);
        accessorialGroup = findViewById(R.id.accessorial);
        areaGroup = findViewById(R.id.radioGroup);
        comRadio = findViewById(R.id.commercialRadio);
        noRadio = findViewById(R.id.noRadio);
        yesRadio = findViewById(R.id.yesRadio);
        resiRadio = findViewById(R.id.residentRadio);
        usdRadio = findViewById(R.id.paymentUSD);
        cadRadio = findViewById(R.id.paymentCAD);
        paymentGroup = findViewById(R.id.paymentCurrency);
        saveBtn = findViewById(R.id.saveBtn);
        barcodeTxt = findViewById(R.id.barcodeTxt);
        enteredQuantityET = findViewById(R.id.qtyEntered);
        shipRefET = findViewById(R.id.reffNo);
        weightET = findViewById(R.id.weightEntered);
        comments = findViewById(R.id.comments);
        notice = findViewById(R.id.driverNotice);
        paymentTypeSpinner = findViewById(R.id.paymentTypeSpinner);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consolicate_bills_details);

        initUI();
        long taskId = getIntent().getLongExtra("taskId", 0);
        String locationKey = getIntent().getStringExtra("locationKey");

        TaskInfoEntity mTaskInfoEntity = mTaskInfoRepository.getTaskInfoWithId(Long.toString(taskId));
        barcodeTxt.setText(mTaskInfoEntity.getBarcode());
        comments.setText(mTaskInfoEntity.getDriverComment());
        notice.setText(mTaskInfoEntity.getDriverNotice());
        weightET.setText(mTaskInfoEntity.getWeight());
        shipRefET.setText(mTaskInfoEntity.getReffNo());
        enteredQuantityET.setText(mTaskInfoEntity.getQuantity() + "");
        if(mTaskInfoEntity.getCashCollect()!=null ){
            cashCollect.setText(mTaskInfoEntity.getCashCollect());
        }
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

        if (mTaskInfoEntity.getCodCurrency() != null) {
            if (mTaskInfoEntity.getCodCurrency().equals("0")) {
                cadRadio.setChecked(true);
                usdRadio.setChecked(false);
                paymentRadio = "0";

            }
            if (mTaskInfoEntity.getCodCurrency().equals("1")) {
                usdRadio.setChecked(true);
                cadRadio.setChecked(false);
                paymentRadio = "1";

            }
        }
        if (mTaskInfoEntity.getAccessorial() != null) {
            if (mTaskInfoEntity.getAccessorial().equals("0")) {
                noRadio.setChecked(true);
                yesRadio.setChecked(false);
                accessorialRadio = "0";

            }
            if (mTaskInfoEntity.getAccessorial().equals("1")) {
                yesRadio.setChecked(true);
                noRadio.setChecked(false);
                accessorialRadio = "1";

            }
        }
        if (mTaskInfoEntity.getAreaType() != null) {
            if (mTaskInfoEntity.getAreaType().equals("R")) {
                resiRadio.setChecked(true);
                comRadio.setChecked(false);
                areaType = "R";

            }
            if (mTaskInfoEntity.getAreaType().equals("C")) {
                comRadio.setChecked(true);
                resiRadio.setChecked(false);
                areaType = "C";

            }
        }
        if (mTaskInfoEntity.getCod() == 0) {

        } else {
            if (mTaskInfoEntity.getCod() == 1) {
                paymentTypeSpinner.setSelection(1);

            }
            if (mTaskInfoEntity.getCod() == 2) {
                paymentTypeSpinner.setSelection(2);

            }
            if (mTaskInfoEntity.getCod() == 3) {
                paymentTypeSpinner.setSelection(3);

            }
            if (mTaskInfoEntity.getCod() == 4) {
                paymentTypeSpinner.setSelection(4);

            }
        }

        ArrayAdapter paymentAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, paymentCOD);

        paymentTypeSpinner.setAdapter(paymentAdapter);
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

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shipRefET.getText().toString().equals("")) {
                    Toast.makeText(ConsolicateBillsDetailsActivity.this, "Reff no is mandatory!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (weightET.getText().toString().equals("")) {
                    Toast.makeText(ConsolicateBillsDetailsActivity.this, "Enter weight!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (enteredQuantityET.getText().toString().equals("")) {
                    Toast.makeText(ConsolicateBillsDetailsActivity.this, "Enter quantity!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (accessorialRadio.equals("")) {
                    Toast.makeText(ConsolicateBillsDetailsActivity.this, "Please Select Accessorial!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (areaType.equals("")) {
                    Toast.makeText(ConsolicateBillsDetailsActivity.this, "Please select Area Type!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (paymentRadio.equals("")) {
                    Toast.makeText(ConsolicateBillsDetailsActivity.this, "Please select payment currency!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    mTaskInfoEntity.setAccessorial(accessorialRadio);
                    mTaskInfoEntity.setAreaType(areaType);
                    mTaskInfoEntity.setCod(paymentIntCOD.get(paymentTypeSpinner.getSelectedItemPosition()));
                    mTaskInfoEntity.setCodCurrency(paymentRadio);
                    mTaskInfoEntity.setQtyEntered(Integer.parseInt(enteredQuantityET.getText().toString()));
                    mTaskInfoEntity.setDriverComment(comments.getText().toString());
                    mTaskInfoEntity.setDriverNotice(notice.getText().toString());
                    mTaskInfoEntity.setWeightEntered(Double.parseDouble(weightET.getText().toString()));
                    mTaskInfoEntity.setReffNo(shipRefET.getText().toString());
                    mTaskInfoRepository.update(mTaskInfoEntity);
                    Log.e("TAG", "onClick: " + new Gson().toJson(mTaskInfoEntity));
                }

            }
        });
        taskInfoViewModel.getTaskInfoByLocationKey(locationKey).observe(this, new Observer<List<TaskInfoEntity>>() {
            @Override
            public void onChanged(List<TaskInfoEntity> taskInfoEntities) {

            }
        });
    }

}