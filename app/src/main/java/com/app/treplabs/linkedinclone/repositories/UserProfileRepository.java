package com.app.treplabs.linkedinclone.repositories;

import android.util.Log;

import com.app.treplabs.linkedinclone.models.User;
import com.app.treplabs.linkedinclone.models.UserEducation;
import com.app.treplabs.linkedinclone.models.UserExperience;
import com.app.treplabs.linkedinclone.models.UserSkill;
import com.app.treplabs.linkedinclone.network.BackendProfileApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserProfileRepository {
    private static final String BASE_URL = "http://trep-lc-backend.herokuapp.com/api/v1/profiles/";
    private List<UserExperience> mUserExperiences = new ArrayList<>();
    private List<UserSkill> mUserSkills = new ArrayList<>();
    private List<UserEducation> mUserEducations = new ArrayList<>();
    private static UserProfileRepository instance;
    private String mMessage;

    public static UserProfileRepository getInstance(){
        if (instance == null){
            instance = new UserProfileRepository();
            instance.initializeSampleExperience();
            instance.initializeSampleEducation();
            instance.initializeSampleSkills();
        }
        return instance;
    }

    private UserProfileRepository(){}

    private BackendProfileApi invokeAPI(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BackendProfileApi.class);
    }

    public String getBasicProfile(String userId){
        Call<ResponseBody> call = invokeAPI().getBasicProfile(userId);
        try {
            Response<ResponseBody> result = call.execute();
            if (result.isSuccessful()){
                Log.d("UserProfileRepo", "getBasicProfile: isSuccessful");
                getBasicProfileResponseFromJSON(result.body().string());
            }else {
                Log.d("UserProfileRepo", "getBasicProfile: unSuccessful");
                getBasicProfileResponseFromJSON(result.errorBody().string());
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return mMessage;
    }

    private void getBasicProfileResponseFromJSON(String string) throws JSONException {
        JSONObject parent = new JSONObject(string);
        boolean success = parent.getBoolean("success");
        mMessage = parent.getString("message");
        if (success){
            JSONObject data = parent.getJSONObject("data");
            JSONObject user = data.getJSONObject("user");
            String id = user.getString("id");
            String firstname = user.getString("firstname");
            String lastname = user.getString("lastname");
            String email = user.getString("email");
            String slug = user.getString("slug");

            User.getInstance().setFirstName(firstname);
            User.getInstance().setLastName(lastname);
            User.getInstance().setUserId(id);
            User.getInstance().setEmail(email);
            User.getInstance().setSlug(slug);
        }
    }

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

    private void initializeSampleSkills(){
        mUserSkills.add(new UserSkill("UI Design"));
        mUserSkills.add(new UserSkill("Graphics Design"));
        mUserSkills.add(new UserSkill("Adobe Photoshop"));
        mUserSkills.add(new UserSkill("Adobe Illustrator"));
        mUserSkills.add(new UserSkill("Android"));
        mUserSkills.add(new UserSkill("Web Development"));
    }

    public List<UserEducation> getUserEducations() {
        return mUserEducations;
    }

    public List<UserExperience> getUserExperiences() {
        return mUserExperiences;
    }

    public List<UserSkill> getUserSkills() {
        return mUserSkills;
    }
}
