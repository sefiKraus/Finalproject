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

import com.sefy.finalproject.Auth.AuthActivity;
import com.sefy.finalproject.Brand.BrandAddFragment;
import com.sefy.finalproject.Brand.BrandListFragment;
import com.sefy.finalproject.Cart.CartActivity;
import com.sefy.finalproject.Cart.CartListService;
import com.sefy.finalproject.Item.ItemAddFragment;
import com.sefy.finalproject.Item.ItemDetailsFragment;
import com.sefy.finalproject.Item.ItemEditFragment;
import com.sefy.finalproject.Item.ItemListFragment;
import com.sefy.finalproject.Model.BrandModel;
import com.sefy.finalproject.Model.ItemModel;
import com.sefy.finalproject.Model.UserManager;
import com.sefy.finalproject.Model.UserModel;
import com.sefy.finalproject.User.UserActivity;

public class HomeActivity extends Activity implements
        BrandListFragment.OnBrandListListener ,
        ItemListFragment.OnItemListListener ,
        ItemAddFragment.OnItemLAddListener,
        ItemEditFragment.OnItemEditListener,
        BrandAddFragment.OnBrandAddListener{
    private UserManager userManager;
    private Bundle userDetails;
    private UserModel currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        userManager = new UserManager();
        this.userDetails = getIntent().getExtras() ;
        userManager.getUserDB(this.userDetails.get("userEmail").toString(), new UserManager.GetUserCallback() {
            @Override
            public void onComplete(UserModel user) {
                currentUser = user;
                BrandListFragment brandListFragment = BrandListFragment.newInstance(currentUser.getEmail());
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.brand_frag_container,brandListFragment);
                transaction.addToBackStack("");
                transaction.commit();
            }

            @Override
            public void onCancel() {

            }
        });

        CartListService service = CartListService.getInstance();
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
     * Changing view from item list to item view
     * @param itemModel
     */
    @Override
    public void onItemSelected(ItemModel itemModel) {
        ItemDetailsFragment fragment = ItemDetailsFragment.newInstance(itemModel);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.brand_frag_container, fragment);
        transaction.addToBackStack("");
        transaction.commit();
        this.menu.findItem(R.id.home_actionbar_add).setVisible(false);
    }

    /**
     * Changes view to edit item
     * @param item
     * @param brandName
     * @param userEmail
     */
    @Override
    public void onItemEditRequest(ItemModel item, String brandName, String userEmail) {
        ItemEditFragment fragment = ItemEditFragment.newInstance(item,brandName,userEmail);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.brand_frag_container, fragment);
        transaction.addToBackStack("");
        transaction.commit();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        this.menu.findItem(R.id.home_actionbar_add).setVisible(false);

    }


    /**
     * Changes view from edit item to item list after deleting the item
     * @param brandName
     */
    @Override
    public void onItemRemove(String brandName) {
        ItemListFragment itemListFragment = ItemListFragment.newInstance(brandName , currentUser.getEmail());
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.brand_frag_container,itemListFragment);
        transaction.addToBackStack("");
        transaction.commit();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        this.menu.findItem(R.id.home_actionbar_add).setVisible(true);

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
                    toDisplay = BrandListFragment.newInstance(currentUser.getEmail());
                    transaction.replace(R.id.brand_frag_container, toDisplay);
                    transaction.addToBackStack("");
                    transaction.commit();
                }else if(this.currentFragment instanceof ItemAddFragment){
                    /**
                     * getting back from ItemAddFragment to ItemListFragment
                     */
                    toDisplay = ItemListFragment.newInstance(((ItemAddFragment) this.currentFragment).getBrandName()
                    ,currentUser.getEmail()
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
                            ,currentUser.getEmail()
                    );
                    transaction.replace(R.id.brand_frag_container, toDisplay);
                    transaction.addToBackStack("");
                    transaction.commit();
                    getActionBar().setDisplayHomeAsUpEnabled(true);
                    this.menu.findItem(R.id.home_actionbar_add).setVisible(true);

                }
                /**
                 * getting back from ItemDetailsFragment to ItemListFragment
                 */
                else if(this.currentFragment instanceof ItemDetailsFragment){
                    toDisplay = ItemListFragment.newInstance(((ItemDetailsFragment)this.currentFragment).getBrandName()
                            ,currentUser.getEmail());
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
                /**
                 * Adding new brand
                 */
                if(this.currentFragment instanceof BrandListFragment){
                    toDisplay = BrandAddFragment.newInstance(currentUser.getEmail());
                    transaction.replace(R.id.brand_frag_container, toDisplay);
                    transaction.addToBackStack("");
                    transaction.commit();
                    getActionBar().setDisplayHomeAsUpEnabled(true);

                }else if(this.currentFragment instanceof ItemListFragment){
                    /**
                     * Adding new item
                     */
                    String itemListBrand = ((ItemListFragment) this.currentFragment).getBrandName();
                    toDisplay = ItemAddFragment.newInstance(itemListBrand,currentUser.getEmail());
                    transaction.replace(R.id.brand_frag_container, toDisplay);
                    transaction.addToBackStack("");
                    transaction.commit();
                    getActionBar().setDisplayHomeAsUpEnabled(true);
                }
            }
            break;
            case R.id.home_actionbar_cart:{
                /**
                 * Go to Shopping cart
                 */
                Intent cartActivity =  new Intent(getApplicationContext(), CartActivity.class);
                cartActivity.putExtras(this.userDetails);
                startActivity(cartActivity);
                finish();
            }
            break;
            case R.id.home_actionbar_user_details:{
                /**
                 * Go to UserActivity
                 */
                Intent userActivity =  new Intent(getApplicationContext(), UserActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userFirstName", currentUser.getFirstName());
                bundle.putString("userLastName", currentUser.getLastName());
                bundle.putString("userEmail", currentUser.getEmail());
                bundle.putString("userPassword", currentUser.getPassword());
                userActivity.putExtras(bundle);
                startActivity(userActivity);
                finish();
            }
            break;
            case R.id.home_actionbar_logout:{
                /**
                 * Logout
                 */
                Intent authActivity = new Intent(getApplicationContext(), AuthActivity.class);
                startActivity(authActivity);
                finish();
            }
            break;
            default:{
              return super.onOptionsItemSelected(item);
            }
        }
        return true;

    }

}
