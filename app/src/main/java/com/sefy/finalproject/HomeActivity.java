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
import com.sefy.finalproject.Cart.CartListService;
import com.sefy.finalproject.Item.ItemAddFragment;
import com.sefy.finalproject.Item.ItemEditFragment;
import com.sefy.finalproject.Item.ItemListFragment;
import com.sefy.finalproject.Model.ItemModel;
import com.sefy.finalproject.User.UserActivity;

public class HomeActivity extends Activity implements
        BrandListFragment.OnBrandListListener ,
        ItemListFragment.OnItemListListener ,
        ItemAddFragment.OnItemLAddListener,
        ItemEditFragment.OnItemEditListener,
        BrandAddFragment.OnBrandAddListener{
    private Bundle userDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.userDetails = getIntent().getExtras() ;

        BrandListFragment brandListFragment = BrandListFragment.newInstance(this.userDetails.get("userEmail").toString());
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.brand_frag_container,brandListFragment);
        transaction.addToBackStack("");
        transaction.commit();

        startService(new Intent(this, CartListService.class));
    }


    /**
     * handling brand selected
     * @param brandName
     */
    @Override
    public void onBrandSelected(String brandName , String userEmail) {
        /**
         * swap views between BrandList and ItemList
         */

        ItemListFragment itemListFragment = ItemListFragment.newInstance(brandName , userEmail);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.brand_frag_container,itemListFragment);
        transaction.addToBackStack("");
        transaction.commit();
        getActionBar().setDisplayHomeAsUpEnabled(true);
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


    /**
     * handling item selected
     * @param itemName
     */
    @Override
    public void onItemSelected(String itemName) {
        Log.d("TAG","Navigate to item details fragment of item: "+itemName);
    }

    /**
     * Navigate to item edit fragment
     * @param itemName
     * @param itemDescription
     * @param brandName
     * @param userEmail
     */
    @Override
    public void onItemEditRequest(String itemName, String itemDescription, int price, String brandName, String userEmail) {
        ItemEditFragment fragment = ItemEditFragment.newInstance(itemName,itemDescription,price,brandName,userEmail);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.brand_frag_container, fragment);
        transaction.addToBackStack("");
        transaction.commit();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        this.menu.findItem(R.id.home_actionbar_add).setVisible(false);

    }

    /**
     * Create new item
     * this method receives send back item from ItemAddFragment and will try to
     * save it in database
     * @param item
     */
    @Override
    public void onAddItem(ItemModel item) {
        Log.d("TAG", "onAddItem " + item.toString());
    }


    /**
     *
     * @param name
     * @param price
     * @param description
     * @param brand
     * @param userEmail
     */
    @Override
    public void onItemEdit(String name, int price, String description, String brand, String userEmail) {
        ItemModel itemModel = new ItemModel(name,price,null,description,brand,userEmail);
        Log.d("TAG",itemModel.toString());
    }

    @Override
    public void onItemRemove() {
        Log.d("TAG","onItemRemove");
    }
/*------------------------------------------------------------------------------------------------*/
    /**
     * handling Action bar
     */
    private Menu menu;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.menu = menu;
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
                /**
                 * getting back from BrandAddFragment or ItemListFragment to BrandListFragment
                 */
                if(this.currentFragment  instanceof BrandAddFragment || this.currentFragment instanceof ItemListFragment){
                    toDisplay = BrandListFragment.newInstance(userDetails.get("userEmail").toString());
                    transaction.replace(R.id.brand_frag_container, toDisplay);
                    transaction.addToBackStack("");
                    transaction.commit();
                }else if(this.currentFragment instanceof ItemAddFragment){
                    /**
                     * getting back from ItemAddFragment to ItemListFragment
                     */
                    toDisplay = ItemListFragment.newInstance(((ItemAddFragment) this.currentFragment).getBrandName()
                    ,userDetails.get("userEmail").toString()
                    );
                    transaction.replace(R.id.brand_frag_container, toDisplay);
                    transaction.addToBackStack("");
                    transaction.commit();
                    getActionBar().setDisplayHomeAsUpEnabled(true)  ;
                }
                else if(this.currentFragment instanceof ItemEditFragment){
                    /**
                     * getting back from ItemEditFragment to ItemListFragment
                     */
                    toDisplay = ItemListFragment.newInstance(((ItemEditFragment) this.currentFragment).getBrandName()
                            ,userDetails.get("userEmail").toString()
                    );
                    transaction.replace(R.id.brand_frag_container, toDisplay);
                    transaction.addToBackStack("");
                    transaction.commit();
                    getActionBar().setDisplayHomeAsUpEnabled(true);
                    this.menu.findItem(R.id.home_actionbar_add).setVisible(true);

                }
            }
            break;
            case R.id.home_actionbar_add:{
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if(this.currentFragment instanceof BrandListFragment){
                    toDisplay = BrandAddFragment.newInstance(this.userDetails.get("userEmail").toString());
                    transaction.replace(R.id.brand_frag_container, toDisplay);
                    transaction.addToBackStack("");
                    transaction.commit();
                    getActionBar().setDisplayHomeAsUpEnabled(true);
                }else if(this.currentFragment instanceof ItemListFragment){
                    String itemListBrand = ((ItemListFragment) this.currentFragment).getBrandName();
                    toDisplay = ItemAddFragment.newInstance(itemListBrand,this.userDetails.get("userEmail").toString());
                    transaction.replace(R.id.brand_frag_container, toDisplay);
                    transaction.addToBackStack("");
                    transaction.commit();
                    getActionBar().setDisplayHomeAsUpEnabled(true);
                }
            }
            break;
            case R.id.home_actionbar_cart:{
                Log.d("TAG","Create new CartActivity intent");
            }
            break;
            case R.id.home_actionbar_user_details:{
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
