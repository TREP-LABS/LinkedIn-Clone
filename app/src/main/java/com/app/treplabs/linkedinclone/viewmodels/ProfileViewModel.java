package com.app.treplabs.linkedinclone.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.app.treplabs.linkedinclone.models.UserEducation;
import com.app.treplabs.linkedinclone.models.UserExperience;
import com.app.treplabs.linkedinclone.repositories.UserProfileRepository;
import java.util.List;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<List<UserExperience>> mUserExperienceLD = new MutableLiveData<>();
    private MutableLiveData<List<UserEducation>> mUserEducationLD = new MutableLiveData<>();

    public ProfileViewModel() {
        mUserExperienceLD.setValue(UserProfileRepository.getInstance().getUserExperiences());
        mUserEducationLD.setValue(UserProfileRepository.getInstance().getUserEducations());
    }

    public LiveData<List<UserExperience>> getUserExperienceLD() {
        return mUserExperienceLD;
    }

    public MutableLiveData<List<UserEducation>> getUserEducationLD() {
        return mUserEducationLD;
    }
}
