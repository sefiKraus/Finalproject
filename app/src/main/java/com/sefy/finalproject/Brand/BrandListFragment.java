package com.sefy.finalproject.Brand;

import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.sefy.finalproject.Model.BrandManager;
import com.sefy.finalproject.Model.BrandModel;
import com.sefy.finalproject.Model.ImageManager;
import com.sefy.finalproject.R;

import java.util.List;
import java.util.Vector;

public class BrandListFragment extends Fragment{
    BrandListAdapter adapter;
    private ListView brandList;
  //  private SearchView searchBar;
    private static List<BrandModel> brandListVector;
    private static final String USER_EMAIL = "userEmail";
    private static final String ARG_PARAM2 = "param2";

    private String userEmail;
    private String mParam2;
    private static BrandManager brandManager;

    private OnBrandListListener mListener;

    public BrandListFragment() {
        // Required empty public constructor
    }

    public BrandListAdapter getAdapter() {
        return adapter;
    }

    public static BrandListFragment newInstance(String userEmail) {
        Bundle args = new Bundle();
        args.putString(USER_EMAIL, userEmail);
        BrandListFragment fragment = new BrandListFragment();
        brandListVector = new Vector<>();
        brandManager = new BrandManager();

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

        View contentView =  inflater.inflate(R.layout.fragment_brand_list, container, false);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(false);
        this.brandList = (ListView) contentView.findViewById(R.id.brand_list_fragment_list);

        this.adapter = new BrandListAdapter();
        this.brandList.setAdapter(this.adapter);
        brandManager.getAllBrandsAndObserve(new BrandManager.GetBrandListCallback() {
            @Override
            public void onComplete(List<BrandModel> list) {
                Log.d("-==DEBUG==-","Brand list updated");
                //brandListVector = list;
                brandListVector.clear();
                brandListVector.addAll(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancel() {

            }
        });



        /**
         * setting search listener
         */
        /*
        this.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("TAG","Brand list fragment search bar onQueryTextSubmit: "+query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("TAG","Brand list fragment search bar onQueryTextChange: " + newText);
                return false;
            }
        });

*/
        /**
         * Setting on item click listener
         */
        //TODO: later on when firebase model is complete use adapter.notifyDataSetChanged() when new elements will update
        // in firebase
        this.brandList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**
                 *
                 * calling mListener to notify HomeActivity to change view to ItemList fragment that belongs to
                 * brandListVector.get(position)
                 */
                Log.d("TAG","clicked on position " + brandListVector.get(position).toString());
                BrandModel brand = brandListVector.get(position);
                mListener.onBrandSelected(brand.getName() , userEmail);

            }
        });


        return contentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBrandListListener) {
            mListener = (OnBrandListListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBrandListListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnBrandListListener {
        void onBrandSelected(String brandName , String userEmail);
    }

    /**
     * List adapter
     */
    class BrandListAdapter extends BaseAdapter{
        LayoutInflater inflater = getActivity().getLayoutInflater();
        @Override
        public int getCount() {
            return brandListVector.size();
        }

        @Override
        public Object getItem(int position) {
            return brandListVector.get(position);
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
            BrandModel brand = brandListVector.get(position);
            final ImageView brandImage;

            if(convertView == null){
                /**
                 * getting fragment inflater
                 */
                convertView = inflater.inflate(R.layout.brand_item,null);
            }

            TextView brandName = (TextView) convertView.findViewById(R.id.brand_item_name);
             brandImage =(ImageView) convertView.findViewById(R.id.brand_item_picture);
            TextView brandDescription = (TextView) convertView.findViewById(R.id.brand_item_description);
            final ProgressBar spinner = (ProgressBar) convertView.findViewById(R.id.brand_item_spinner);
            brandName.setText(brand.getName());
            brandDescription.setText(brand.getDescription());
            brandImage.setTag(position);
            Log.d("-==DEBUG==-","getView was called for position: "+position);


            ImageManager imageman= new ImageManager();
            imageman.loadImageFromCache(brand.getImage(), new ImageManager.GetImageListener() {
                @Override
                public void onSuccess(Bitmap image) {

                    brandImage.setImageBitmap(image);
                    spinner.setVisibility(View.GONE);
                    // notifyDataSetChanged();
                }

                @Override
                public void onFail() {

                }
            });


            return convertView;
        }
    }
}
