package com.sefy.finalproject.Model;

import android.widget.ImageView;

/**
 * Created by sefy1 on 04/08/2017.
 */

public class BrandModel {
    private String name;
    private ImageView image;
    private String description;
    private String userEmail;
    public BrandModel() {
    }

    public BrandModel(String name, ImageView image, String description,String userEmail) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.userEmail = userEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BrandModel that = (BrandModel) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (image != null ? !image.equals(that.image) : that.image != null) return false;
        return description != null ? description.equals(that.description) : that.description == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BrandModel{" +
                "name='" + name + '\'' +
                ", image=" + image +
                ", description='" + description + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
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
}
