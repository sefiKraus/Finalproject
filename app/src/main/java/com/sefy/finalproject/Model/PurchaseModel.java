package com.sefy.finalproject.Model;

import java.util.Date;
import java.util.Vector;

/**
 * Created by sefy1 on 04/08/2017.
 */

public class PurchaseModel {
    private String id;
    private int totalPrice;
    private Date date;
    private String userId;
    private Vector<CartItem> cartList;

    public PurchaseModel() {
    }

    public PurchaseModel(int totalPrice, Date date, String userId, Vector<CartItem> cartList) {
        this.totalPrice = totalPrice;
        this.date = date;
        this.userId = userId;
        this.cartList = cartList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PurchaseModel that = (PurchaseModel) o;

        if (totalPrice != that.totalPrice) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        return cartList != null ? cartList.equals(that.cartList) : that.cartList == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + totalPrice;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (cartList != null ? cartList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PurchaseModel{" +
                "id='" + id + '\'' +
                ", totalPrice=" + totalPrice +
                ", date=" + date +
                ", userId='" + userId + '\'' +
                ", cartList=" + cartList +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Vector<CartItem> getCartList() {
        return cartList;
    }

    public void setCartList(Vector<CartItem> cartList) {
        this.cartList = cartList;
    }
}