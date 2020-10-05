package com.doozycod.axs.UpdateDebug;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.doozycod.axs.R;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.ContentValues.TAG;

public class MenuFragment extends Fragment {
    Button changeDateButton, emailButton, selfDispatchButton, makeManualStopButton, accessWebButton;
    DatePickerDialog datePickerDialog;


    public MenuFragment() {
        // Required empty public constructor
    }

    String formattedDate;
    String month;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        initUI(view);
        datePickerDialog = new DatePickerDialog(getContext());
        DateFormat df = new SimpleDateFormat("dd"); // Just the year, with 2 digits
        DateFormat dfMonth = new SimpleDateFormat("MMM"); // Just the year, with 2 digits
        formattedDate = df.format(Calendar.getInstance().getTime());
        month = dfMonth.format(Calendar.getInstance().getTime());
        month = month.substring(0, 3).toUpperCase();
        emailButton.setText("Email DDTS - " + month + "/" + formattedDate);

//        fileprovider exception
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        onClicks();
        return view;
    }

    void showDialog() {
        Dialog dialog = new Dialog(getContext(), R.style.MyDialogTheme);
        dialog.setContentView(R.layout.email_message_dialog);
        Button closeDialog = dialog.findViewById(R.id.closeButton);
        Button sendMailButton = dialog.findViewById(R.id.sendButton);

        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        sendMailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filename = "1234.pdf";
                File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), filename);
                Log.e(TAG, "onClick: " + filelocation);
//                Uri path = Uri.fromFile(filelocation);
                Uri path = FileProvider.getUriForFile(
                        getContext(),
                        "com.example.homefolder.example.provider", //(use your app signature + ".provider" )
                        filelocation);
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
// set the type to 'email'
                emailIntent.setType("vnd.android.cursor.dir/email");
                String to[] = {"skype@fleetopticsinc.com"};
                emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
// the attachment
                emailIntent.putExtra(Intent.EXTRA_STREAM, path);
// the mail subject
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void onClicks() {
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();

            }
        });
        makeManualStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.frameOut, new ManualStopFragment())
                        .addToBackStack("")
                        .commit();

            }
        });
        accessWebButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), ScannerActivity.class));
            }
        });
        selfDispatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.frameOut, new SelfdispatchFragment())
                        .addToBackStack("")
                        .commit();
            }
        });
        changeDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Log.e(TAG, "onDateSet: " + i + " " + i1 + " " + i2);
            }
        });
    }

    private void initUI(View view) {
        changeDateButton = view.findViewById(R.id.changeDateButton);
        emailButton = view.findViewById(R.id.emailButton);
        selfDispatchButton = view.findViewById(R.id.selfDispatchButton);
        makeManualStopButton = view.findViewById(R.id.mmButton);
        accessWebButton = view.findViewById(R.id.accessButton);
    }
}