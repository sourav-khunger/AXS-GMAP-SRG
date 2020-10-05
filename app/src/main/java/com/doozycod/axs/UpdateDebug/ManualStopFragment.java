package com.doozycod.axs.UpdateDebug;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.doozycod.axs.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ManualStopFragment extends Fragment {

    EditText durationET, reasonET;
    TextView dateSelectionTV;
    Button makethisStopButton;
    TimePickerDialog timePickerDialog;
    String date = "", timeString = "";

    public ManualStopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manual_stop, container, false);
        initUI(view);
        onClickEvent();
        return view;
    }

    void showDialog(String text) {
        Dialog dialog = new Dialog(getContext(), R.style.MyDialogTheme);
        dialog.setContentView(R.layout.server_message_dialog);
        Button closeDialog = dialog.findViewById(R.id.closeDialog);
        TextView messageTxt = dialog.findViewById(R.id.messageTxt);
        messageTxt.setText(text);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    private void onClickEvent() {
        makethisStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reasonET.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please enter reason", Toast.LENGTH_SHORT).show();
                } else {
                    showDialog(reasonET.getText().toString() + " Stop Added successfully at time " + date + " " + timeString
                            + " Please wait until auto refresh manually to see the stop.");

                }
            }
        });
        dateSelectionTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        timePicker.setIs24HourView(true);
//                        Log.e(TAG, "onTimeSet: ", );
                        String hr = "";
                        if (i < 10) {
                            hr = "0" + i;
                        } else hr = "" + i;
                        timeString = hr + ":" + i1;
                        dateSelectionTV.setText(hr + ":" + i1);
                    }
                }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });
    }

    private void initUI(View view) {
        reasonET = view.findViewById(R.id.reasonET);
        durationET = view.findViewById(R.id.durationET);
        dateSelectionTV = view.findViewById(R.id.timeTxt);
        makethisStopButton = view.findViewById(R.id.makethisStopButton);
    }
}