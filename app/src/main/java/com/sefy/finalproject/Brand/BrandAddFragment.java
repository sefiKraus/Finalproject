package com.sefy.finalproject.Brand;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
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

import com.sefy.finalproject.Model.BrandModel;
import com.sefy.finalproject.R;

import org.w3c.dom.Text;

import static android.app.Activity.RESULT_OK;


public class BrandAddFragment extends Fragment {

    private static final String USER_EMAIL = "userEmail";
    private OnBrandAddListener mListener;
    private String userEmail;
    private EditText brandName, brandDescription;
    private Button saveButton;
    private ImageView image;
    private TextView messageHandler;
    public BrandAddFragment() {
        // Required empty public constructor
    }

    public static BrandAddFragment newInstance(String userEmail) {
        BrandAddFragment fragment = new BrandAddFragment();
        Log.d("TAG","BrandAddFragment received "+userEmail);
        Bundle args = new Bundle();
        args.putString(USER_EMAIL, userEmail);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userEmail = getArguments().getString(USER_EMAIL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View contentView =  inflater.inflate(R.layout.fragment_brand_add, container, false);
        this.brandName = (EditText) contentView.findViewById(R.id.brand_add_fragment_brandName);
        this.brandDescription = (EditText) contentView.findViewById(R.id.brand_add_fragment_brandDescription);
        this.image = (ImageView) contentView.findViewById(R.id.brand_add_fragment_image);
        this.messageHandler = (TextView) contentView.findViewById(R.id.brand_add_fragment_messageHandler);


        this.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        this.saveButton = (Button) contentView.findViewById(R.id.brand_add_fragment_save);

        this.saveButton.setOnClickListener(new View.OnClickListener() {
            //TODO: set image empty condition
            @Override
            public void onClick(View v) {
                if(brandName.getText().toString().matches("") ||
                        brandDescription.getText().toString().matches("")){
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
                }else{
                    String name  = brandName.getText().toString();
                    String description = brandDescription.getText().toString();

                    BrandModel brandModel = new BrandModel(name,null,description,userEmail);
                    Log.d("TAG","Brand created: "+brandModel.toString());
                }
            }
        });
        return contentView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBrandAddListener) {
            mListener = (OnBrandAddListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBrandAddListener");
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
    public interface OnBrandAddListener {
        void onBrandAdd(String name, ImageView image, String description);
    }
}