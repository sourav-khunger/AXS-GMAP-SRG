package com.doozycod.axs.ui.shipment.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShipmentSignatureViewModel extends ViewModel {
    private MutableLiveData<String> signatureDate = new MutableLiveData<>();
    private MutableLiveData<byte[]> signatureByteArray = new MutableLiveData<>();

    public LiveData<String> getSignatureDate() {
        return signatureDate;
    }

    public void setSignatureDate() {
        this.signatureDate.setValue(
                new SimpleDateFormat("MM/dd/yy HH:mm").format(new Date())
        );
    }

    public LiveData<byte[]> getSignatureByteArray() {
        return signatureByteArray;
    }

    public void setSignature(byte[] signature){
        this.signatureByteArray.setValue(signature);
    }
}