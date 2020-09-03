package com.app.treplabs.linkedinclone.interfaces;

public interface ProfileStateListener {
    void onGetProfileFailure(String message);
    void onGetProfileSuccess(String s);
}
