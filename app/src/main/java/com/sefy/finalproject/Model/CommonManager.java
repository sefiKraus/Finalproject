package com.sefy.finalproject.Model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Lior on 8/18/2017.
 */

abstract class CommonManager<T> {


    ArrayList<T> list = new ArrayList<T>();


    public abstract boolean add(T obj);

    public abstract boolean remove(String id);

    public abstract boolean update(String id, T newobj);

    public abstract T read(String id);


    public void print() {
        String temp;
        Iterator it = list.iterator();
        while(it.hasNext()){


            temp =it.next().toString();
            Log.d("print",temp);


        }
    }


}
