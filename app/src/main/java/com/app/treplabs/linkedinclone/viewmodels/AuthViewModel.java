package com.app.treplabs.linkedinclone.viewmodels;

import android.util.Log;
import android.view.View;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.app.treplabs.linkedinclone.interfaces.AuthStateListener;
import com.app.treplabs.linkedinclone.repositories.UserRepository;
import java.util.HashMap;

public class AuthViewModel extends ViewModel {
    public String mFirstName;
    public String mLastName;
    public String mUserEmail;
    public String mUserPassword;
    private AuthStateListener mAuthStateListener;

    public void setAuthStateListener(AuthStateListener authStateListener) {
        mAuthStateListener = authStateListener;
    }

    public void onLoginButtonClicked(View view) {
        if (!isEmailValid(mUserEmail)) {
            mAuthStateListener.onFailure("Error in Email");
            return;
        }else if (!isPasswordValid(mUserPassword)){
            mAuthStateListener.onFailure("Error in Password");
            return;
        }
        mAuthStateListener.onFailure("No errors");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("email", mUserEmail.trim());
        hashMap.put("password", mUserPassword.trim());

        LiveData<String> mLoginResponse = UserRepository.getInstance().logUserIn(hashMap);
        mAuthStateListener.onSuccess(mLoginResponse);
    }

    public void onJoinNowButtonClicked(View view) {
        if (!isNameValid(mFirstName)) {
            mAuthStateListener.onFailure("Error in First name");
            return;
        } else if (!isNameValid(mLastName)) {
            mAuthStateListener.onFailure("Error in Last name");
            return;
        } else if (!isEmailValid(mUserEmail)) {
            mAuthStateListener.onFailure("Error in Email");
            return;
        } else if (!isPasswordValid(mUserPassword)) {
            mAuthStateListener.onFailure("Error in Password");
            return;
        }
        mAuthStateListener.onFailure("No errors");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("firstname", mFirstName.trim());
        hashMap.put("lastname", mLastName.trim());
        hashMap.put("email", mUserEmail.trim());
        hashMap.put("password", mUserPassword);

        LiveData<String> mSignUpResponse = UserRepository.getInstance().signUserUp(hashMap);
        Log.d("AuthViewModel", "onLoginButtonClicked: " + mSignUpResponse.getValue());
        mAuthStateListener.onSuccess(mSignUpResponse);
    }

    private boolean isEmailValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if (email == null || email.isEmpty())
            return false;
        else
            return email.matches(regex);
    }

    private boolean isPasswordValid(String password) {
        if (password == null || password.length() < 6)
            return false;
        else
            return true;
    }

    private boolean isNameValid(String name) {
        if (name == null || name.length() < 2)
            return false;
        else
            return true;
    }
}
