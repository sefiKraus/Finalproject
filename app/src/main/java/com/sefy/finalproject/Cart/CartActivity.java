package com.sefy.finalproject.Cart;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.sefy.finalproject.HomeActivity;
import com.sefy.finalproject.Item.ItemListFragment;
import com.sefy.finalproject.Model.CartItem;
import com.sefy.finalproject.Model.ImageManager;
import com.sefy.finalproject.Model.ItemModel;
import com.sefy.finalproject.R;
import com.sefy.finalproject.User.UserActivity;

import java.util.Iterator;
import java.util.Vector;

public class CartActivity extends Activity {

    private Vector<CartItem> cartItemVector;
    private ListView cartList;
    private CartListAdapter adapter;
    Button checkoutButton;
    TextView cartTotalPrice;
    Bundle userDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        this.cartItemVector = new Vector<>();
        this.cartList = (ListView) this.findViewById(R.id.cart_activity_list);
        this.adapter = new CartListAdapter();
        this.cartList.setAdapter(adapter);
        this.cartTotalPrice = (TextView) findViewById(R.id.cart_activity_TotalPrice);
        this.checkoutButton = (Button) findViewById(R.id.cart_activity_buy);
        this.checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cartItemVector.size() == 0){
                    Toast.makeText(CartActivity.this,"Shopping cart is empty !!",Toast.LENGTH_LONG).show();

                }
                else{
                    Toast.makeText(CartActivity.this,"ORDER SENT ! THANK YOU !",Toast.LENGTH_LONG).show();
                    checkoutButton.setVisibility(View.GONE);
                }
            }
        });
        this.userDetails = getIntent().getExtras();
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.cartItemVector = CartListService.getInstance().getCartItemVector();
        this.cartTotalPrice.setText(String.valueOf(calculateTotal()) + "$");
    }

    int calculateTotal() {
        Iterator it = cartItemVector.iterator();
        int total = 0;
        while (it.hasNext()){

            total += ((CartItem)it.next()).getTotalPrice();

        }
        return total;

    }

    private Menu menu;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId){
            case android.R.id.home:{
                Intent homeActivity = new Intent(getApplicationContext() , HomeActivity.class);
                homeActivity.putExtras(this.userDetails);
                startActivity(homeActivity);
            }
            break;
            default:{
                return super.onOptionsItemSelected(item);
            }
        }
        return true;
    }

    class CartListAdapter extends BaseAdapter {
        LayoutInflater inflater = CartActivity.this.getLayoutInflater();

        @Override
        public int getCount() {
            if(cartItemVector == null){
                return 0 ;
            }
            return cartItemVector.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if(convertView == null) {
                convertView = inflater.inflate(R.layout.cart_list_row, null);

            }
            final CartItem cartItem = cartItemVector.get(position);

            TextView itemName = (TextView) convertView.findViewById(R.id.cart_list_row_itemName);
            final TextView totalPrice = (TextView) convertView.findViewById(R.id.cart_list_row_totalPrice);
            final ImageView itemPicture = (ImageView) convertView.findViewById(R.id.cart_list_row_image);
            final TextView amount = (TextView) convertView.findViewById(R.id.cart_list_row_amount);
            final ProgressBar spinner = (ProgressBar)  convertView.findViewById(R.id.cart_list_spinner);
            itemName.setText(cartItem.getItem().getName());
            totalPrice.setText(String.valueOf(cartItem.getItem().getPrice()) +"$");
            amount.setText(String.valueOf(cartItem.getQuantity()));
            Button remove = (Button) convertView.findViewById(R.id.cart_list_row_remove);
            Button minus = (Button) convertView.findViewById(R.id.cart_list_row_minus);
            Button plus = (Button) convertView.findViewById(R.id.cart_list_row_plus);

            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cartItem.getQuantity() != 0){
                        cartItem.setQuantity(cartItem.getQuantity()-1);
                        amount.setText(String.valueOf(cartItem.getQuantity()));
                        cartTotalPrice.setText(String.valueOf(calculateTotal()) + "$");
                    }
                }
            });

            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartItem.setQuantity(cartItem.getQuantity()+1);
                    amount.setText(String.valueOf(cartItem.getQuantity()));
                    cartTotalPrice.setText(String.valueOf(calculateTotal()) + "$");
                }
            });



            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartItemVector.remove(position);
                    cartItem.getItem().setClicked(false);
                    cartItem.setQuantity(0);
                    cartItem.setTotalPrice(0);
                    cartTotalPrice.setText(String.valueOf(calculateTotal()) + "$");
                    CartListService.getInstance().removeFromCart(cartItem);
                    notifyDataSetChanged();
                }
            });
            //itemPricture
            ImageManager imageManager = new ImageManager();
            imageManager.loadImageFromCache(cartItem.getItem().getImage(), new ImageManager.GetImageListener() {
                @Override
                public void onSuccess(Bitmap image) {
                    itemPicture.setImageBitmap(image);
                    spinner.setVisibility(View.GONE);
                }

                @Override
                public void onFail() {

                }
            });

            return convertView;
        }
    }
}
