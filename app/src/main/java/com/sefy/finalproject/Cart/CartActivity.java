package com.sefy.finalproject.Cart;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.Iterator;
import java.util.Vector;

public class CartActivity extends Activity {

    private Vector<CartItem> cartItemVector;
    private ListView cartList;
    private CartListAdapter adapter;
    Button checkoutButton;
    TextView cartTotalPrice;
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
                Log.d("TAG", "Total price is: "+ cartTotalPrice.getText());
            }
        });
        this.cartTotalPrice.setText(String.valueOf(calculateTotal()));
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

    int calculateTotal() {
        Iterator it = cartItemVector.iterator();
        int total = 0;
        while (it.hasNext()){

            total += ((CartItem)it.next()).getTotalPrice();

        }
        return total;

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
            ImageView itemPricture = (ImageView) convertView.findViewById(R.id.cart_list_row_image);
            final TextView amount = (TextView) convertView.findViewById(R.id.cart_list_row_amount);

            itemName.setText(cartItem.getItem().getName());
            totalPrice.setText(String.valueOf(cartItem.getItem().getPrice()));
            amount.setText(String.valueOf(cartItem.getQuantity()));
            Button remove = (Button) convertView.findViewById(R.id.cart_list_row_remove);



//            amount.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    if(s.toString().matches("")){
//
//                      //  amount.setText("");
//                       // cartItem.setQuantity(0);
//                       // totalPrice.setText(String.valueOf(cartItem.getTotalPrice()));
//
//                    }else{
//
//                        int newAmount = Integer.parseInt(s.toString());
//                        cartItem.setQuantity(newAmount);
//                        totalPrice.setText(String.valueOf(cartItem.getTotalPrice()));
//                        cartTotalPrice.setText(String.valueOf(calculateTotal()));
//                    }
//                }
//            });
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartItemVector.remove(position);
                    cartItem.getItem().setClicked(false);
                    cartItem.setQuantity(0);
                    cartItem.setTotalPrice(0);

                    notifyDataSetChanged();
                }
            });
            //itemPricture


            return convertView;
        }
    }
}
