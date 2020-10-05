package com.doozycod.axs.Database.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.doozycod.axs.Database.Entities.DriverInfoEntity;
import com.doozycod.axs.Database.Repository.DriverInfoRepository;

import java.util.List;

public class DriverInfoViewModel extends AndroidViewModel {

    private DriverInfoRepository driverInfoRepository;
    // private MutableLiveData<List<DriverInfoEntity>> driverInfos;


    public DriverInfoViewModel(@NonNull Application application) {
        super(application);
        driverInfoRepository = new DriverInfoRepository(application);
    }

    public LiveData<List<DriverInfoEntity>> getDriverInfos(){return  driverInfoRepository.getDriverInfos();}

    public void insertDriverInfoEntity (DriverInfoEntity driverInfoEntity){
        driverInfoRepository.insert(driverInfoEntity);
    }
    public void deleteAll (){
        driverInfoRepository.deleteAll();
    }

}
