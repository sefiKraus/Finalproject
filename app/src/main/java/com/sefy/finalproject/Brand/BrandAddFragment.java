package com.sefy.finalproject.Brand;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sefy.finalproject.R;


public class BrandAddFragment extends Fragment {

    private static final String USER_EMAIL = "userEmail";
    private OnBrandAddListener mListener;
    private String userEmail;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_brand_add, container, false);
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

    public interface OnBrandAddListener {
        void onBrandAdd(String name, ImageView image, String description);
    }
}
