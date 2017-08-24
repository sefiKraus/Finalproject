package com.sefy.finalproject.Item;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
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

import static android.app.Activity.RESULT_OK;

public class ItemAddFragment extends Fragment {
    private static final String BRAND_NAME = "brandName";
    private static final String USER_EMAIL = "userEmail";
    private TextView messageHandler;
    private String brandName;
    private String userEmail;
    private EditText itemName, itemDescription , itemPrice;
    ImageView image;
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
        this.image = (ImageView) contentView.findViewById(R.id.item_add_fragment_image);



        this.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
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

        /*-----------Handling taking picture------------------------*/

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(imageBitmap);
        }
    }

    public String getBrandName(){
        return this.brandName;
    }
    public interface OnItemLAddListener {
        //TODO: handle image
        void onAddItem(ItemModel item);
    }
}
