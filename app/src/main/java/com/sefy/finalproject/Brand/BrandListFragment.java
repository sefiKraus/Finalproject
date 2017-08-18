package com.sefy.finalproject.Brand;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.sefy.finalproject.Model.BrandModel;
import com.sefy.finalproject.R;

import java.util.Vector;

public class BrandListFragment extends Fragment {
    BrandListAdapter adapter;
    private ListView brandList;
    private SearchView searchBar;
    private static Vector<BrandModel> brandListVector;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BrandListFragment() {
        // Required empty public constructor
    }

    public static BrandListFragment newInstance() {
        brandListVector = new Vector<>();
        BrandListFragment fragment = new BrandListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView =  inflater.inflate(R.layout.fragment_brand_list, container, false);
        this.brandList = (ListView) contentView.findViewById(R.id.brand_list_fragment_list);
        this.searchBar = (SearchView) contentView.findViewById(R.id.brand_list_fragment_search);

        this.adapter = new BrandListAdapter();
        this.brandList.setAdapter(this.adapter);
        return contentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }

    /**
     * List adapter
     */
    class BrandListAdapter extends BaseAdapter{
        LayoutInflater inflater = getActivity().getLayoutInflater();

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        /**
         * Setting row view in index "position"
         * @param position
         * @param convertView
         * @param parent
         * @return
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                /**
                 * getting fragment inflater
                 */
                convertView = inflater.inflate(R.layout.brand_item,null);
            }

            TextView brandName = (TextView) convertView.findViewById(R.id.brand_item_name);
            ImageView brandImage = (ImageView) convertView.findViewById(R.id.brand_item_picture);
            TextView brandDescription = (TextView) convertView.findViewById(R.id.brand_item_description);

            brandName.setText("brand name "+ position);
            brandDescription.setText("brand description "+ position);
            return convertView;
        }
    }
}
