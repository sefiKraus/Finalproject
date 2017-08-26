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
import com.sefy.finalproject.Cart.CartListService.MyLocalBinder;
/**
 * Created by sefy1 on 25/08/2017.
 */

public class CartListService extends Service {

    private Vector<CartItem> cartItemVector;
    private final IBinder myBinder = new MyLocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }


    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
         super.onStartCommand(intent, flags, startId);
        this.cartItemVector = new Vector<>();
        EventBus.getDefault().register(this);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(CartAddEvent event){
        CartItem item = event.getCartItemToAdd();
        this.cartItemVector.add(item);
        Log.d("TAG","vector size: "+ this.cartItemVector.size());
    }


    @Subscribe
    public void onEvent(CartRemoveEvent event){
        CartItem item = event.getCartItemToRemove();
        this.cartItemVector.remove(item);
        Log.d("TAG","vector size: "+ this.cartItemVector.size());

    }

    public void printSize(){
        Log.d("TAG","something");
    }

    /**
     * binding client to service
     */
    public class MyLocalBinder extends Binder{
        CartListService getService(){
            return CartListService.this;
        }
    }
}
