package com.example.androidprojekti2;

public class StoreDataModel {

    private String storeName;
    private String storeLogoUrl;

    public StoreDataModel(String storeLogoUrl, String name) {
        this.storeLogoUrl = "https://www.cheapshark.com" + storeLogoUrl;
        this.storeName = name;
    }

    public StoreDataModel(){
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreLogoUrl() {
        return  storeLogoUrl;
    }

    public void setStoreLogoUrl(String storeLogoUrl) {
        this.storeLogoUrl = storeLogoUrl;
    }
}
