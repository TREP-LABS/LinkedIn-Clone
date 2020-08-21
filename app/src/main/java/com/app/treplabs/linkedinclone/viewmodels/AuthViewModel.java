package com.app.treplabs.linkedinclone.viewmodels;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.app.treplabs.linkedinclone.interfaces.AuthStateListener;
import com.app.treplabs.linkedinclone.network.BackEndApiConnection;
import com.app.treplabs.linkedinclone.repositories.UserRepository;

import java.util.HashMap;

import retrofit2.Retrofit;

public class AuthViewModel extends ViewModel {
    public String mUserEmail;
    public String mUserPassword;
    private AuthStateListener mAuthStateListener;

    public void setAuthStateListener(AuthStateListener authStateListener) {
        mAuthStateListener = authStateListener;
    }

    public void onLoginButtonClicked(View view){
        mAuthStateListener.onStarted();
        if (mUserEmail == null || mUserPassword == null || mUserEmail.isEmpty() || mUserPassword.isEmpty()){
            mAuthStateListener.onFailure("Invalid email or password");
            return;
        }
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("email", mUserEmail);
        hashMap.put("password", mUserPassword);

        LiveData<String> mLoginResponse = new UserRepository().logUserIn(hashMap);
        Log.d("AuthViewModel", "onLoginButtonClicked: " + mLoginResponse.toString());
        mAuthStateListener.onSuccess(mLoginResponse);
    }
}
