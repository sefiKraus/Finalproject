package com.sefy.finalproject.Model;


import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by Lior on 8/12/2017.
 */

public class FireBase<T> {


    private static FireBase ourInstance;
    private FirebaseDatabase _db;
    private DatabaseReference _ref;
    ArrayList<T> list = new ArrayList<T>();



    public static FireBase getInstance()
    {
        if(ourInstance == null)
            ourInstance= new FireBase();
        return ourInstance;
    }

    private FireBase() {

        _db = FirebaseDatabase.getInstance();
        _ref = _db.getReference();
        _ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable snapshotIterator = dataSnapshot.getChildren();
                Iterator iterator = snapshotIterator.iterator();
               while((iterator.hasNext())){

                   T temp = (T)iterator.next();
                  // Log.d("Debug", temp.toString() );
                    list.add(temp);

                }
               printList();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void Create(String _node, String _id , T _newObj ){


        try {
            _ref = FirebaseDatabase.getInstance().getReference(_node);
            if (_id == "")
                _id = _ref.push().getKey();
            _ref.child(_id).setValue(_newObj);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void Read(){

        return ;

    }

    public void Update(){

        return ;

    }

    public void Delete(){

        return ;

    }


    public void printList(){
       Iterator it = list.iterator();
        while((it.hasNext())){

            T temp = (T)it.next();

            Log.d("Debug",temp.toString());
        }
    }



}
