package com.sefy.finalproject.ErrorHandler;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sefy.finalproject.R;

public class ErrorHandlerFragment extends Fragment {

    private static final String ERROR_MESSAGE = "Error_Message";
    // TODO: Rename and change types of parameters
    private String errorMessage;

    private OnFragmentInteractionListener mListener;

    public ErrorHandlerFragment() {
        // Required empty public constructor
    }

    public static ErrorHandlerFragment newInstance(String errorMessage) {
        ErrorHandlerFragment fragment = new ErrorHandlerFragment();
        Bundle args = new Bundle();
        args.putString(ERROR_MESSAGE, errorMessage);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            errorMessage = getArguments().getString(ERROR_MESSAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_error_handler, container, false);
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
        void onDisplayErrorMessage();
    }
}
