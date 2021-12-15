package com.example.androidprojekti2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidprojekti2.databinding.FragmentFirstBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment implements SelectListener{

    private FragmentFirstBinding binding;
    List<GameModelClass> gameList;
    RecyclerView recyclerView;
    GamesList gamelist;
    StoreDataList storeList;
    boolean isChecked;
    private Adaptery adaptery;
    private FilterOptionHolder filter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        gamelist = new ViewModelProvider(this.requireActivity()).get(GamesList.class);
        storeList = new ViewModelProvider(this.requireActivity()).get(StoreDataList.class);
        filter = new ViewModelProvider(this.requireActivity()).get(FilterOptionHolder.class);

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        gameList = new ArrayList<>();
        recyclerView = binding.gameRecyclerView;

        if(gamelist.getListSize() != 0){
            PutDataIntoRecyclerView(gamelist);
        }

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(gamelist.getListSize() == 0) getMoreGameData();

        if(gamelist.getListSize() != 0){
            PutDataIntoRecyclerView(gamelist);
        }

        if(storeList.getStoreList().size() == 0){
            getStoreData();
        }

        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String x = binding.gameSearchText.getText().toString();
                String url;

                if(isChecked) url = "https://www.cheapshark.com/api/1.0/games?title=" + x + "&exact=1";
                else url = "https://www.cheapshark.com/api/1.0/games?title=" + x;

                RequestQueue queue = Volley.newRequestQueue(getActivity());


                gamelist.resetList();

                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null , new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject gameData;
                        String gameTitle = "";
                        String gameSalePrice = "";
                        String gameNormalPrice = "";
                        String gameImageUrl = "";
                        String gameId = "";

                        try {

                            if(response.length() == 0){
                                Toast.makeText(getActivity(), "No results", Toast.LENGTH_SHORT).show();
                            }

                            for(int i = 0; i < response.length(); i++){

                                gameData = response.getJSONObject(i);
                                gameId = gameData.getString("gameID");
                                gameTitle = gameData.getString("external");
                                gameSalePrice = gameData.getString("cheapest");
                                gameImageUrl = gameData.getString("thumb");

                                GameModelClass model = new GameModelClass();

                                model.setName(gameTitle);
                                model.setId(gameId);
                                model.setSalePrice(gameSalePrice);
                                model.setImgUrl(gameImageUrl);

                                gamelist.addGame(model);
                            }

                            gamelist.nextPage();
                            PutDataIntoRecyclerView(gamelist);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Something wrong", Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(request);

            }
        });

        binding.buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMoreGameData();
            }
        });

        binding.exactSearchSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isChecked = !isChecked;
            }
        });



        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_ThirdFragment);
            }
        });


        binding.resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gamelist.resetList();
                PutDataIntoRecyclerView(gamelist);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void PutDataIntoRecyclerView(GamesList listOfGames){
        adaptery = new Adaptery(this.getContext(), listOfGames.getGameList(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adaptery);
    }

    @Override
    public void onClicked(int position) {
        gamelist.setCurrentId(gamelist.getGameList().get(position).getId());
        NavHostFragment.findNavController(FirstFragment.this)
                .navigate(R.id.action_FirstFragment_to_SecondFragment);
    }

    public void getStoreData(){
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        String url = "https://www.cheapshark.com/api/1.0/stores";


        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject storeData;
                JSONObject imageData;
                String storeName = "";
                String storeLogoUrl = "";
                try {
                    for(int i = 0; i < response.length(); i++){
                        storeData = response.getJSONObject(i);
                        imageData = storeData.getJSONObject("images");
                        storeName = storeData.getString("storeName");
                        storeLogoUrl = imageData.getString("logo");


                        StoreDataModel entry = new StoreDataModel();
                        entry.setStoreLogoUrl(storeLogoUrl);
                        entry.setStoreName(storeName);


                        storeList.addStoreIntoList(entry);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Something wrong", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }

    public void getMoreGameData(){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = filter.getUrl()+ gamelist.getPage();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null , new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject gameData;
                String gameTitle = "";
                String gameSalePrice = "";
                String gameNormalPrice = "";
                String gameImageUrl = "";
                String gameId = "";

                try {

                    if(response.length() == 0){
                        Toast.makeText(getActivity(), "No more results", Toast.LENGTH_SHORT).show();
                    }

                    for(int i = 0; i < response.length(); i++){

                        gameData = response.getJSONObject(i);
                        gameTitle = gameData.getString("title");
                        gameSalePrice = gameData.getString("salePrice");
                        gameNormalPrice = gameData.getString("normalPrice");
                        gameImageUrl = gameData.getString("thumb");
                        gameId = gameData.getString("gameID");

                        GameModelClass model = new GameModelClass();

                        model.setName(gameTitle);
                        model.setSalePrice(gameSalePrice);
                        model.setNormalPrice(gameNormalPrice);
                        model.setImgUrl(gameImageUrl);
                        model.setId(gameId);
                        gamelist.addGame(model);
                    }

                    gamelist.nextPage();
                    PutDataIntoRecyclerView(gamelist);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Something wrong", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }
}