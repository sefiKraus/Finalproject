package com.sefy.finalproject.Model;

/**
 * Created by sefy1 on 25/08/2017.
 */

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Lior on 8/19/2017.
 */

public class UserManager extends CommonManager<UserModel> {

    String dbref="users";

    public UserManager() {
        // getAllUsersAndObserve();
    }


    public String emailToKey(String email) {
        return email.replace(".","%252E");
    }


    public boolean addUserDB(UserModel obj){


        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference( dbref);
            myRef.child(emailToKey(obj.getEmail())).setValue(obj);





            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean deleteUserDB(String email){

        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference( dbref);
            myRef.child(emailToKey(email)).removeValue();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public interface GetUserCallback {
        void onComplete(UserModel user);
        void onCancel();
    }




    public void getUserDB(String email, final GetUserCallback callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference( dbref);

        Log.d("--==DEBUG==--","Search for: "+emailToKey(email));
        myRef.child(emailToKey(email)).addListenerForSingleValueEvent(new ValueEventListener() {
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
            addUserDB(obj);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public  boolean remove(String email) {
        Iterator it = list.iterator();
        UserModel temp;
        while (it.hasNext()) {
            temp = (UserModel) it.next();
            if (temp.getEmail() == emailToKey(email)) {
                //   list.remove(temp);
                deleteUserDB(email);
                return true;
            }

        }


        return false;
    }

    @Override
    public  boolean update(String email, UserModel newobj) {

        if (remove(email)) {
            newobj.setEmail(emailToKey(email));
            if (add(newobj)) {
                addUserDB(newobj);
                return true;
            }
        }

        return false;
    }

    @Override
    public  UserModel read(String email) {
        Iterator it = list.iterator();
        UserModel temp;
        while (it.hasNext()) {
            temp = (UserModel) it.next();
            if (temp.getEmail() == emailToKey(email)) {


                return temp;

            }


        }
        return null;
    }

/*
    public  UserModel readByEmail(String email) {
        Iterator it = list.iterator();
        UserModel temp;
        while (it.hasNext()) {
            temp = (UserModel) it.next();
            if (temp.getEmail() == email) {
                return temp;
            }
        }
        return null;
    }
*/

/*
     void getAllUsersAndObserve() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(dbref);
        ValueEventListener listener = myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List< UserModel> list1 = new LinkedList< UserModel>();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    UserModel user = snap.getValue( UserModel.class);
                    list1.add(user);
                }
                list.clear();
                list.addAll(list1);
                print();
              //  callback.onComplete(list1);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
              Log.d("ERROR",databaseError.getMessage().toString());
            }
        });
    }
*/







}