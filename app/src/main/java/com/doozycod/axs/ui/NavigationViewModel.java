package com.doozycod.axs.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.doozycod.axs.POJO.DriverInfo;

public class NavigationViewModel extends ViewModel {
    private final MutableLiveData<DriverInfo> driver = new MutableLiveData<>();

    public LiveData<DriverInfo> getDriver() {
        return driver;
    }

    public void setDriver(DriverInfo driver){
        this.driver.setValue(driver);
    }
}
