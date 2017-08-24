package com.sefy.finalproject.Item;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sefy.finalproject.Model.ItemModel;
import com.sefy.finalproject.R;

import static android.app.Activity.RESULT_OK;

public class ItemEditFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ITEM_NAME = "itemNAme";
    private static final String ITEM_DESC = "itemDescription";
    private static final String BRAND_NAME = "brandName";
    private static final String USER_EMAIL = "userEmail";
    private static final String ITEM_PRICE = "itemPrice";


    // TODO: Rename and change types of parameters
    private String itemName;
    private String itemDescription;
    private String brandName;
    private String  userEmail;
    private int itemPrice;

    private EditText name, description , price;
    private ImageView image;
    private Button save , remove;
    private TextView messageHandler;
    private OnItemEditListener mListener;

    public ItemEditFragment() {
        // Required empty public constructor
    }


    public static ItemEditFragment newInstance(String itemName, String itemDescription, int price, String brandName, String userEmail) {
        ItemEditFragment fragment = new ItemEditFragment();
        Bundle args = new Bundle();
        args.putString(ITEM_NAME, itemName);
        args.putString(ITEM_DESC, itemDescription);
        args.putString(BRAND_NAME, brandName);
        args.putString(USER_EMAIL, userEmail);
        args.putInt(ITEM_PRICE, price);
        fragment.setArguments(args);
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
        this.image = (ImageView) contentView.findViewById(R.id.item_edit_fragment_image);

        image.setOnClickListener(new View.OnClickListener() {
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
        // Setting buttons listeners

        this.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().matches("")||
                   price.getText().toString().matches("")){
                    messageHandler.setText("Please make sure that name and price are not empty");
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
                    String iName = name.getText().toString();
                    String iDescription = description.getText().toString();
                    int iPrice = Integer.parseInt(price.getText().toString());
                    //TODO: Handle image
                    mListener.onItemEdit(iName,iPrice,iDescription,brandName, userEmail);
                }
            }
        });

        this.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.onItemRemove();
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
            image.setImageBitmap(imageBitmap);
        }
    }
    public interface OnItemEditListener {
        //TODO: handle image in onItemEdit
        void onItemEdit(String name , int price, String description , String brand , String userEmail);
        void onItemRemove();
    }
}
