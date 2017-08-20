package com.sefy.finalproject.Auth;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.sefy.finalproject.R;

public class LoginFragment extends Fragment {
    //TODO: set email pattern validator and password pattern
    private OnLoginListener mListener;
    private EditText email,password;
    private Button login,register;

    public LoginFragment() {
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView= inflater.inflate(R.layout.fragment_login, container, false);
        this.email = (EditText) contentView.findViewById(R.id.login_fragment_email);
        this.password = (EditText) contentView.findViewById(R.id.login_fragment_password);

        this.login = (Button) contentView.findViewById(R.id.login_fragment_submit);
        this.register = (Button) contentView.findViewById(R.id.login_fragment_register_page);

        /**
         * setting listeners
         */
        this.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();
                mListener.onSubmitLogin(userEmail,userPassword);
            }
        });
        this.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickRegisterButton();
            }
        });
        return contentView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLoginListener) {
            mListener = (OnLoginListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLoginListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnLoginListener {
        void onSubmitLogin(String userEmail, String userPassword);
        void onClickRegisterButton();
    }
}
