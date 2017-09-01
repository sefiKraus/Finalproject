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
import android.widget.Toast;

import com.sefy.finalproject.Model.BrandManager;
import com.sefy.finalproject.Model.BrandModel;
import com.sefy.finalproject.Model.ImageManager;
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
    private String imageurl;
    private TextView messageHandler;
    private static BrandManager brandManager;
    public BrandAddFragment() {
        // Required empty public constructor
    }

    public static BrandAddFragment newInstance(String userEmail) {
        brandManager = new BrandManager();
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
                        brandDescription.getText().toString().matches("") || imageurl == "" || imageurl == null){

                    Toast.makeText(getActivity(),"please fill all the fields",Toast.LENGTH_LONG).show();

                }else{
                    String name  = brandName.getText().toString();
                    String description = brandDescription.getText().toString();

                    final BrandModel brandModel = new BrandModel(name,imageurl,description,userEmail);

                    brandManager.getBrandDB(name, new BrandManager.GetBrandCallback() {
                        @Override
                        public void onComplete(BrandModel brand) {
                            if(brand == null){
                                if(brandManager.addBrandDB(brandModel)){
                                    mListener.onBrandAdd(brandModel);
                                }
                            }else{
                                Toast.makeText(getActivity(),"Brand already exists in database!",Toast.LENGTH_LONG).show();

                            }
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
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
           ImageManager imageManager = new ImageManager();
            imageManager.saveImageAndCache(imageBitmap, brandName.getText().toString(), new ImageManager.SaveImageListener() {
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
    public interface OnBrandAddListener {
        void onBrandAdd(BrandModel brand);
    }
}
