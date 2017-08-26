package com.sefy.finalproject.Model;

/**
 * Created by sefy1 on 25/08/2017.
 */

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Lior on 8/19/2017.
 */

public class ItemManager extends CommonManager<ItemModel> {

    String dbref="items";

    public ItemManager() {
        // getAllUsersAndObserve();
    }

/*
    public String nameToKey(String email) {
        return email.replace(".","%252E");
    }
*/

    public boolean addItemDB(ItemModel obj){


        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference( dbref);
            if (obj.getId()==null || obj.getId()==""){
                String uid = myRef.push().getKey();
                obj.setId(uid);
            }
            myRef.child(obj.getId()).setValue(obj);





            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean deleteItemDB(String id){

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


    public interface GetItemCallback {
        void onComplete(ItemModel item);
        void onCancel();
    }




    public void getItemDB(String id, final GetItemCallback callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference( dbref);

        Log.d("--==DEBUG==--","Search for: "+id);
        myRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ItemModel item = dataSnapshot.getValue(ItemModel.class);
                callback.onComplete(item);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onCancel();
            }
        });
    }





    @Override
    public boolean add(ItemModel obj) {

        try {
            //list.add(obj);
            addItemDB(obj);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public  boolean remove(String id) {
        Iterator it = list.iterator();
        ItemModel temp;
        while (it.hasNext()) {
            temp = (ItemModel) it.next();
            if (temp.getId() == id) {
                //   list.remove(temp);
                deleteItemDB(id);
                return true;
            }

        }


        return false;
    }

    @Override
    public boolean update(String id, ItemModel newobj) {

        if (remove(id)) {
            newobj.setId(id);
            if (add(newobj)) {
                addItemDB(newobj);
                return true;
            }
        }

        return false;
    }

    @Override
    public  ItemModel read(String id) {
        Iterator it = list.iterator();
        ItemModel temp;
        while (it.hasNext()) {
            temp = (ItemModel) it.next();
            if (temp.getId() == id) {


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






    public interface GetItemListCallback {
        void onComplete(List<ItemModel> list);
        void onCancel();
    }








     public void getAllItemsAndObserve( final GetItemListCallback callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(dbref);
        ValueEventListener listener = myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List< ItemModel> list1 = new LinkedList<ItemModel>();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    ItemModel item = snap.getValue( ItemModel.class);
                    list1.add(item);
                }
              //  list.clear();
               // list.addAll(list1);
              //  print();
                callback.onComplete(list1);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
              Log.d("ERROR",databaseError.getMessage().toString());
            }
        });
    }

    public void getAllItemsByBrand( String brandName,final GetItemListCallback callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(dbref);

        Query results = myRef.orderByChild("brandName").equalTo(brandName);
        ValueEventListener listener =results.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List< ItemModel> list1 = new LinkedList<ItemModel>();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    ItemModel item = snap.getValue( ItemModel.class);
                    list1.add(item);
                }
                //  list.clear();
                // list.addAll(list1);
                //  print();
                callback.onComplete(list1);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("ERROR",databaseError.getMessage().toString());
            }
        });


















    }




}