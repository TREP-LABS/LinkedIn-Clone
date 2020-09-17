package com.app.treplabs.linkedinclone.viewmodels;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.app.treplabs.linkedinclone.interfaces.ProfileStateListener;
import com.app.treplabs.linkedinclone.models.UserEducation;
import com.app.treplabs.linkedinclone.models.UserExperience;
import com.app.treplabs.linkedinclone.models.UserSkill;
import com.app.treplabs.linkedinclone.repositories.UserProfileRepository;

import java.util.ArrayList;
import java.util.List;

public class ProfileViewModel extends ViewModel {
    private ProfileStateListener mProfileStateListener;
    private List<UserExperience> mUserExperiences = new ArrayList<>();
    private List<UserEducation> mUserEducations = new ArrayList<>();
    private List<UserSkill> mUserSkills = new ArrayList<>();

    public void setProfileStateListener(ProfileStateListener profileStateListener) {
        mProfileStateListener = profileStateListener;
    }

    public void getFullProfileFromRepo(String userId) {
        if (userId == null || userId.isEmpty()) {
            mProfileStateListener.onGetProfileFailure("No user is logged in");
            return;
        }
        @SuppressLint("StaticFieldLeak")
        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String userId = strings[0];
                return UserProfileRepository.getInstance().getFullProfile(userId);
            }

            @Override
            protected void onPostExecute(String s) {
                mUserEducations = UserProfileRepository.getInstance().getUserEducations();
                mProfileStateListener.onGetProfileSuccess(s);
            }
        };
        task.execute(userId);
    }

    public ProfileViewModel() {
        mUserExperiences = UserProfileRepository.getInstance().getUserExperiences();
        mUserSkills = UserProfileRepository.getInstance().getUserSkills();
    }

    public List<UserExperience> getUserExperiences() {
        return mUserExperiences;
    }

    public List<UserEducation> getUserEducations() {
        return mUserEducations;
    }

    public List<UserSkill> getUserSkills() {
        return mUserSkills;
    }
}
