package com.sefy.finalproject.Auth;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import com.sefy.finalproject.R;

public class AuthActivity extends Activity implements RegisterFragment.OnFragmentInteractionListener, LoginFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        LoginFragment loginFragment = LoginFragment.newInstance();
        /**
         * setting transaction in order to display register fragment
         */
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.auth_frag_container,loginFragment);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction() {

    }

    @Override
    public void onSubmitLogin(String userEmail, String userPassword) {
        Log.d("TAG","Auth activity recieved: "+ userEmail+" "+userPassword);
    }

    @Override
    public void onClickRegisterButton() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        RegisterFragment registerFragment = RegisterFragment.newInstance();
        fragmentTransaction.replace(R.id.auth_frag_container,registerFragment);
        fragmentTransaction.commit();
    }
}
