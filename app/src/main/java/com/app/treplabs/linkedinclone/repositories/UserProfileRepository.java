package com.app.treplabs.linkedinclone.repositories;

import com.app.treplabs.linkedinclone.models.UserEducation;
import com.app.treplabs.linkedinclone.models.UserExperience;

import java.util.ArrayList;
import java.util.List;

public class UserProfileRepository {
    private List<UserExperience> mUserExperiences = new ArrayList<>();

    private List<UserEducation> mUserEducations = new ArrayList<>();
    private static UserProfileRepository instance;

    public static UserProfileRepository getInstance(){
        if (instance == null){
            instance = new UserProfileRepository();
            instance.initializeSampleExperience();
            instance.initializeSampleEducation();
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

    private void initializeSampleEducation(){
        mUserEducations.add(new UserEducation("University of ABC", "BSc. CDE",
                "2000 - 2001"));
        mUserEducations.add(new UserEducation("ABC High School", "High School Cert",
                "1999 - 2000"));
    }

    public List<UserEducation> getUserEducations() {
        return mUserEducations;
    }

    public List<UserExperience> getUserExperiences() {
        return mUserExperiences;
    }
}
