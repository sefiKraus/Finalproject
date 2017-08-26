package com.sefy.finalproject.Auth;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.sefy.finalproject.HomeActivity;
import com.sefy.finalproject.Model.UserManager;
import com.sefy.finalproject.Model.UserModel;
import com.sefy.finalproject.R;

import java.util.regex.Pattern;

public class AuthActivity extends Activity implements RegisterFragment.OnRegisterListener, LoginFragment.OnLoginListener {

    private UserManager userManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
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


    @Override
    public void onSubmitLogin(String userEmail, String userPassword) {
        this.login(userEmail,userPassword);

    }

    @Override
    public void onClickRegisterButton() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        RegisterFragment registerFragment = RegisterFragment.newInstance();
        fragmentTransaction.replace(R.id.auth_frag_container,registerFragment);
        fragmentTransaction.addToBackStack("GoToRegisterPage");
        fragmentTransaction.commit();
    }

    @Override
    public void onSubmitRegister(String firstName, String lastName, String email, String password) {
        this.register(firstName,lastName,email,password);
    }

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
            this.userManager.getUserDB(email, new UserManager.GetUserCallback() {
                @Override
                public void onComplete(UserModel user) {
                    if(user == null){
                        UserModel newUser = new UserModel(firstName,lastName,email,password);
                        if(userManager.addUserDB(newUser)) {
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            LoginFragment loginFragment = LoginFragment.newInstance();
                            fragmentTransaction.replace(R.id.auth_frag_container, loginFragment);
                            fragmentTransaction.addToBackStack("GoToLoginPage");
                            fragmentTransaction.commit();
                        }
                    }
                    else{
                        Fragment currentFragment = getFragmentManager().findFragmentById(R.id.auth_frag_container);
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

            this.userManager.getUserDB(email, new UserManager.GetUserCallback() {
                @Override
                public void onComplete(UserModel user) {
                    if(user == null){
                        Toast.makeText(AuthActivity.this,"Error occurred please verify email and password",Toast.LENGTH_LONG).show();
                    }else{
                        if(!user.getPassword().equals(password)){
                            Toast.makeText(AuthActivity.this,"Error occurred please verify email and password",Toast.LENGTH_LONG).show();
                        }else{
                             Intent homeActivity =  new Intent(getApplicationContext(), HomeActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("userFirstName", user.getFirstName());
                            bundle.putString("userLastName", user.getLastName());
                            bundle.putString("userEmail", user.getEmail());
                            bundle.putString("userPassword", user.getPassword());
                            homeActivity.putExtras(bundle);
                            startActivity(homeActivity);
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

    private boolean validEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

}
