package com.app.treplabs.linkedinclone.repositories;

import android.util.Log;

import com.app.treplabs.linkedinclone.helpers.JSONParser;
import com.app.treplabs.linkedinclone.models.UserCertificate;
import com.app.treplabs.linkedinclone.models.UserEducation;
import com.app.treplabs.linkedinclone.models.UserExperience;
import com.app.treplabs.linkedinclone.models.UserProfile;
import com.app.treplabs.linkedinclone.models.UserSkill;
import com.app.treplabs.linkedinclone.network.BackendProfileApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserProfileRepository {
    private static final String BASE_URL = "http://trep-lc-backend.herokuapp.com/api/v1/profiles/";
    private List<UserExperience> mUserExperiences = new ArrayList<>();
    private List<UserSkill> mUserSkills = new ArrayList<>();
    private List<UserEducation> mUserEducations = new ArrayList<>();
    private List<UserCertificate> mUserCertificates = new ArrayList<>();
    private UserProfile mUserProfile;
    private static UserProfileRepository instance;
    private String mMessage;
    private JSONParser mJSONParser = new JSONParser();

    public static UserProfileRepository getInstance() {
        if (instance == null) {
            instance = new UserProfileRepository();
        }
        return instance;
    }

    private UserProfileRepository() {
    }

    private BackendProfileApi invokeAPI() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BackendProfileApi.class);
    }

    //profile
    public String getBasicProfile(String userId) {
        Call<ResponseBody> call = invokeAPI().getBasicProfile(userId);
        try {
            Response<ResponseBody> result = call.execute();
            if (result.isSuccessful()) {
                Log.d("UserProfileRepo", "getBasicProfile: isSuccessful");
                mMessage = mJSONParser.getBasicProfileResponseFromJSON(result.body().string());
            } else {
                Log.d("UserProfileRepo", "getBasicProfile: unSuccessful");
                mMessage = mJSONParser.getBasicProfileResponseFromJSON(result.errorBody().string());
            }
        } catch (IOException | JSONException e) {
            mMessage = "An error occurred";
            e.printStackTrace();
        }
        return mMessage;
    }

    public String getFullProfile(String userId) {
        Call<ResponseBody> call = invokeAPI().getFullProfile(userId);
        try {
            Response<ResponseBody> result = call.execute();
            if (result.isSuccessful()) {
                Log.d("UserProfileRepo", "getFullProfile: isSuccessful");
                mMessage = mJSONParser.getFullProfileResponseFromJSON(result.body().string());
                mUserProfile = mJSONParser.mUserProfile;
                mUserEducations = mJSONParser.mUserEducations;
                mUserExperiences = mJSONParser.mUserExperiences;
                mUserSkills = mJSONParser.mUserSkills;
                mUserCertificates = mJSONParser.mUserCertificates;
            } else {
                Log.d("UserProfileRepo", "getFullProfile: unSuccessful");
                mMessage = mJSONParser.getFullProfileResponseFromJSON(result.errorBody().string());
            }
        } catch (IOException | JSONException e) {
            mMessage = "An error occurred";
            e.printStackTrace();
        }
        return mMessage;
    }

    public String add(String whatToAdd, String token, HashMap<String, String> map) {
        Call<ResponseBody> call;
        switch (whatToAdd) {
            case "education":
                call = invokeAPI().addNewEducation(token, map);
                executeAddOperationInBackground(call, whatToAdd);
                mUserEducations = mJSONParser.mUserEducations;
            case "experience":
                call = invokeAPI().addNewExperience(token, map);
                executeAddOperationInBackground(call, whatToAdd);
                mUserExperiences = mJSONParser.mUserExperiences;
                break;
            case "certificate":
                call = invokeAPI().addNewCertificate(token, map);
                executeAddOperationInBackground(call, whatToAdd);
                mUserCertificates = mJSONParser.mUserCertificates;
                break;
            case "skill":
                call = invokeAPI().addNewSkill(token, map);
                executeAddOperationInBackground(call, whatToAdd);
                mUserSkills = mJSONParser.mUserSkills;
                break;
        }
        return mMessage;
    }

    private void executeAddOperationInBackground(Call<ResponseBody> call, String whatToAdd) {
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        Log.d("UserProfileRepo", "add: isSuccessful");
                        switch (whatToAdd) {
                            case "education":
                                mMessage = mJSONParser.getResponseFromEducationRequest(response.body().string());
                                break;
                            case "experience":
                                mMessage = mJSONParser.getResponseFromExperienceRequest(response.body().string());
                                break;
                            case "certificate":
                                mMessage = mJSONParser.getResponseFromCertificateRequest(response.body().string());
                                break;
                            case "skill":
                                mMessage = mJSONParser.getResponseFromSkillRequest(response.body().string());
                                break;
                        }
                    } else {
                        Log.d("UserProfileRepo", "add: Unsuccessful");
                        switch (whatToAdd) {
                            case "education":
                                mMessage = mJSONParser.getResponseFromEducationRequest(response.errorBody().string());
                                break;
                            case "experience":
                                mMessage = mJSONParser.getResponseFromExperienceRequest(response.errorBody().string());
                                break;
                            case "certificate":
                                mMessage = mJSONParser.getResponseFromCertificateRequest(response.errorBody().string());
                                break;
                            case "skill":
                                mMessage = mJSONParser.getResponseFromSkillRequest(response.errorBody().string());
                                break;
                        }
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("UserProfileRepo", "OnFailure: " + t.getMessage());
                mMessage = t.getMessage();
            }
        });
    }

    public String update(String whatToUpdate, String token, String idToUpdate, HashMap<String, String> map) {
        Call<ResponseBody> call;
        switch (whatToUpdate) {
            case "education":
                call = invokeAPI().updateExistingEducation(token, idToUpdate, map);
                executeUpdateInBackground(call, whatToUpdate);
                mUserEducations = mJSONParser.mUserEducations;
                break;
            case "experience":
                call = invokeAPI().updateExistingExperience(token, idToUpdate, map);
                executeUpdateInBackground(call, whatToUpdate);
                mUserExperiences = mJSONParser.mUserExperiences;
                break;
            case "certificate":
                call = invokeAPI().updateExistingCertificate(token, idToUpdate, map);
                executeUpdateInBackground(call, whatToUpdate);
                mUserCertificates = mJSONParser.mUserCertificates;
                break;
        }
        return mMessage;
    }

    private void executeUpdateInBackground(Call<ResponseBody> call, String whatToUpdate) {
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        Log.d("UserProfileRepo", "update: isSuccessful");
                        switch (whatToUpdate) {
                            case "education":
                                mMessage = mJSONParser.getResponseFromEducationRequest(response.body().string());
                                break;
                            case "experience":
                                mMessage = mJSONParser.getResponseFromExperienceRequest(response.body().string());
                                break;
                            case "certificate":
                                mMessage = mJSONParser.getResponseFromCertificateRequest(response.body().string());
                                break;
                        }
                    } else {
                        Log.d("UserProfileRepo", "update: Unsuccessful");
                        switch (whatToUpdate) {
                            case "education":
                                mMessage = mJSONParser.getResponseFromEducationRequest(response.errorBody().string());
                                break;
                            case "experience":
                                mMessage = mJSONParser.getResponseFromExperienceRequest(response.errorBody().string());
                                break;
                            case "certificate":
                                mMessage = mJSONParser.getResponseFromCertificateRequest(response.errorBody().string());
                                break;
                        }
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("UserProfileRepo", "OnFailure: " + t.getMessage());
                mMessage = t.getMessage();
            }
        });
    }

    public String delete(String whatToDelete, String token, String idToDelete) {
        Call<ResponseBody> call;
        switch (whatToDelete) {
            case "education":
                call = invokeAPI().deleteEducation(token, idToDelete);
                executeDeleteOperationInBackground(call, whatToDelete);
                break;
            case "experience":
                call = invokeAPI().deleteExperience(token, idToDelete);
                executeDeleteOperationInBackground(call, whatToDelete);
                break;
            case "certificate":
                call = invokeAPI().deleteCertificate(token, idToDelete);
                executeDeleteOperationInBackground(call, whatToDelete);
                break;
            case "skill":
                call = invokeAPI().deleteSkill(token, idToDelete);
                executeDeleteOperationInBackground(call, whatToDelete);
                break;
        }
        return mMessage;
    }

    private void executeDeleteOperationInBackground(Call<ResponseBody> call, String whatToDelete) {
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        Log.d("UserProfileRepo", "delete: isSuccessful");
                        mMessage = "Deleted " + whatToDelete + " successfully";
                    } else {
                        Log.d("UserProfileRepo", "delete: Unsuccessful");
                        JSONObject parent = new JSONObject(response.errorBody().string());
                        mMessage = parent.getString("message");
                    }
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("UserProfileRepo", "OnFailure: " + t.getMessage());
                mMessage = t.getMessage();
            }
        });
    }

    //skills
    public String searchSkill(String token, String searchText) {
        invokeAPI().searchForSkill(token, searchText)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if (response.isSuccessful()) {
                                Log.d("UserProfileRepo", "searchSkill OnSuccess: ");
                                mMessage = mJSONParser.getResponseFromSkillRequest(response.body().string());
                                mUserSkills = mJSONParser.mUserSkills;
                            } else {
                                Log.d("UserProfileRepo", "searchSkill UnSuccess: ");
                                mMessage = mJSONParser.getResponseFromSkillRequest(response.errorBody().string());
                            }
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("UserProfileRepo", "searchSkill OnFailure: " + t.getMessage());
                        mMessage = t.getMessage();
                    }
                });
        return mMessage;
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

    public List<UserCertificate> getUserCertificates() {
        return mUserCertificates;
    }

    public UserProfile getUserProfile() {
        return mUserProfile;
    }
}
