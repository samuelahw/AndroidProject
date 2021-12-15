package com.example.androidprojekti2;

public class GameModelClass {

    String name;
    String normalPrice;
    String salePrice;
    String imgUrl;
    String id;

    public GameModelClass(String name, String normalPrice, String salePrice, String imgUrl) {
        this.name = name;
        this.normalPrice = normalPrice;
        this.salePrice = salePrice;
        this.imgUrl = imgUrl;
    }

    public GameModelClass(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNormalPrice(String normalPrice) {
        this.normalPrice = normalPrice + "$";
    }

    public void setSalePrice(String salePrice) {

        if(salePrice.equals("0.00")) this.salePrice = "FREE!";
        else this.salePrice = salePrice + "$";
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNormalPrice() {
        return normalPrice;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getId(){
        return id;
    }
     public void setId(String i){
        id = i;
     }
}
