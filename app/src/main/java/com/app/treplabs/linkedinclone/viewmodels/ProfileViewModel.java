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
import java.util.List;

public class ProfileViewModel extends ViewModel {
    private ProfileStateListener mProfileStateListener;
    private MutableLiveData<List<UserExperience>> mUserExperienceLD = new MutableLiveData<>();
    private MutableLiveData<List<UserEducation>> mUserEducationLD = new MutableLiveData<>();
    private MutableLiveData<List<UserSkill>> mUserSkillLD = new MutableLiveData<>();

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
                mUserEducationLD.setValue(UserProfileRepository.getInstance().getUserEducations());
                mProfileStateListener.onGetProfileSuccess(s);
            }
        };
        task.execute(userId);
    }

    public ProfileViewModel() {
        mUserExperienceLD.setValue(UserProfileRepository.getInstance().getUserExperiences());
        mUserSkillLD.setValue(UserProfileRepository.getInstance().getUserSkills());
    }

    public LiveData<List<UserExperience>> getUserExperienceLD() {
        return mUserExperienceLD;
    }

    public LiveData<List<UserEducation>> getUserEducationLD() {
        return mUserEducationLD;
    }

    public LiveData<List<UserSkill>> getUserSkillLD() {
        return mUserSkillLD;
    }
}
