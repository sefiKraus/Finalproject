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
import android.widget.Toast;

import com.sefy.finalproject.Model.ImageManager;
import com.sefy.finalproject.Model.ItemManager;
import com.sefy.finalproject.Model.ItemModel;
import com.sefy.finalproject.R;

import static android.app.Activity.RESULT_OK;

public class ItemAddFragment extends Fragment {
    private static final String BRAND_NAME = "brandName";
    private static final String USER_EMAIL = "userEmail";
    private TextView messageHandler;
    private String imageurl;
    private String brandName;
    private String userEmail;
    private EditText itemName, itemDescription , itemPrice;
    ImageView image;
    Button saveButton;
    private static ItemManager itemManager;

    private OnItemLAddListener mListener;

    public ItemAddFragment() {
        // Required empty public constructor
    }

    public static ItemAddFragment newInstance(String brandName, String userEmail) {
        ItemAddFragment fragment = new ItemAddFragment();
        itemManager = new ItemManager();
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
                String tempName = itemName.getText().toString();
                if(!itemName.getText().toString().matches("")){
                    dispatchTakePictureIntent();
                }
                else{
                    Toast.makeText(getActivity(),"please choose a name before uploading a picture",Toast.LENGTH_LONG).show();
                }
            }



        });
        this.saveButton = (Button) contentView.findViewById(R.id.item_add_fragment_saveItem);
        this.messageHandler = (TextView) contentView.findViewById(R.id.item_add_fragment_messageHandler);
        this.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemName.getText().toString().matches("")||
                   itemDescription.getText().toString().matches("")||
                   itemPrice.getText().toString().matches("")
                   ){
                    Toast.makeText(getActivity(),"Please insert all required fields",Toast.LENGTH_LONG).show();

                }
                else{
                    String name = itemName.getText().toString();
                    String description = itemDescription.getText().toString();
                    int price = Integer.parseInt(itemPrice.getText().toString());
                    ItemModel item = new ItemModel(name,price,imageurl,description,brandName,userEmail);
                    if(itemManager.addItemDB(item)){
                        Toast.makeText(getActivity(),"New Item Created",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getActivity(),"Error occurred please try again!!",Toast.LENGTH_LONG).show();

                    }
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
        /*intent to open image capture app*/
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        /*checks if such intent is possible and starts the activity of this app and expect for a result*/
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override /*image capture activity returns with a result through this function*/
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            /*low res image*/
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(imageBitmap);



            ImageManager imageManager = new ImageManager();
            imageManager.saveImageAndCache(imageBitmap, itemName.getText().toString(), new ImageManager.SaveImageListener() {
                @Override
                public void complete(String url) {
                    imageurl=url;
                }

                @Override
                public void fail() {
                    Log.d("ERROR","saving image failed!");
                }
            });


        }
    }

    public String getBrandName(){
        return this.brandName;
    }
    public interface OnItemLAddListener {
    }
}
