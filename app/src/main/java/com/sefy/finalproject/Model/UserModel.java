package com.sefy.finalproject.Model;

/**
 * Created by sefy1 on 04/08/2017.
 */

public class UserModel {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String id;

    public UserModel() {
    }

    public UserModel(String firstName, String lastName, String email, String password, String id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.id = id;
    }





    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
