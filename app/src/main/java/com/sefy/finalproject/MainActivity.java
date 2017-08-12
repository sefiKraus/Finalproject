package com.sefy.finalproject;

import android.app.Activity;
import android.os.Bundle;

import com.sefy.finalproject.Model.FireBase;
import com.sefy.finalproject.Model.UserModel;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FireBase<UserModel> users = FireBase.getInstance();
        UserModel user = new UserModel("avi","piggie","meow@howhow.com","fuckmylife123","1");
        users.Create("Users","1",user);

    }
}
