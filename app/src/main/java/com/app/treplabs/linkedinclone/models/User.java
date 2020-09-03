package com.app.treplabs.linkedinclone.models;

public class User {
    private static User instance = null;
    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private String mUserId;
    private String mSlug;
    private String mToken;

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

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getSlug() {
        return mSlug;
    }

    public void setSlug(String slug) {
        mSlug = slug;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }

    private User(){}

    public static User getInstance(){
        if (instance == null){
            instance = new User();
        }
        return instance;
    }
}
