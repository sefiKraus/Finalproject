package com.sefy.finalproject.Model;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by sefy1 on 11/08/2017.
 */

public class FireBaseDB {
    private DatabaseReference mDatabase;

    public FireBaseDB() {
        Log.d("TAG","here");
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
        this.mDatabase.child("users").setValue("firstname");

    }
}
