package com.sefy.finalproject;

import android.app.Activity;
import android.os.Bundle;

import com.sefy.finalproject.Model.FireBaseDB;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FireBaseDB db = new FireBaseDB();


    }
}
