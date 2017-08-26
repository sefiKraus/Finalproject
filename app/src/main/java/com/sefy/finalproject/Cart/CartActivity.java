package com.sefy.finalproject.Cart;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.sefy.finalproject.Model.CartItem;
import com.sefy.finalproject.Model.ItemModel;
import com.sefy.finalproject.R;

import java.util.Vector;

public class CartActivity extends Activity {

    private Vector<CartItem> cartItemVector;
    private ListView cartList;
    private CartListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        this.cartItemVector = new Vector<>();
        this.cartList = (ListView) this.findViewById(R.id.cart_activity_list);
        this.adapter = new CartListAdapter();
        this.cartList.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.cartItemVector = CartListService.getInstance().getCartItemVector();
        if(this.cartItemVector == null){
            Log.d("TAG","Vector size: 0");
        }
        else{
            Log.d("TAG","Vector size: "+ this.cartItemVector.size());
        }
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
            TextView itemName = (TextView) convertView.findViewById(R.id.cart_list_row_itemName);
            TextView totalPrice = (TextView) convertView.findViewById(R.id.cart_list_row_totalPrice);
            ImageView itemPricture = (ImageView) convertView.findViewById(R.id.cart_list_row_image);
            EditText amount = (EditText) convertView.findViewById(R.id.cart_list_row_amount);
            Button remove = (Button) convertView.findViewById(R.id.cart_list_row_remove);

            CartItem cartItem = cartItemVector.get(position);
            itemName.setText(cartItem.getItem().getName());

            return convertView;
        }
    }
}
