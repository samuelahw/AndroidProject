package com.example.androidprojekti2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

//Store data list class which can be used between fragments.
public class StoreDataList extends AndroidViewModel {

    private List<StoreDataModel> storeList;

    public StoreDataList(@NonNull Application application) {
        super(application);

        storeList = new ArrayList<>();
    }

    public List<StoreDataModel> getStoreList() {
        return storeList;
    }

    public StoreDataModel getStoreWithId(int id){
        return storeList.get(id-1);
    }

    public void addStoreIntoList(StoreDataModel item){
        storeList.add(item);
    }
}
