package com.doozycod.axs.ui.shipment.ViewModel;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShipmentPhotoViewModel extends ViewModel {
    private MutableLiveData<Bitmap> bitmap = new MutableLiveData<>();

    public LiveData<Bitmap> getPhotoBitMap() {
        return bitmap;
    }


    public void setPhotoBitMap(Bitmap bitMap) {
        this.bitmap.setValue(
                bitMap
        );
    }
}