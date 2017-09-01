package com.sefy.finalproject.Item;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sefy.finalproject.Model.ItemModel;
import com.sefy.finalproject.R;

public class ItemDetailsFragment extends Fragment {

    private static final String ITEM_NAME = "param1";
    private static final String ITEM_DESCRIPTION = "param2";
    private static final String ITEM_PRICE = "";
    private static final String BRAND_NAME = "param2";


    private int itemPrice;
    private String brandName;
    private String itemName;
    private String itemDescription;


    public ItemDetailsFragment() {
        // Required empty public constructor
    }


    public static ItemDetailsFragment newInstance(ItemModel itemModel) {
        ItemDetailsFragment fragment = new ItemDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ITEM_NAME, itemModel.getName());
        args.putString(ITEM_DESCRIPTION, itemModel.getDescription());
        args.putString(ITEM_PRICE, String.valueOf(itemModel.getPrice()));
        args.putString(BRAND_NAME, itemModel.getBrandName());

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            itemName = getArguments().getString(ITEM_NAME);
            itemDescription = getArguments().getString(ITEM_DESCRIPTION);
            brandName = getArguments().getString(BRAND_NAME);
            itemPrice = Integer.valueOf(getArguments().getString(ITEM_PRICE));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_item_details, container, false);

        return contentView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
