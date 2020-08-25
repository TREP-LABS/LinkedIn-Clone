package com.app.treplabs.linkedinclone.models;

public class User {
    private static User instance = null;
    private String mFirstName;
    private String mLastName;

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    private String mEmail;

    private User(){}

    public static User getInstance(){
        if (instance == null){
            instance = new User();
        }
        return instance;
    }
}
