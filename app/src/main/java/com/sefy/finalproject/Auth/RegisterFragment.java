package com.sefy.finalproject.Auth;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.sefy.finalproject.R;

public class RegisterFragment extends Fragment {
    private EditText email,password,firstName,lastName;
    private Button register, login;
    private OnRegisterListener mListener;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
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
        View contentView = inflater.inflate(R.layout.fragment_register, container, false);
        this.email = (EditText) contentView.findViewById(R.id.register_fragment_email);
        this.password = (EditText) contentView.findViewById(R.id.register_fragment_password);
        this.firstName = (EditText) contentView.findViewById(R.id.register_fragment_firstName);
        this.lastName = (EditText) contentView.findViewById(R.id.register_fragment_lastName);
        this.register = (Button) contentView.findViewById(R.id.register_fragment_submit);
        this.login = (Button) contentView.findViewById(R.id.register_fragment_login);

        this.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();
                String userFirstName = firstName.getText().toString();
                String userLastName = lastName.getText().toString();
                mListener.onSubmitRegister(userFirstName,userLastName,userEmail,userPassword);

            }
        });

        this.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onLoginButtonClicked();
            }
        });
        return contentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRegisterListener) {
            mListener = (OnRegisterListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRegisterListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnRegisterListener {
        void onSubmitRegister(String firstName, String lastName, String email, String password);
        void onLoginButtonClicked();
    }
}
