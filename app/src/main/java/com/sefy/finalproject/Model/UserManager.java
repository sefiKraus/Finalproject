package com.sefy.finalproject.Model;

import android.util.Log;

import java.util.Iterator;

/**
 * Created by Lior on 8/19/2017.
 */

public class UserManager extends CommonManager<UserModel> {


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
            if (temp.getId() == id) {
                list.remove(temp);
                return true;
            }

        }


        return false;
    }

    @Override
    public  boolean update(String id, UserModel newobj) {

        if (remove(id)) {
            newobj.setId(id);
            if (add(newobj)) {
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
            if (temp.getId() == id) {


                return temp;

            }


        }
        return null;
    }
}