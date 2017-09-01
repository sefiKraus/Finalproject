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

public class BrandManager extends CommonManager<BrandModel> {

    String dbref="brands";

    public BrandManager() {
        // getAllUsersAndObserve();
    }


    public String nameToKey(String email) {
        return email.replace(".","%252E");
    }


    public boolean addBrandDB(BrandModel obj){


        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference( dbref);
            myRef.child(nameToKey(obj.getName())).setValue(obj);





            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean deleteBrandDB(String email){

        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference( dbref);
            myRef.child(nameToKey(email)).removeValue();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public interface GetBrandCallback {
        void onComplete(BrandModel brand);
        void onCancel();
    }




    public void getBrandDB(String name, final GetBrandCallback callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference( dbref);

        Log.d("--==DEBUG==--","Search for: "+nameToKey(name));
        myRef.child(nameToKey(name)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                BrandModel brand = dataSnapshot.getValue(BrandModel.class);
                callback.onComplete(brand);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCancel();
            }
        });
    }





    @Override
    public boolean add(BrandModel obj) {

        try {
            list.add(obj);
            addBrandDB(obj);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public  boolean remove(String name) {
        Iterator it = list.iterator();
        BrandModel temp;
        while (it.hasNext()) {
            temp = (BrandModel) it.next();
            if (temp.getName() == nameToKey(name)) {
                //   list.remove(temp);
                deleteBrandDB(name);
                return true;
            }

        }


        return false;
    }

    @Override
    public boolean update(String name, BrandModel newobj) {

        if (remove(name)) {
            newobj.setName(nameToKey(name));
            if (add(newobj)) {
                addBrandDB(newobj);
                return true;
            }
        }

        return false;
    }

    @Override
    public  BrandModel read(String name) {
        Iterator it = list.iterator();
        BrandModel temp;
        while (it.hasNext()) {
            temp = (BrandModel) it.next();
            if (temp.getName() == nameToKey(name)) {


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






    public interface GetBrandListCallback {
        void onComplete(List< BrandModel> list);
        void onCancel();
    }

     public void getAllBrandsAndObserve( final GetBrandListCallback callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(dbref);
        ValueEventListener listener = myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List< BrandModel> list1 = new LinkedList<BrandModel>();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    BrandModel brand = snap.getValue( BrandModel.class);
                    list1.add(brand);
                }

                callback.onComplete(list1);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
              Log.d("ERROR",databaseError.getMessage().toString());
            }
        });
    }








}