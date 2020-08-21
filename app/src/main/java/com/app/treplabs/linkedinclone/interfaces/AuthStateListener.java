package com.app.treplabs.linkedinclone.interfaces;

import androidx.lifecycle.LiveData;

public interface AuthStateListener {
    void onStarted();
    void onSuccess(LiveData<String> loginResponse);
    void onFailure(String message);
}
