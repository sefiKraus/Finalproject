package com.sefy.finalproject.Auth;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sefy.finalproject.HomeActivity;
import com.sefy.finalproject.Model.ItemManager;
import com.sefy.finalproject.Model.ItemModel;
import com.sefy.finalproject.Model.UserManager;
import com.sefy.finalproject.Model.UserModel;
import com.sefy.finalproject.R;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class AuthActivity extends Activity implements RegisterFragment.OnRegisterListener, LoginFragment.OnLoginListener {

    private UserManager userManager;
    private ProgressBar spinner;

    /**
     *  OnCreate
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        this.spinner = (ProgressBar) findViewById(R.id.auth_spinner);
        userManager = new UserManager();

               LoginFragment loginFragment = LoginFragment.newInstance();
        /**
         * setting transaction in order to display register fragment
         */
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.auth_frag_container,loginFragment);
        transaction.addToBackStack("");
        transaction.commit();
    }

    /**
     * onSubmitLogin using login method that will handle verifying the user details
     * @param userEmail
     * @param userPassword
     */
    @Override
    public void onSubmitLogin(String userEmail, String userPassword) {
        this.login(userEmail,userPassword);

    }

    /**
     * Change view from login page to register page
     */
    @Override
    public void onClickRegisterButton() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        RegisterFragment registerFragment = RegisterFragment.newInstance();
        fragmentTransaction.replace(R.id.auth_frag_container,registerFragment);
        fragmentTransaction.addToBackStack("GoToRegisterPage");
        fragmentTransaction.commit();
    }

    /**
     * onSubmitRegister using register method that will handle register the user
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     */
    @Override
    public void onSubmitRegister(String firstName, String lastName, String email, String password) {
        this.register(firstName,lastName,email,password);
    }

    /**
     * Change view from register page to login page
     */
    @Override
    public void onLoginButtonClicked() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        LoginFragment loginFragment = LoginFragment.newInstance();
        fragmentTransaction.replace(R.id.auth_frag_container,loginFragment);
        fragmentTransaction.addToBackStack("GoToLoginPage");
        fragmentTransaction.commit();
    }


    /**
     * Register method
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     * @return
     */
    private void register(final String firstName, final String lastName , final String email, final String password){
        if(!this.validEmail(email)){
            Toast.makeText(this,"Enter valid e-mail!",Toast.LENGTH_LONG).show();
        }
        else{
            spinner.setVisibility(View.VISIBLE);
            this.userManager.getUserDB(email, new UserManager.GetUserCallback() {
                @Override
                public void onComplete(UserModel user) {
                    if(user == null){
                        UserModel newUser = new UserModel(firstName,lastName,email,password);
                        if(userManager.addUserDB(newUser)) {
                            spinner.setVisibility(View.GONE);
                            Toast.makeText(AuthActivity.this,"Register successfully !!!",Toast.LENGTH_LONG).show();
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            LoginFragment loginFragment = LoginFragment.newInstance();
                            fragmentTransaction.replace(R.id.auth_frag_container, loginFragment);
                            fragmentTransaction.addToBackStack("GoToLoginPage");
                            fragmentTransaction.commit();
                        }
                    }
                    else{
                        spinner.setVisibility(View.GONE);
                        Toast.makeText(AuthActivity.this,"Email already in use !!!",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancel() {

                }
            });
        }

    }

    /**
     * Login method
     * @param email
     * @param password
     * @return
     */
    private void login(String email, final String password){
        if(validEmail(email))
        {
            spinner.setVisibility(View.VISIBLE);
            this.userManager.getUserDB(email, new UserManager.GetUserCallback() {
                @Override
                public void onComplete(UserModel user) {
                    if(user == null){
                        spinner.setVisibility(View.GONE);
                        Toast.makeText(AuthActivity.this,"Error occurred please verify email and password",Toast.LENGTH_LONG).show();
                    }else{
                        if(!user.getPassword().equals(password)){
                            spinner.setVisibility(View.GONE);
                            Toast.makeText(AuthActivity.this,"Error occurred please verify email and password",Toast.LENGTH_LONG).show();
                        }else{
                             Intent homeActivity =  new Intent(getApplicationContext(), HomeActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("userFirstName", user.getFirstName());
                            bundle.putString("userLastName", user.getLastName());
                            bundle.putString("userEmail", user.getEmail());
                            bundle.putString("userPassword", user.getPassword());
                            homeActivity.putExtras(bundle);
                            spinner.setVisibility(View.GONE);
                            startActivity(homeActivity);
                            finish();
                        }
                    }
                }

                @Override
                public void onCancel() {

                }
            });
        }
        else{
            Toast.makeText(this,"Enter valid e-mail!",Toast.LENGTH_LONG).show();

        }
    }

    /**
     * validating email pattern
     * @param email
     * @return
     */
    private boolean validEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

}
