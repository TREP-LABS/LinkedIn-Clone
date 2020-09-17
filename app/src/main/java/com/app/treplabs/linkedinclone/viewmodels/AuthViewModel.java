package com.app.treplabs.linkedinclone.viewmodels;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.view.View;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.app.treplabs.linkedinclone.interfaces.AuthStateListener;
import com.app.treplabs.linkedinclone.repositories.UserRepository;
import java.util.HashMap;

public class AuthViewModel extends ViewModel {
    public String mFirstName;
    public String mLastName;
    public String mUserEmail;
    public String mUserPassword;
    public String mResetEmail;
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

        @SuppressLint("StaticFieldLeak")
        AsyncTask<HashMap<String, String>, Void, String> task =
                new AsyncTask<HashMap<String, String>, Void, String>() {
                    @Override
                    protected void onPreExecute() {
                        mAuthStateListener.onStarted();
                    }
                    @Override
                    protected String doInBackground(HashMap<String, String>... hashMaps) {
                        return UserRepository.getInstance().logUserIn(hashMaps[0]);
                    }
                    @Override
                    protected void onPostExecute(String string) {
                        if (string.contains("success"))
                            mAuthStateListener.onSuccess(string);
                        else
                            mAuthStateListener.onFailure(string);
                    }

                };
        task.execute(hashMap);
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

        @SuppressLint("StaticFieldLeak")
        AsyncTask<HashMap<String, String>, Void, String> task =
                new AsyncTask<HashMap<String, String>, Void, String>() {
                    @Override
                    protected void onPreExecute() {
                        mAuthStateListener.onStarted();
                    }
                    @Override
                    protected String doInBackground(HashMap<String, String>... hashMaps) {
                        return UserRepository.getInstance().signUserUp(hashMaps[0]);
                    }
                    @Override
                    protected void onPostExecute(String string) {
                        if (string.contains("success"))
                            mAuthStateListener.onSuccess(string);
                        else
                            mAuthStateListener.onFailure(string);
                    }

                };
        task.execute(hashMap);
    }

    public void onResetButtonClicked(View view){
        if (!isEmailValid(mResetEmail)){
            mAuthStateListener.onFailure("Error in Email");
            return;
        }
        mAuthStateListener.onFailure("No errors");
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("email", mResetEmail.trim());

        @SuppressLint("StaticFieldLeak")
        AsyncTask<HashMap<String, String>, Void, String> task =
                new AsyncTask<HashMap<String, String>, Void, String>() {
                    @Override
                    protected void onPreExecute() {
                        mAuthStateListener.onStarted();
                    }
                    @Override
                    protected String doInBackground(HashMap<String, String>... hashMaps) {
                        return UserRepository.getInstance().resetUserPassword(hashMaps[0]);
                    }
                    @Override
                    protected void onPostExecute(String string) {
                        if (string.contains("success"))
                            mAuthStateListener.onSuccess(string);
                        else
                            mAuthStateListener.onFailure(string);
                    }

                };
        task.execute(hashMap);

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
