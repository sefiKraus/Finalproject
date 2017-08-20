package com.sefy.finalproject.Auth;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.sefy.finalproject.HomeActivity;
import com.sefy.finalproject.Model.UserModel;
import com.sefy.finalproject.R;

import java.util.Vector;

public class AuthActivity extends Activity implements RegisterFragment.OnRegisterListener, LoginFragment.OnLoginListener {

    private Vector<UserModel> usersList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        this.usersList = new Vector<>();
        this.usersList.add(new UserModel("sefi","krausz","sefi@gmail.com","123456789"));
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
        if(this.login(userEmail,userPassword)){
            Intent homeActivity =  new Intent(getApplicationContext(), HomeActivity.class);
            Bundle bundle = new Bundle();
            UserModel user = this.getUserFromList(userEmail);
            bundle.putString("userFirstName", user.getFirstName());
            bundle.putString("userLastName", user.getLastName());
            bundle.putString("userEmail", user.getEmail());
            bundle.putString("userPassword", user.getPassword());
            homeActivity.putExtras(bundle);
            startActivity(homeActivity);
        }
        else{
            Log.d("Tag","Error occurred during login");

        }
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
        if(this.register(firstName,lastName,email,password)){
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            LoginFragment loginFragment = LoginFragment.newInstance();
            fragmentTransaction.replace(R.id.auth_frag_container,loginFragment);
            fragmentTransaction.addToBackStack("GoToLoginPage");
            fragmentTransaction.commit();
        }
        else{
            Log.d("TAG","Email already exists");
        }
    }

    @Override
    public void onLoginButtonClicked() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        LoginFragment loginFragment = LoginFragment.newInstance();
        fragmentTransaction.replace(R.id.auth_frag_container,loginFragment);
        fragmentTransaction.addToBackStack("GoToLoginPage");
        fragmentTransaction.commit();
    }
/*--------------------------"Move to FireBase"----------------------------------*/


    /**
     *
     * @param email
     * @param password
     * @return
     */
    private Boolean authenticateUser(String email, String password){
       UserModel tempUser = this.getUserFromList(email);
        if(tempUser!=null){
            if(tempUser.getPassword().equals(password)){
                return true;
            }
            else{
                return false;
            }
        }
        return false;
    }

    /**
     * Checks if email already exists in database
     * @param email
     * @return
     */
    private Boolean isUserExists(String email){
        for(int i = 0; i < this.usersList.size(); i++){
            if(this.usersList.get(i).getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    /**
     * Get user from list
     * @param email
     * @return
     */
    private UserModel getUserFromList(String email){
        for(int i = 0; i < this.usersList.size(); i++){
            if(this.usersList.get(i).getEmail().equals(email)){
                return this.usersList.get(i);
            }
        }
        return null;
    }

    /**
     * Register method
     * @param firstName
     * @param lastName
     * @param email
     * @param password
     * @return
     */
    private Boolean register(String firstName, String lastName , String email, String password){
        if(this.isUserExists(email)){
            return false;
        }
        else{
            UserModel user = new UserModel(firstName,lastName,email,password);
            this.usersList.add(user);
            return true;
        }
    }

    /**
     * Login method
     * @param email
     * @param password
     * @return
     */
    private Boolean login(String email, String password){
        if(this.authenticateUser(email, password)){
            return true;
        }
        return false;
    }

}
