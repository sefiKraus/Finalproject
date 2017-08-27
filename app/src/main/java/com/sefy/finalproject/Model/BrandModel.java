package com.sefy.finalproject.Model;

import android.widget.ImageView;

/**
 * Created by sefy1 on 04/08/2017.
 */

public class BrandModel {
    private String name;
   // private ImageView image;
    private String description;
    private String userEmail;
    private String image;
    public BrandModel() {
    }

    public BrandModel(String name, String image, String description,String userEmail) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.userEmail = userEmail;
    }



    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
