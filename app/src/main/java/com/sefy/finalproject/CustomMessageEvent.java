package com.sefy.finalproject;

import android.util.Log;

import com.sefy.finalproject.Model.CartItem;

/**
 * Created by sefy1 on 25/08/2017.
 */

public class CustomMessageEvent {

    private CartItem cartItem;


    public CartItem getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItem cartItem) {

        this.cartItem = cartItem;
    }
}
