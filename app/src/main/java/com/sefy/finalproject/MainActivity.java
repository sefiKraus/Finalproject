package com.sefy.finalproject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.sefy.finalproject.Model.ItemModel;
import com.sefy.finalproject.Model.UserManager;
import com.sefy.finalproject.Model.UserModel;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserManager user_manager = new UserManager();

      user_manager.getUserDB("life0@gmail.com", new UserManager.GetUserCallback() {
          @Override
          public void onComplete(UserModel user) {
              Log.d("---====DEBUG====---",user.getFirstName().toString());
          }

          @Override
          public void onCancel() {
              Log.d("---====DEBUG====---","no such user");
          }
      });




    }
}
