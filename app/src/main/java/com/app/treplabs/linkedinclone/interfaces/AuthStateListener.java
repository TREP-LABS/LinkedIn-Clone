package com.app.treplabs.linkedinclone.interfaces;

public interface AuthStateListener {
    void onStarted();
    void onSuccess(String loginResponse);
    void onFailure(String message);
}
