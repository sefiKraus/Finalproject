package com.sefy.finalproject.Events;

import com.sefy.finalproject.Model.CartItem;

/**
 * Created by sefy1 on 26/08/2017.
 */

public class CartAddEvent {

    private CartItem cartItem;

    public CartItem getCartItemToAdd() {
        return cartItem;
    }

    public void setCartItemToAdd(CartItem cartItem) {
        this.cartItem = cartItem;
    }
}
