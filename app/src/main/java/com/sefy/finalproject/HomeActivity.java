package com.sefy.finalproject;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import com.sefy.finalproject.Brand.BrandListFragment;
import com.sefy.finalproject.Item.ItemListFragment;

public class HomeActivity extends Activity implements BrandListFragment.OnBrandListListener , ItemListFragment.OnItemListListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle userDetails = getIntent().getExtras() ;

        Log.d("TAG","HomeActivity received bundle with " + userDetails.get("userFirstName").toString());

        BrandListFragment brandListFragment = BrandListFragment.newInstance();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.brand_frag_container,brandListFragment);
        transaction.addToBackStack("");
        transaction.commit();
    }


    @Override
    public void onBrandSelected(String brandName) {
        /**
         * swap views between BrandList and ItemList
         */

        ItemListFragment itemListFragment = ItemListFragment.newInstance(brandName);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.brand_frag_container,itemListFragment);
        transaction.addToBackStack("");
        transaction.commit();
    }

    @Override
    public void onItemSelected(String itemName) {
        Log.d("TAG",itemName);
    }
}
