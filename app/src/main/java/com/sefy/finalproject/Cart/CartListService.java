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
        Log.d("TAG"," "+ getInstance().cartItemVector.size());

    }

    public void removeFromCart(CartItem cartItem){
        for(int i = 0 ; i< getInstance().cartItemVector.size() ; i++){
            CartItem tempItem = getInstance().cartItemVector.get(i);
            if(tempItem.getItem().getBrandName().equals(cartItem.getItem().getBrandName())
                    && tempItem.getItem().getName().equals(cartItem.getItem().getName())){
                getInstance().cartItemVector.remove(i);
            }
        }

        Log.d("TAG"," "+ getInstance().cartItemVector.size());
    }
    public Vector<CartItem> getCartItemVector() {
        return cartItemVector;
    }

}
