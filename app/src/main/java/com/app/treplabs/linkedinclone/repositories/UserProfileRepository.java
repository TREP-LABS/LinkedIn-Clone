package com.app.treplabs.linkedinclone.repositories;

import com.app.treplabs.linkedinclone.models.UserExperience;

import java.util.ArrayList;
import java.util.List;

public class UserProfileRepository {
    private List<UserExperience> mUserExperiences = new ArrayList<>();
    private static UserProfileRepository instance;

    public static UserProfileRepository getInstance(){
        if (instance == null){
            instance = new UserProfileRepository();
            instance.initializeSampleExperience();
        }
        return instance;
    }

    private UserProfileRepository(){}

    private void initializeSampleExperience(){
        mUserExperiences.add(new UserExperience("UI/UX Designer", "WhatsApp",
                "Jun 2007 - July 2017", 10));
        mUserExperiences.add(new UserExperience("Android Developer", "Apple",
                "Jun 2018 - July 2028", 10));
        mUserExperiences.add(new UserExperience("BackEnd Engineer", "LinkedIn",
                "Jun 2029 - July 2039", 10));
    }

    public List<UserExperience> getUserExperiences() {
        return mUserExperiences;
    }
}
