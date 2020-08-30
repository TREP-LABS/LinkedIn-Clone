package com.app.treplabs.linkedinclone.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.app.treplabs.linkedinclone.models.UserEducation;
import com.app.treplabs.linkedinclone.models.UserExperience;
import com.app.treplabs.linkedinclone.models.UserSkill;
import com.app.treplabs.linkedinclone.repositories.UserProfileRepository;
import java.util.List;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<List<UserExperience>> mUserExperienceLD = new MutableLiveData<>();
    private MutableLiveData<List<UserEducation>> mUserEducationLD = new MutableLiveData<>();
    private MutableLiveData<List<UserSkill>> mUserSkillLD = new MutableLiveData<>();

    public ProfileViewModel() {
        mUserExperienceLD.setValue(UserProfileRepository.getInstance().getUserExperiences());
        mUserEducationLD.setValue(UserProfileRepository.getInstance().getUserEducations());
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
