package com.sefy.finalproject;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.sefy.finalproject.Brand.BrandAddFragment;
import com.sefy.finalproject.Brand.BrandListFragment;
import com.sefy.finalproject.Item.ItemListFragment;

public class HomeActivity extends Activity implements
        BrandListFragment.OnBrandListListener ,
        ItemListFragment.OnItemListListener ,
        BrandAddFragment.OnBrandAddListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle userDetails = getIntent().getExtras() ;

        Log.d("TAG","HomeActivity received bundle with " + userDetails.get("userEmail").toString());

        BrandListFragment brandListFragment = BrandListFragment.newInstance();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.brand_frag_container,brandListFragment);
        transaction.addToBackStack("");
        transaction.commit();
    }


    /**
     * handling brand selected
     * @param brandName
     */
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

    /**
     * handling item selected
     * @param itemName
     */
    @Override
    public void onItemSelected(String itemName) {
        Log.d("TAG",itemName);
    }

    /**
     * handling create new brand
     * @param name
     * @param image
     * @param description
     */
    @Override
    public void onBrandAdd(String name, ImageView image, String description) {

    }
/*------------------------------------------------------------------------------------------------*/
    /**
     * handling Action bar
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.home_actionbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.home_actionbar_add_brand:
            {
                Log.d("TAG","Add brand item selected");
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                BrandAddFragment brandAddFragment = BrandAddFragment.newInstance();
                transaction.replace(R.id.brand_frag_container, brandAddFragment);
                transaction.addToBackStack("");
                transaction.commit();
                getActionBar().setDisplayHomeAsUpEnabled(true);
            }
            break;
            case android.R.id.home:{

            }
            break;
            default:{
                return super.onOptionsItemSelected(item);
            }
        }
        return true;
    }

}
