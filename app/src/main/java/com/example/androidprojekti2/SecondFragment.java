package com.example.androidprojekti2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.androidprojekti2.databinding.FragmentSecondBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment implements SelectListener{

    private FragmentSecondBinding binding;
    private GamesList gamelist;
    private StoreDataList storeList;
    private TextView titleText;
    private TextView cheapestEverText;
    private ImageView gameThumbnail;
    private List<DealModelClass> dealList;
    private DealDataList deallist;
    private ExtrasAdaptery adaptery;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState

    ) {
        gamelist = new ViewModelProvider(this.requireActivity()).get(GamesList.class);
        storeList = new ViewModelProvider(this.requireActivity()).get(StoreDataList.class);
        deallist = new ViewModelProvider(this.requireActivity()).get(DealDataList.class);
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        titleText = binding.gameTitleText;
        cheapestEverText = binding.cheapestPriceText;
        gameThumbnail = binding.gameThumbnail;
        recyclerView = binding.dealRecyclerView;
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        String url = "https://www.cheapshark.com/api/1.0/games?id=" + gamelist.getCurrentId();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject dealData;
                        String gameTitle = "";
                        String cheapestEver = "";
                        String gameThumb = "";
                        String storeTitle = "";
                        String normalPrice = "";
                        String dealPrice = "";
                        String storeThumb = "";
                        String storeId = "";
                        String dealId = "";

                        dealList = new ArrayList<>();

                        try {
                            JSONObject data = response.getJSONObject("info");
                            JSONObject cheapData = response.getJSONObject("cheapestPriceEver");
                            JSONArray deals = response.getJSONArray("deals");
                            gameTitle = data.getString("title");
                            cheapestEver = cheapData.getString("price");
                            gameThumb = data.getString("thumb");
                            titleText.setText(gameTitle);
                            cheapestEverText.setText("Cheapest price ever: " + cheapestEver + "$");
                            Glide.with(getView()).load(gameThumb).into(gameThumbnail);

                            for(int i = 0; i < deals.length(); i++){
                                dealData = deals.getJSONObject(i);
                                normalPrice = dealData.getString("retailPrice");
                                dealPrice = dealData.getString("price");
                                storeId = dealData.getString("storeID");
                                dealId = dealData.getString("dealID");
                                int x = Integer.parseInt(storeId);
                                StoreDataModel store = storeList.getStoreWithId(x);
                                DealModelClass deal = new DealModelClass();

                                deal.setDealId(dealId);
                                deal.setStoreName(store.getStoreName());
                                deal.setDealPrice(dealPrice + "$");
                                deal.setNormalPrice(normalPrice + "$");
                                deal.setStoreThumb(store.getStoreLogoUrl());

                                deallist.addDealIntoList(deal);


                            }
                            PutDataIntoRecyclerView(deallist.getLista());



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        

                    }
                });

        queue.add(jsonObjectRequest);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void PutDataIntoRecyclerView(List<DealModelClass> list){
        adaptery = new ExtrasAdaptery(this.getContext(), this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adaptery);
    }


    @Override
    public void onClicked(int position) {

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.cheapshark.com/redirect?dealID=" + deallist.getLista().get(position).getDealId()));
        startActivity(browserIntent);
    }
}