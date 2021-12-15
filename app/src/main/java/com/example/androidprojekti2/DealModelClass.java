package com.example.androidprojekti2;

public class DealModelClass {

    String storeName;
    String normalPrice;
    String dealPrice;
    String storeThumb;
    String dealId;

    public DealModelClass(){

    }
    public DealModelClass(String name, String nPrice, String dPrice, String sThumb){
        storeName = name;
        normalPrice = nPrice;
        dealPrice = dPrice;
        storeThumb = sThumb;
    }
    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(String normalPrice) {
        this.normalPrice = normalPrice;
    }

    public String getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(String dealPrice) {
        this.dealPrice = dealPrice;
    }

    public String getStoreThumb() {
        return storeThumb;
    }

    public void setStoreThumb(String storeThumb) {
        this.storeThumb = "https://www.cheapshark.com" + storeThumb;
    }

    public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

}
