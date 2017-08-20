package com.sefy.finalproject.User;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.sefy.finalproject.Model.UserModel;
import com.sefy.finalproject.R;

public class UserActivity extends Activity {
    private Bundle userDetails;
    UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        this.userDetails = getIntent().getExtras();
        this.user = new UserModel(this.userDetails.getString("userFirstName"),
                                  this.userDetails.getString("userLastName"),
                                  this.userDetails.getString("userEmail"),
                                  this.userDetails.getString("userPassword"));

        Log.d("TAG","User Activity received user details: "+ this.user.toString());
    }
}
