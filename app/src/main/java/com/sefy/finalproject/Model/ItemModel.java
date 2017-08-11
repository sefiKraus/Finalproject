package com.sefy.finalproject.Model;

import android.media.Image;

/**
 * Created by sefy1 on 04/08/2017.
 */

public class ItemModel {
    private int id;
    private String name;
    private int price;
    private Image image;
    private String description;
    private int brandId;
    private String brandName;

    public ItemModel() {
    }

    public ItemModel(int id, String name, int price, Image image, String description, int brandId, String brandName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
        this.brandId = brandId;
        this.brandName = brandName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemModel itemModel = (ItemModel) o;

        if (id != itemModel.id) return false;
        if (price != itemModel.price) return false;
        if (brandId != itemModel.brandId) return false;
        if (name != null ? !name.equals(itemModel.name) : itemModel.name != null) return false;
        if (image != null ? !image.equals(itemModel.image) : itemModel.image != null) return false;
        if (description != null ? !description.equals(itemModel.description) : itemModel.description != null)
            return false;
        return brandName != null ? brandName.equals(itemModel.brandName) : itemModel.brandName == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + price;
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + brandId;
        result = 31 * result + (brandName != null ? brandName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ItemModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", image=" + image +
                ", description='" + description + '\'' +
                ", brandId=" + brandId +
                ", brandName='" + brandName + '\'' +
                '}';
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
