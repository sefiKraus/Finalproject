package com.sefy.finalproject.Item;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.sefy.finalproject.Model.ItemModel;
import com.sefy.finalproject.R;

public class ItemAddFragment extends Fragment {
    private static final String BRAND_NAME = "brandName";
    private static final String USER_EMAIL = "userEmail";
    private TextView messageHandler;
    private String brandName;
    private String userEmail;
    private EditText itemName, itemDescription , itemPrice;
    ImageButton image;
    Button saveButton;

    private OnItemLAddListener mListener;

    public ItemAddFragment() {
        // Required empty public constructor
    }

    public static ItemAddFragment newInstance(String brandName, String userEmail) {
        ItemAddFragment fragment = new ItemAddFragment();
        Bundle args = new Bundle();
        args.putString(BRAND_NAME, brandName);
        args.putString(USER_EMAIL, userEmail);
        Log.d("TAG","ItemAddFragment received "+brandName+" "+userEmail);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            brandName = getArguments().getString(BRAND_NAME);
            userEmail = getArguments().getString(USER_EMAIL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View contentView = inflater.inflate(R.layout.fragment_item_add, container, false);
        this.itemPrice = (EditText) contentView.findViewById(R.id.item_add_fragment_itemPrice);
        this.itemDescription = (EditText) contentView.findViewById(R.id.item_add_fragment_itemDescription);
        this.itemName = (EditText) contentView.findViewById(R.id.item_add_fragment_itemName);
        this.image = (ImageButton) contentView.findViewById(R.id.item_add_fragment_image);
        this.saveButton = (Button) contentView.findViewById(R.id.item_add_fragment_saveItem);
        this.messageHandler = (TextView) contentView.findViewById(R.id.item_add_fragment_messageHandler);
        this.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //TODO: insert if image is null condition
            public void onClick(View v) {
                if(itemName.getText().toString().matches("")||
                   itemDescription.getText().toString().matches("")||
                   itemPrice.getText().toString().matches("")
                   ){
                    messageHandler.setText("Please insert all required fields!!");
                    messageHandler.setTextColor(Color.RED);

                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                @Override
                                public void run() {
                                    messageHandler.setText("");
                                }
                            }
                    ,3000);
                }
                else{
                    //TODO: create new item in data base and use listener
                    String name = itemName.getText().toString();
                    String description = itemDescription.getText().toString();
                    int price = Integer.parseInt(itemPrice.getText().toString());
                    ItemModel item = new ItemModel(name,price,null,description,brandName,userEmail);
                    Log.d("TAG","Item created "+ item.toString());
                }
            }
        });
        return contentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemLAddListener) {
            mListener = (OnItemLAddListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnItemLAddListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public String getBrandName(){
        return this.brandName;
    }
    public interface OnItemLAddListener {
        void onAddItem(String itemName, String description , String brandName);
    }
}
