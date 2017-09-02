package com.sefy.finalproject.Item;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sefy.finalproject.Cart.CartListService;
import com.sefy.finalproject.CustomMessageEvent;
import com.sefy.finalproject.Model.CartItem;
import com.sefy.finalproject.Model.ImageManager;
import com.sefy.finalproject.Model.ItemModel;
import com.sefy.finalproject.R;

import org.greenrobot.eventbus.EventBus;

public class ItemDetailsFragment extends Fragment {

    private static final String ITEM_NAME = "itemName";
    private static final String ITEM_DESCRIPTION = "itemDescription";
    private static final String ITEM_PRICE = "itemPrice";
    private static final String BRAND_NAME = "brandName";

    private TextView iName , iDescription , iPrice;
    private ImageView iImage;
    private ImageButton addToCart;


    private int itemPrice;
    private String brandName;
    private String itemName;
    private String itemDescription;
    static ItemModel currentItem = null;

    public ItemDetailsFragment() {
    }


    public String getBrandName(){
        return this.currentItem.getBrandName();
    }

    public static ItemDetailsFragment newInstance(ItemModel itemModel) {
        ItemDetailsFragment fragment = new ItemDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ITEM_NAME, itemModel.getName());
        args.putString(ITEM_DESCRIPTION, itemModel.getDescription());
        args.putString(ITEM_PRICE, String.valueOf(itemModel.getPrice()));
        args.putString(BRAND_NAME, itemModel.getBrandName());
        currentItem = itemModel;
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
        this.iName = (TextView) contentView.findViewById(R.id.item_details_fragment_itemName);
        this.iDescription = (TextView) contentView.findViewById(R.id.item_details_fragment_description);
        this.iImage = (ImageView) contentView.findViewById(R.id.item_details_fragment_image);
        this.iPrice = (TextView) contentView.findViewById(R.id.item_details_fragment_price);

        this.iName.setText(currentItem.getName());
        this.iPrice.setText(String.valueOf(currentItem.getPrice()));
        this.iDescription.setText(currentItem.getDescription());

        this.addToCart = (ImageButton) contentView.findViewById(R.id.item_details_fragment_addToCart);
        this.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomMessageEvent event = new CustomMessageEvent();
                event.setCartItem(new CartItem(currentItem,1 , currentItem.getPrice()));
                EventBus.getDefault().post(event);

                Toast.makeText(getActivity(),"Item added to shopping list",Toast.LENGTH_LONG).show();

            }
        });

        ImageManager imageman= new ImageManager();
        imageman.loadImageFromCache(currentItem.getImage(), new ImageManager.GetImageListener() {
            @Override
            public void onSuccess(Bitmap image) {
                iImage.setImageBitmap(image);
            }

            @Override
            public void onFail() {

            }
        });
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
