package com.sefy.finalproject.Cart;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sefy.finalproject.EventBus.CartEvent;
import com.sefy.finalproject.Model.CartItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Vector;

/**
 * Created by sefy1 on 25/08/2017.
 */

public class CartListService extends Service {

    private Vector<CartItem> cartItemVector;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
         super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void addToCart(CartItem cartItem){
        this.cartItemVector.add(cartItem);
        Log.d("TAG","added " + cartItem.toString());
    }
}
