package com.sefy.finalproject.Model;

import android.media.Image;

/**
 * Created by sefy1 on 04/08/2017.
 */

public class BrandModel {
    private int id;
    private String name;
    private Image image;
    private String description;

    public BrandModel() {
    }

    public BrandModel(int id, String name, Image image, String description) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
