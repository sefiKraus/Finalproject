package com.sefy.finalproject.EventBus;

import android.util.Log;

import com.sefy.finalproject.Model.CartItem;

import java.util.Vector;

/**
 * Created by sefy1 on 25/08/2017.
 */

public class CartEvent {

    private CartItem cartItem;

    public CartItem getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }
}
