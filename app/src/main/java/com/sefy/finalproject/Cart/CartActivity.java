package com.sefy.finalproject.Cart;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.sefy.finalproject.CustomMessageEvent;
import com.sefy.finalproject.EventBus.CartEvent;
import com.sefy.finalproject.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class CartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
    }


}
