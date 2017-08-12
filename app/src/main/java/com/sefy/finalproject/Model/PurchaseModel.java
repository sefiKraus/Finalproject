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

    public PurchaseModel(String id, int totalPrice, Date date, String userId, Vector<CartItem> cartList) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.date = date;
        this.userId = userId;
        this.cartList = cartList;
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


    public Vector<CartItem> getCartList() {
        return cartList;
    }

    public void setCartList(Vector<CartItem> cartList) {
        this.cartList = cartList;
    }
}