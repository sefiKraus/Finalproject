package com.sefy.finalproject.Model;

import java.util.Date;
import java.util.Vector;

/**
 * Created by sefy1 on 04/08/2017.
 */

public class PurchaseModel {
    private int id;
    private int totalPrice;
    private Date date;
    private int userId;
    private Vector<CartItem> cartList;

    public PurchaseModel() {
    }

    public PurchaseModel(int id, int totalPrice, Date date, int userId, Vector<CartItem> cartList) {
        this.id = id;
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

        if (id != that.id) return false;
        if (totalPrice != that.totalPrice) return false;
        if (userId != that.userId) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        return cartList != null ? cartList.equals(that.cartList) : that.cartList == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + totalPrice;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + userId;
        result = 31 * result + (cartList != null ? cartList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PurchaseModel{" +
                "id=" + id +
                ", totalPrice=" + totalPrice +
                ", date=" + date +
                ", userId=" + userId +
                ", cartList=" + cartList +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Vector<CartItem> getCartList() {
        return cartList;
    }

    public void setCartList(Vector<CartItem> cartList) {
        this.cartList = cartList;
    }
}