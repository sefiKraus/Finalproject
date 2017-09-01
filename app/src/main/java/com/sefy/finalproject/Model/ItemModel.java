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
    private String image;
    private String description;
    private String brandName;
    private String userEmail;
    private boolean isClicked;

    public ItemModel() {
    }

    public ItemModel(String name, int price, String image, String description, String brandName,String userEmail) {

        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
        this.brandName = brandName;
        this.userEmail = userEmail;
        this.isClicked = false;
    }

    @Override
    public String toString() {
        return "ItemModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", brandName='" + brandName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", isClicked=" + isClicked +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
