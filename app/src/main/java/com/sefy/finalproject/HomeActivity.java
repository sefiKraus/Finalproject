package com.sefy.finalproject;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.sefy.finalproject.Brand.BrandAddFragment;
import com.sefy.finalproject.Brand.BrandListFragment;
import com.sefy.finalproject.Item.ItemListFragment;
import com.sefy.finalproject.User.UserActivity;

public class HomeActivity extends Activity implements
        BrandListFragment.OnBrandListListener ,
        ItemListFragment.OnItemListListener ,
        BrandAddFragment.OnBrandAddListener{
    private Bundle userDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.userDetails = getIntent().getExtras() ;

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
        getActionBar().setDisplayHomeAsUpEnabled(true);
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

   private Fragment currentFragment;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.currentFragment = getFragmentManager().findFragmentById(R.id.brand_frag_container);
        int itemId = item.getItemId();
        Fragment toDisplay;
        switch (itemId){
            case android.R.id.home:{
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if(this.currentFragment instanceof BrandAddFragment || this.currentFragment instanceof ItemListFragment){
                    toDisplay = BrandListFragment.newInstance();
                    transaction.replace(R.id.brand_frag_container, toDisplay);
                    transaction.addToBackStack("");
                    transaction.commit();

                }
            }
            break;
            case R.id.home_actionbar_add:{
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if(this.currentFragment instanceof BrandListFragment){
                    toDisplay = BrandAddFragment.newInstance();
                    transaction.replace(R.id.brand_frag_container, toDisplay);
                    transaction.addToBackStack("");
                    transaction.commit();
                    getActionBar().setDisplayHomeAsUpEnabled(true);
                }else if(this.currentFragment instanceof ItemListFragment){
                    Log.d("TAG","Change to Add item fragment");
                }
            }
            break;
            case R.id.home_actionbar_cart:{
                Log.d("TAG","Create new CartActivity intent");
            }
            break;
            case R.id.home_actionbar_user_details:{
                Log.d("TAG","Create new UserActivity intent");
                Intent userActivity =  new Intent(getApplicationContext(), UserActivity.class);
                userActivity.putExtras(this.userDetails);
                startActivity(userActivity);
            }
            break;
            default:{
              return super.onOptionsItemSelected(item);
            }
        }
        return true;

    }

}
