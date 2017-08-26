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

public class CartActivity extends Activity {

    Vector<CartItem> cartItemVector;
    CartListService cartListService;
    boolean isBound = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        this.cartItemVector = new Vector<>();

    }

    @Override
    protected void onStart() {
        super.onStart();
        this.cartItemVector = CartListService.getInstance().getCartItemVector();
        if(this.cartItemVector == null){
            Log.d("TAG","Vector size: 0");
        }
        else{
            Log.d("TAG","Vector size: "+ this.cartItemVector.size());
        }
    }

}
