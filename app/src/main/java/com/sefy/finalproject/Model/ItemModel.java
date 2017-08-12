package com.sefy.finalproject.Model;

import android.media.Image;

/**
 * Created by sefy1 on 04/08/2017.
 */

public class ItemModel {
    private String id;
    private String name;
    private int price;
    private Image image;
    private String description;
    private int brandId;
    private String brandName;

    public ItemModel() {
    }

    public ItemModel(String id, String name, int price, Image image, String description, int brandId, String brandName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
        this.brandId = brandId;
        this.brandName = brandName;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
