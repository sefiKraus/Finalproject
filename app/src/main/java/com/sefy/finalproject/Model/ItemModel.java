package com.sefy.finalproject.Model;

import android.media.Image;
import android.widget.ImageView;

/**
 * Created by sefy1 on 04/08/2017.
 */

public class ItemModel {
    private String id;
    private String name;
    private int price;
    private ImageView image;
    private String description;
    private String brandId;
    private String brandName;
    private String userEmail;
    public ItemModel() {
    }

    public ItemModel(String name, int price, ImageView image, String description, String brandName,String userEmail) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
        this.brandName = brandName;
        this.userEmail = userEmail;
    }

    @Override
    public String toString() {
        return "ItemModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", image=" + image +
                ", description='" + description + '\'' +
                ", brandId='" + brandId + '\'' +
                ", brandName='" + brandName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
