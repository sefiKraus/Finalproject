package com.sefy.finalproject.User;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sefy.finalproject.HomeActivity;
import com.sefy.finalproject.Model.UserModel;
import com.sefy.finalproject.R;

public class UserActivity extends Activity {
    private Bundle userDetails;
    private UserModel user;
    private EditText firstName, lastName, password;
    private TextView email;
    private Button saveChanges;
    private boolean firstNameChanged, passChanged, lastNameChanged;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        this.firstNameChanged = false;
        this.lastNameChanged = false;
        this.passChanged = false;
        this.userDetails = getIntent().getExtras();
        this.user = new UserModel(this.userDetails.getString("userFirstName"),
                                  this.userDetails.getString("userLastName"),
                                  this.userDetails.getString("userEmail"),
                                  this.userDetails.getString("userPassword"));

        Log.d("TAG","User Activity received user details: "+ this.user.toString());
        this.email = (TextView) findViewById(R.id.user_details_email);
        this.firstName = (EditText) findViewById(R.id.user_details_firstName);
        this.lastName = (EditText) findViewById(R.id.user_details_lastName);
        this.password = (EditText) findViewById(R.id.user_details_password);

        this.email.setText(this.user.getEmail());
        this.password.setText(this.user.getPassword());
        this.firstName.setText(this.user.getFirstName());
        this.lastName.setText(this.user.getLastName());
        this.saveChanges = (Button) findViewById(R.id.user_details_saveChanges);
        this.saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG","UserActivity Save change in database!!!!");
            }
        });
        this.setEditTextChangeListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.user_actionbar,menu);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
            switch (itemId){
                case android.R.id.home:{
                    Intent homeActivity =  new Intent(getApplicationContext(), HomeActivity.class);
                    homeActivity.putExtras(this.userDetails);
                    startActivity(homeActivity);
                }
                break;
                case R.id.user_actionbar_edit:{
                    Log.d("TAG","Navigate to edit profile");

                }
                break;
                default:{
                    return super.onOptionsItemSelected(item);

                }
            }
        return true;
    }

    private void setEditTextChangeListeners(){
        //TODO:Fix bug!!
        this.firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("TAG",s.toString());
                if(!s.toString().matches(user.getFirstName())|| passChanged || lastNameChanged){
                    firstNameChanged = true;
                    saveChanges.setVisibility(View.VISIBLE);
                }
                else{
                    firstNameChanged = false;
                    saveChanges.setVisibility(View.INVISIBLE);
                }
            }
        });

        this.lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().matches(user.getLastName())|| passChanged || firstNameChanged){
                    lastNameChanged = true;
                    saveChanges.setVisibility(View.VISIBLE);
                }
                else{
                    lastNameChanged = false;
                    saveChanges.setVisibility(View.INVISIBLE);
                }
            }
        });

        this.password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().matches(user.getPassword())|| firstNameChanged || lastNameChanged){
                    passChanged = true;
                    saveChanges.setVisibility(View.VISIBLE);
                }
                else{
                    passChanged = false;
                    saveChanges.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

}
