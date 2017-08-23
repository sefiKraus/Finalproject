package com.sefy.finalproject.Model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

/**
 * Created by Lior on 8/19/2017.
 */

public class UserManager<T> extends CommonManager<UserModel> {

    String dbref="users";




    private boolean addUserDB(UserModel obj){
        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference( dbref);
            myRef.child(obj.getUserId()).setValue(obj);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    private boolean deleteUserDB(String id){

        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference( dbref);
            myRef.child(id).removeValue();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    interface GetUserCallback {
        void onComplete(UserModel user);
        void onCancel();
    }




    public void getUserDB(String stId, final GetUserCallback callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference( dbref);
        myRef.child(stId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserModel user = dataSnapshot.getValue(UserModel.class);
                callback.onComplete(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCancel();
            }
        });
    }





    @Override
    public boolean add(UserModel obj) {

        try {
            list.add(obj);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public  boolean remove(String id) {
        Iterator it = list.iterator();
        UserModel temp;
        while (it.hasNext()) {
            temp = (UserModel) it.next();
            if (temp.getUserId() == id) {
                list.remove(temp);
                deleteUserDB(id);
                return true;
            }

        }


        return false;
    }

    @Override
    public  boolean update(String id, UserModel newobj) {

        if (remove(id)) {
            newobj.setUserId(id);
            if (add(newobj)) {
                addUserDB(newobj);
                return true;
            }
        }

        return false;
    }

    @Override
    public  UserModel read(String id) {
        Iterator it = list.iterator();
        UserModel temp;
        while (it.hasNext()) {
            temp = (UserModel) it.next();
            if (temp.getUserId() == id) {


                return temp;

            }


        }
        return null;
    }
}