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

        UserModel user = new UserModel("avi1","piggie","meow@howhow.com","fuckmylife123","1");
        UserManager user_manager = new UserManager();
        user_manager.add(user);
        user = user_manager.read("1");
        Log.d("test",user.getFirstName());
        UserModel user2 = new UserModel("avi2","piggie2","meow@howhow.com2","fuckmylife1232","2");
        user_manager.update("1",user2);
         user2 = user_manager.read("1");
        Log.d("test",user2.getFirstName());
        user_manager.remove(user2.getId());
        user2 = user_manager.read("1");
        if(user2==null){
            Log.d("test","Removed !");
        }
        else{
            Log.d("test","Failed to remove!");

        }





    }
}
