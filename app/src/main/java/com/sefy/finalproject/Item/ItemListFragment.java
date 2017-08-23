package com.sefy.finalproject.Item;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.sefy.finalproject.Model.BrandModel;
import com.sefy.finalproject.Model.ItemModel;
import com.sefy.finalproject.R;

import java.util.Vector;

public class ItemListFragment extends Fragment {

    private ListView itemList;
    private ImageView brandImage;
    private SearchView searchBar;
    private TextView brandNameText, brandDescriptionText;

    private static Vector<ItemModel> itemListVector;

    private static final String BRAND_NAME = "brandName";
    private static final String ARG_PARAM2 = "param2";

    private String brandName;
    private String mParam2;
    private ItemListAdapter adapter;
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
        itemListVector = new Vector<>();
    //String name, int price, ImageView image, String description, String brandName,String userEmail
        //TODO: remove after connecting to firebase
        for(int i = 0 ; i< 20; i++){
            itemListVector.add(new ItemModel("item "+i,i* 15,null,"Item description "+i,brandName.toString(),null));
        }
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
        View contentView = inflater.inflate(R.layout.fragment_item_list, container, false);
        this.searchBar = (SearchView) contentView.findViewById(R.id.item_list_fragment_search);
        this.brandImage = (ImageView) contentView.findViewById(R.id.item_list_fragment_brand_image);
        this.brandNameText = (TextView) contentView.findViewById(R.id.item_list_fragment_brand_name);
        this.brandDescriptionText = (TextView) contentView.findViewById(R.id.item_list_fragment_brand_description);
        this.itemList = (ListView) contentView.findViewById(R.id.item_list_fragment_list);

        this.adapter = new ItemListAdapter();
        this.itemList.setAdapter(this.adapter);
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
    }

    class ItemListAdapter extends BaseAdapter{
        LayoutInflater inflater = getActivity().getLayoutInflater();

        @Override
        public int getCount() {
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
                final ImageButton addToCart = (ImageButton) convertView.findViewById(R.id.item_list_row_add_to_cart);
                addToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ItemModel item = itemListVector.get((int)v.getTag());
                        item.setClicked(!item.isClicked());
                        if(item.isClicked()){
                            addToCart.setBackgroundResource(R.drawable.remove_from_cart);

                        }
                        else{
                            addToCart.setBackgroundResource(R.drawable.add_to_cart);
                        }

                    }
                });
            }
            TextView itemName = (TextView) convertView.findViewById(R.id.item_list_row_name);
            TextView itemDescription = (TextView) convertView.findViewById(R.id.item_list_row_description);
            TextView itemPrice = (TextView) convertView.findViewById(R.id.item_list_row_price);
            final ImageButton addToCart = (ImageButton) convertView.findViewById(R.id.item_list_row_add_to_cart);
            //TODO: set picture when connected to db
            final ImageView itemImage = (ImageView) convertView.findViewById(R.id.item_list_row_image);

            ItemModel item = itemListVector.get(position);

            itemName.setText(item.getName());
            itemDescription.setText(item.getDescription());
            itemPrice.setText(String.valueOf(item.getPrice()));
            if(item.isClicked()){
                addToCart.setBackgroundResource(R.drawable.remove_from_cart);

            }
            else{
                addToCart.setBackgroundResource(R.drawable.add_to_cart);
            }
            addToCart.setTag(position);

            return convertView;
        }
    }

}

