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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sefy.finalproject.Model.ImageManager;
import com.sefy.finalproject.Model.ItemManager;
import com.sefy.finalproject.Model.ItemModel;
import com.sefy.finalproject.R;

import static android.app.Activity.RESULT_OK;

public class ItemEditFragment extends Fragment {

    private static final String ITEM_NAME = "itemNAme";
    private static final String ITEM_DESC = "itemDescription";
    private static final String BRAND_NAME = "brandName";
    private static final String IMAGE_URL = "";
    private static final String USER_EMAIL = "userEmail";
    private static final String ITEM_PRICE = "itemPrice";


    private String itemName;
    private String itemDescription;
    private String brandName;
    private String  userEmail;
    private String imageurl;
    private int itemPrice;

    private EditText name, description , price;
    private ImageView _image;
    private Button save , remove;
    private TextView messageHandler;
    private OnItemEditListener mListener;

    private static ItemManager itemManager;
    private static ItemModel currentItem ;
    public ItemEditFragment() {
        // Required empty public constructor
    }


    public static ItemEditFragment newInstance(ItemModel item, String brandName, String userEmail) {
        ItemEditFragment fragment = new ItemEditFragment();
        itemManager = new ItemManager();
        Bundle args = new Bundle();
        args.putString(ITEM_NAME, item.getName());
        args.putString(ITEM_DESC, item.getDescription());
        args.putString(BRAND_NAME, brandName);
        args.putString(USER_EMAIL, userEmail);
        args.putString(IMAGE_URL, item.getImage());
        args.putInt(ITEM_PRICE, item.getPrice());
        fragment.setArguments(args);
        currentItem = item;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            itemName = getArguments().getString(ITEM_NAME);
            itemDescription = getArguments().getString(ITEM_DESC);
            brandName = getArguments().getString(BRAND_NAME);
            userEmail = getArguments().getString(USER_EMAIL);
            itemPrice = getArguments().getInt(ITEM_PRICE);
            imageurl=getArguments().getString(IMAGE_URL);





        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View contentView =  inflater.inflate(R.layout.fragment_item_edit, container, false);
        this.name = (EditText) contentView.findViewById(R.id.item_edit_fragment_itemName);
        this.description = (EditText) contentView.findViewById(R.id.item_edit_fragment_itemDescription);
        this.price = (EditText) contentView.findViewById(R.id.item_edit_fragment_itemPrice);
        this._image = (ImageView) contentView.findViewById(R.id.item_edit_fragment_image);

        _image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        this.save = (Button) contentView.findViewById(R.id.item_edit_fragment_save);
        this.remove = (Button) contentView.findViewById(R.id.item_edit_fragment_remove);
        this.messageHandler = (TextView) contentView.findViewById(R.id.item_edit_fragment_messageHandler);

        this.name.setText(itemName);
        this.description.setText(itemDescription);
        this.price.setText(String.valueOf(itemPrice));
        ImageManager imageman= new ImageManager();
        imageman.loadImageFromCache(imageurl, new ImageManager.GetImageListener() {
            @Override
            public void onSuccess(Bitmap image) {
                _image.setImageBitmap(image);
              //  notifyDataSetChanged();
           }

           @Override
            public void onFail() {

           }
        });




        // Setting buttons listeners

        this.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(name.getText().toString().matches("")||
                   price.getText().toString().matches("")||imageurl == "" || imageurl == null){
                    Toast.makeText(getActivity(),"Please insert all required fields",Toast.LENGTH_LONG).show();

                }
                else{
                    String iName = name.getText().toString();
                    String iDescription = description.getText().toString();
                    int iPrice = Integer.parseInt(price.getText().toString());

                    currentItem.setName(iName);
                    currentItem.setPrice(iPrice);
                    currentItem.setDescription(iDescription);
                    currentItem.setImage(imageurl);
                    if(itemManager.addItemDB(currentItem)){
                        Toast.makeText(getActivity(),"Item patched successfully !!",Toast.LENGTH_LONG).show();

                    }
                    else{
                        Toast.makeText(getActivity(),"Error occurred, please try again",Toast.LENGTH_LONG).show();

                    }
                }
            }
        });

        this.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String brandName = currentItem.getBrandName();
                if(itemManager.deleteItemDB(currentItem.getId())){
                    Toast.makeText(getActivity(),"Item deleted successfully !!",Toast.LENGTH_LONG).show();
                    mListener.onItemRemove(brandName);

                }
            }
        });
        return contentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemEditListener) {
            mListener = (OnItemEditListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnItemEditListener");
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
            _image.setImageBitmap(imageBitmap);
            ImageManager imageManager = new ImageManager();
            imageManager.saveImageAndCache(imageBitmap, itemName , new ImageManager.SaveImageListener() {
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
    public interface OnItemEditListener {
        void onItemRemove(String brandName);
    }
}
