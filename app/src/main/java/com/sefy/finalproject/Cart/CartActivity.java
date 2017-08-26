package com.sefy.finalproject.Cart;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.sefy.finalproject.Model.CartItem;
import com.sefy.finalproject.R;

import java.util.Vector;
import com.sefy.finalproject.Cart.CartListService.MyLocalBinder;

public class CartActivity extends Activity {

    Vector<CartItem> cartItemVector;
    CartListService cartListService;
    boolean isBound = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        this.cartItemVector = new Vector<>();

        Intent intent = new Intent(this,CartListService.class);
        bindService(intent, myConnection , Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.cartListService.printSize();
        Log.d("TAG","onStart");
    }

    private ServiceConnection myConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyLocalBinder binder = (MyLocalBinder) service;
            cartListService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };
}
