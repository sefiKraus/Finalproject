package com.sefy.finalproject.Cart;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.sefy.finalproject.CustomMessageEvent;
import com.sefy.finalproject.Events.CartAddEvent;
import com.sefy.finalproject.Events.CartRemoveEvent;
import com.sefy.finalproject.Model.CartItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Vector;
/**
 * Created by sefy1 on 25/08/2017.
 */

public class CartListService {

    private Vector<CartItem> cartItemVector;
    private static final CartListService instance = new CartListService();

    private CartListService(){
        cartItemVector = new Vector<>();
    }

    public static CartListService getInstance() {
        return instance;
    }

    public void addToCart(CartItem cartItem){
        getInstance().cartItemVector.add(cartItem);
    }

    public void removeFromCart(CartItem cartItem){
        getInstance().cartItemVector.remove(cartItem);
    }
    public Vector<CartItem> getCartItemVector() {
        return cartItemVector;
    }

}
