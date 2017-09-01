package com.sefy.finalproject.Item;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.sefy.finalproject.Cart.CartListService;
import com.sefy.finalproject.CustomMessageEvent;
import com.sefy.finalproject.Events.CartAddEvent;
import com.sefy.finalproject.Events.CartRemoveEvent;
import com.sefy.finalproject.Model.BrandManager;
import com.sefy.finalproject.Model.BrandModel;
import com.sefy.finalproject.Model.CartItem;
import com.sefy.finalproject.Model.ImageManager;
import com.sefy.finalproject.Model.ItemManager;
import com.sefy.finalproject.Model.ItemModel;
import com.sefy.finalproject.R;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Vector;

public class ItemListFragment extends Fragment {

    private ListView itemList;
    private ImageView brandImage;
    private SearchView searchBar;
    private TextView brandNameText, brandDescriptionText;
    private static List<ItemModel> itemListVector;

    private static final String BRAND_NAME = "brandName";
    private static final String USER_EMAIL = "userEmail";

    private String brandName;
    private static String userEmail;

    private ItemListAdapter adapter;
    private OnItemListListener mListener;
    private static ItemManager itemManager;
    public ItemListFragment() {
        // Required empty public constructor
    }

    public static ItemListFragment newInstance(String brandName,String userEmail) {
        ItemListFragment fragment = new ItemListFragment();
        Bundle args = new Bundle();
        itemManager = new ItemManager();
        itemListVector = new Vector<>();

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
        View contentView = inflater.inflate(R.layout.fragment_item_list, container, false);
        this.searchBar = (SearchView) contentView.findViewById(R.id.item_list_fragment_search);
        this.brandImage = (ImageView) contentView.findViewById(R.id.item_list_fragment_brand_image);
        this.brandNameText = (TextView) contentView.findViewById(R.id.item_list_fragment_brand_name);
        this.brandDescriptionText = (TextView) contentView.findViewById(R.id.item_list_fragment_brand_description);
        this.itemList = (ListView) contentView.findViewById(R.id.item_list_fragment_list);

        this.adapter = new ItemListAdapter();
        this.itemList.setAdapter(this.adapter);
//
//        BrandManager brandManager = new BrandManager();
//        brandManager.getBrandDB(brandName, new BrandManager.GetBrandCallback() {
//            @Override
//            public void onComplete(BrandModel brand) {
//                brandNameText.setText(brand.getName());
//                brandDescriptionText.setText(brand.getDescription());
//                ImageManager imageManager = new ImageManager();
//                imageManager.loadImageFromCache(brand.getImage(), new ImageManager.GetImageListener() {
//                    @Override
//                    public void onSuccess(Bitmap image) {
//                        brandImage.setImageBitmap(image);
//                    }
//
//                    @Override
//                    public void onFail() {
//
//                    }
//                });
//
//
//
//
//
//
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//        });
//
//
//
//
//
//
//
//        itemManager.getAllItemsByBrand(brandName, new ItemManager.GetItemListCallback() {
//            @Override
//            public void onComplete(List<ItemModel> list) {
//                if(list !=null){
//                    itemListVector = list;
//                    adapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//        });
        this.itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemModel itemModel = itemListVector.get(position);
                if(itemModel.getUserEmail().equals(userEmail)){
                    mListener.onItemEditRequest(itemModel,brandName,userEmail);
                }
                else{
                    mListener.onItemSelected(itemModel.getName());
                }
            }
        });
        return contentView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemListListener) {
            mListener = (OnItemListListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnItemListListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public String getBrandName(){
        return  this.brandName;
    }


    public interface OnItemListListener {
        void onItemSelected(String itemName);
        void onItemEditRequest(ItemModel item, String brandName, String userEmail);
    }


    /*-----------------------------------------------------------------------------*/
    class ItemListAdapter extends BaseAdapter{
        LayoutInflater inflater = getActivity().getLayoutInflater();

        @Override
        public int getCount() {
            if(itemListVector == null){
                return 0;
            }
            return itemListVector.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if(convertView == null){
                /**
                 * getting fragment inflater
                 */
                convertView = inflater.inflate(R.layout.item_list_row,null);
                if(!itemListVector.get(position).getUserEmail().equals(userEmail)){
                    final ImageButton addToCart = (ImageButton) convertView.findViewById(R.id.item_list_row_add_to_cart);
                    addToCart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ItemModel item = itemListVector.get((int)v.getTag());
                            item.setClicked(!item.isClicked());
                            CartItem cartItem = new CartItem(item,1,item.getPrice());
                            if(item.isClicked()){
                                addToCart.setBackgroundResource(R.drawable.remove_from_cart);
                                CartListService.getInstance().addToCart(cartItem);
                            }
                            else{
                                addToCart.setBackgroundResource(R.drawable.add_to_cart);
                                CartListService.getInstance().removeFromCart(cartItem);
                            }

                        }
                    });
                }

            }

            TextView itemName = (TextView) convertView.findViewById(R.id.item_list_row_name);
            TextView itemDescription = (TextView) convertView.findViewById(R.id.item_list_row_description);
            TextView itemPrice = (TextView) convertView.findViewById(R.id.item_list_row_price);
            final ImageButton addToCart = (ImageButton) convertView.findViewById(R.id.item_list_row_add_to_cart);

            final ImageView itemImage = (ImageView) convertView.findViewById(R.id.item_list_row_image);

            ItemModel item = itemListVector.get(position);
//            ImageManager imageman= new ImageManager();
//            imageman.loadImageFromCache(item.getImage(), new ImageManager.GetImageListener() {
//                @Override
//                public void onSuccess(Bitmap image) {
//                    itemImage.setImageBitmap(image);
//                    notifyDataSetChanged();
//                }
//
//                @Override
//                public void onFail() {
//
//                }
//            });
            itemName.setText(item.getName());
            itemDescription.setText(item.getDescription());
            itemPrice.setText(String.valueOf(item.getPrice())+" $");

            /**
             * add to cart button should display only if the item's user email is not
             * current email
             */
            if(!item.getUserEmail().equals(userEmail)){
            if(item.isClicked()){
                addToCart.setBackgroundResource(R.drawable.remove_from_cart);

            }
            else{
                addToCart.setBackgroundResource(R.drawable.add_to_cart);
            }
            addToCart.setTag(position);

            }
            else{
                addToCart.setVisibility(View.INVISIBLE);
            }
            return convertView;
        }
    }

}

