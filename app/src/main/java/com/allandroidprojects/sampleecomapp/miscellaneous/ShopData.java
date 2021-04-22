package com.allandroidprojects.sampleecomapp.miscellaneous;

public class ShopData {
    int shopImageId;
    String shopName;
    String shopAddress;
    float ratingBar;

    public ShopData(int shopImageId, String shopName, String shopAddress, float ratingBar) {
        this.shopImageId = shopImageId;
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.ratingBar = ratingBar;
    }

    public int getShopImageId() {
        return shopImageId;
    }

    public String getShopName() {
        return shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public float getRatingBar() {
        return ratingBar;
    }

    public void setShopImageId(int shopImageId) {
        this.shopImageId = shopImageId;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public void setRatingBar(float ratingBar) {
        this.ratingBar = ratingBar;
    }
}
