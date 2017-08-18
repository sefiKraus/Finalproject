package com.sefy.finalproject;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.sefy.finalproject.Brand.BrandListFragment;

public class HomeActivity extends Activity implements BrandListFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BrandListFragment brandListFragment = BrandListFragment.newInstance();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.brand_frag_container,brandListFragment);
        transaction.addToBackStack("");
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction() {

    }
}
