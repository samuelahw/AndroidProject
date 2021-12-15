package com.example.androidprojekti2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

public class DealDataList extends AndroidViewModel {

    private List<DealModelClass> lista;


    public DealDataList(@NonNull Application application) {
        super(application);
        lista = new ArrayList<>();

    }

    public List<DealModelClass> getLista() {
        return lista;
    }

    public void addDealIntoList(DealModelClass model){
        lista.add(model);
    }
}
