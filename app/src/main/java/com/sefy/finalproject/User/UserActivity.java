package com.sefy.finalproject.User;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sefy.finalproject.HomeActivity;
import com.sefy.finalproject.Model.UserManager;
import com.sefy.finalproject.Model.UserModel;
import com.sefy.finalproject.R;

public class UserActivity extends Activity {
    private Bundle userDetails;
    private UserModel user;
    private EditText firstName, lastName, password;
    private TextView email;
    private Button saveChanges;
    private boolean firstNameChanged, passChanged, lastNameChanged;
    private static UserManager userManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userManager = new UserManager();

        setContentView(R.layout.activity_user);
        this.firstNameChanged = false;
        this.lastNameChanged = false;
        this.passChanged = false;
        this.userDetails = getIntent().getExtras();
        this.user = new UserModel(this.userDetails.getString("userFirstName"),
                                  this.userDetails.getString("userLastName"),
                                  this.userDetails.getString("userEmail"),
                                  this.userDetails.getString("userPassword"));

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
                String fName = firstName.getText().toString();
                String lName = lastName.getText().toString();
                String mail = email.getText().toString();
                String pass = password.getText().toString();
                if(fName.matches("") || lName.matches("") || mail.matches("")|| pass.matches("")){
                    Toast.makeText(UserActivity.this,"Make sure that all fields are filled",Toast.LENGTH_LONG).show();
                }
                else{
                    if(userManager.add(new UserModel(fName,lName,mail,pass))){
                        Toast.makeText(UserActivity.this,"User details changed successfully",Toast.LENGTH_LONG).show();

                    }
                    Toast.makeText(UserActivity.this,"Error occurred while trying to update profile",Toast.LENGTH_LONG).show();

                }
            }
        });
        this.setEditTextChangeListeners();
    }

    /*-----------------------------------------------*/
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
                    finish();
                }
                break;
                default:{
                    return super.onOptionsItemSelected(item);

                }
            }
        return true;
    }

    private void setEditTextChangeListeners(){
        this.firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
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
