package com.sefy.finalproject.Item;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sefy.finalproject.R;

public class ItemListFragment extends Fragment {

    private static final String BRAND_NAME = "brandName";
    private static final String ARG_PARAM2 = "param2";

    private String brandName;
    private String mParam2;

    private OnItemListListener mListener;

    public ItemListFragment() {
        // Required empty public constructor
    }

    public static ItemListFragment newInstance(String brandName) {
        ItemListFragment fragment = new ItemListFragment();
        Bundle args = new Bundle();
        args.putString(BRAND_NAME, brandName);
        Log.d("TAG","Item list of brand: "+ brandName);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            brandName = getArguments().getString(BRAND_NAME);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_list, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemListListener) {
            mListener = (OnItemListListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnItemListListener {
        void onItemSelected(String itemName);
    }
}
