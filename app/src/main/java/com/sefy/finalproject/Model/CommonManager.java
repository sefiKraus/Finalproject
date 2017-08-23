package com.sefy.finalproject.Model;

import java.util.ArrayList;

/**
 * Created by Lior on 8/18/2017.
 */

abstract class CommonManager<T> {


    ArrayList<T> list = new ArrayList<T>();


    public abstract boolean add(T obj);

    public abstract boolean remove(String id);

    public abstract boolean update(String id, T newobj);

    public abstract T read(String id);


}

