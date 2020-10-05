package com.doozycod.axs.UpdateDebug;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.doozycod.axs.R;

import java.io.File;

import static android.content.ContentValues.TAG;


public class ReturnParcelFragment extends Fragment {

    public ReturnParcelFragment() {
        // Required empty public constructor
    }
    ImageView frontView, backView, rightView, leftView;
    String truckViewType = "";
    Button reTurnParcel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_return_parcel, container, false);
        initUI(view);
        onClickEvents();

        return view;
    }

    void onClickEvents() {
        frontView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                truckViewType = "front";
                ImagePicker.Companion.with(getActivity())
                        .compress(100)
                        .cameraOnly()
                        .start();
            }
        });
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                truckViewType = "back";
                ImagePicker.Companion.with(getActivity())
                        .compress(100)
                        .cameraOnly()
                        .start();
            }
        });
        leftView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                truckViewType = "left";

                ImagePicker.Companion.with(getActivity())
                        .compress(100)
                        .cameraOnly()

                        .start();
            }
        });
        rightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                truckViewType = "right";

                ImagePicker.Companion.with(getActivity())
                        .compress(100)
                        .cameraOnly()
                        .start();
            }
        });
        reTurnParcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.frameOut, new ReturnScanFragment())
                        .addToBackStack("")
                        .commit();
            }
        });
    }

    private void initUI(View v) {
        backView = v.findViewById(R.id.backView);
        reTurnParcel = v.findViewById(R.id.reTurnParcel);
        frontView = v.findViewById(R.id.frontView);
        leftView = v.findViewById(R.id.leftView);
        rightView = v.findViewById(R.id.rightView);
    }




    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            Uri fileUri = data.getData();

            //You can get File object from intent
            File file = ImagePicker.Companion.getFile(data);

            //You can also get File Path from intent
            String filePath = ImagePicker.Companion.getFilePath(data);
//                    fileString = filePath;
//                    selectedFiles.add(filePath);
            Log.e(TAG, "onActivityResult: " + filePath);

            if (truckViewType.equals("front")) {
                Glide.with(getActivity()).load(file).into(frontView);
            }
            if (truckViewType.equals("back")) {
                Glide.with(getActivity()).load(file).into(backView);

            }
            if (truckViewType.equals("right")) {
                Glide.with(getActivity()).load(file).into(rightView);

            }
            if (truckViewType.equals("left")) {
                Glide.with(getActivity()).load(file).into(leftView);

            }

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(getContext(), ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Task Cancelled", Toast.LENGTH_SHORT).show();
        }

    }
}


