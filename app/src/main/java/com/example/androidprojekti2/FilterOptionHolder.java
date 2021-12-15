package com.example.androidprojekti2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class FilterOptionHolder extends AndroidViewModel {

    private String lower = "";
    private String higher = "";
    private boolean sorter;
    private String url = "https://www.cheapshark.com/api/1.0/deals?sortBy=price&upperPrice=0&pageSize=10&pageNumber=";


    public FilterOptionHolder(@NonNull Application application) {
        super(application);

    }

    public String getLower() {
        return lower;
    }

    public void setLower(String lower) {
        this.lower = lower;
    }

    public String getHigher() {
        return higher;
    }

    public void setHigher(String higher) {
        this.higher = higher;
    }

    public String getUrl(){
        return url;
    }

    public void changeSorter(){
        sorter = !sorter;
    }





    public void makeUrl(){
        if(!sorter){
            if(lower.length() != 0 && higher.length() != 0) url = "https://www.cheapshark.com/api/1.0/deals?pageSize=10&upperPrice="+lower+"&lowerPrice="+higher+"&pageNumber=";
            else if(lower.length() == 0 && higher.length() != 0) url = "https://www.cheapshark.com/api/1.0/deals?pageSize=10&lowerPrice="+higher+"&pageNumber=";
            else if(lower.length() != 0 && higher.length() == 0) url = "https://www.cheapshark.com/api/1.0/deals?pageSize=10&upperPrice="+lower+"&pageNumber=";
            else url = "https://www.cheapshark.com/api/1.0/deals?pageSize=10&pageNumber=";
        } else {
            if(lower.length() != 0 && higher.length() != 0) url = "https://www.cheapshark.com/api/1.0/deals?pageSize=10&sortBy=price&upperPrice="+lower+"&lowerPrice="+higher+"&pageNumber=";
            else if(lower.length() == 0 && higher.length() != 0) url = "https://www.cheapshark.com/api/1.0/deals?pageSize=10&sortBy=price&lowerPrice="+higher+"&pageNumber=";
            else if(lower.length() != 0 && higher.length() == 0) url = "https://www.cheapshark.com/api/1.0/deals?pageSize=10&sortBy=price&upperPrice="+lower+"&pageNumber=";
            else url = "https://www.cheapshark.com/api/1.0/deals?pageSize=10&sortBy=price&pageNumber=";
        }
        lower = "";
        higher = "";
        sorter = false;
    }


}
