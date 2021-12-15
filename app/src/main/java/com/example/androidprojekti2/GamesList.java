package com.example.androidprojekti2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

//Games list class which can be used between fragments
public class GamesList extends AndroidViewModel {

    private List<GameModelClass> gameList;
    private int page;
    private String currentId;

    public GamesList(@NonNull Application application) {
        super(application);
        gameList = new ArrayList<>();
        page = 0;
    }

    public void addGame(GameModelClass game){
        gameList.add(game);
    }

    public List<GameModelClass> getGameList(){
        return gameList;
    }

    public int getListSize(){
        return gameList.size();
    }

    public void nextPage(){
        page+=1;
    }

    public int getPage(){
        return page;
    }

    public void resetList(){
        gameList = new ArrayList<>();
        page = 0;
    }

    public void setCurrentId(String s){
        currentId = s;
    }

    public String getCurrentId(){
        return currentId;
    }
}
