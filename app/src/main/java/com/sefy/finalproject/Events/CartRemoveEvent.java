package com.sefy.finalproject.Events;

import com.sefy.finalproject.Model.CartItem;

/**
 * Created by sefy1 on 26/08/2017.
 */

public class CartRemoveEvent {


    private CartItem cartItem;

    public CartItem getCartItemToRemove() {
        return cartItem;
    }

    public void setCartItemToRemove(CartItem cartItem) {
        this.cartItem = cartItem;
    }
}
