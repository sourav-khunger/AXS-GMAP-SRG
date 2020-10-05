package com.doozycod.axs.ui.shipment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.doozycod.axs.Activity.ShipmentActivity;
import com.doozycod.axs.R;
import com.doozycod.axs.ui.shipment.ViewModel.ShipmentSignatureViewModel;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShipmentSignatureFragment extends Fragment implements View.OnClickListener {

    private ShipmentSignatureViewModel viewModel;
    private TextView signatureTime;
    private SignaturePad signaturePad;
    private Button clearBtn;
    private Button saveBtn;

    public static ShipmentSignatureFragment newInstance() {
        return new ShipmentSignatureFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.shipment_signature_fragment, container, false);
        viewModel = new ViewModelProvider(getActivity()).get(ShipmentSignatureViewModel.class);
        signatureTime = root.findViewById(R.id.signature_date_text);
        signaturePad = root.findViewById(R.id.signature_pad);
        clearBtn = root.findViewById(R.id.clear_signature_btn);
        saveBtn = root.findViewById(R.id.save_signature_btn);

        clearBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getSignatureDate().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("sig", "inside observer");
                signatureTime.setText(s);
            }
        });

        viewModel.getSignatureByteArray().observe(getActivity(), new Observer<byte[]>() {
            @Override
            public void onChanged(byte[] bytes) {
                Bitmap SBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                if(SBitmap != null){
                    signaturePad.setSignatureBitmap(SBitmap);
                }
            }
        });

        viewModel.setSignatureDate();

        if(ShipmentActivity.selectedTask.getSignature() != null && !ShipmentActivity.selectedTask.getSignature().equals("")) {
            byte[] decodedString = Base64.decode(ShipmentActivity.selectedTask.getSignature(), Base64.DEFAULT);
            viewModel.setSignature(decodedString);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.clear_signature_btn:
                signaturePad.clear();
                viewModel.setSignatureDate();
                break;
            case R.id.save_signature_btn:
                saveSignature();
                break;
        }
    }

    private void saveSignature() {
        Bitmap signatureBitmap = signaturePad.getSignatureBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        signatureBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bai = byteArrayOutputStream.toByteArray();
        viewModel.setSignature(bai);

        String curDateTime= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        String base64Image = Base64.encodeToString(bai, Base64.DEFAULT);
        ShipmentActivity.selectedTask.setSignature(base64Image);
        ShipmentActivity.selectedTask.setSignatureTime(curDateTime);

        Navigation.findNavController(getView()).navigate(R.id.action_shipment_signature_to_shipment_recipient);
    }
}