package com.app.treplabs.linkedinclone.repositories;

import android.util.Log;

import com.app.treplabs.linkedinclone.helpers.JSONParser;
import com.app.treplabs.linkedinclone.models.User;
import com.app.treplabs.linkedinclone.models.UserCertificate;
import com.app.treplabs.linkedinclone.models.UserEducation;
import com.app.treplabs.linkedinclone.models.UserExperience;
import com.app.treplabs.linkedinclone.models.UserSkill;
import com.app.treplabs.linkedinclone.network.BackendProfileApi;

import org.json.JSONArray;
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
    private static UserProfileRepository instance;
    private String mMessage;

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
        JSONParser jsonParser = new JSONParser();
        try {
            Response<ResponseBody> result = call.execute();
            if (result.isSuccessful()) {
                Log.d("UserProfileRepo", "getBasicProfile: isSuccessful");
                mMessage = jsonParser.getBasicProfileResponseFromJSON(result.body().string());
            } else {
                Log.d("UserProfileRepo", "getBasicProfile: unSuccessful");
                mMessage = jsonParser.getBasicProfileResponseFromJSON(result.errorBody().string());
            }
        } catch (IOException | JSONException e) {
            mMessage = "An error occurred";
            e.printStackTrace();
        }
        return mMessage;
    }

    public String getFullProfile(String userId) {
        Call<ResponseBody> call = invokeAPI().getFullProfile(userId);
        JSONParser jsonParser = new JSONParser();
        try {
            Response<ResponseBody> result = call.execute();
            if (result.isSuccessful()) {
                Log.d("UserProfileRepo", "getFullProfile: isSuccessful");
                mMessage = jsonParser.getFullProfileResponseFromJSON(result.body().string());
                mUserEducations = jsonParser.mUserEducations;
                mUserExperiences = jsonParser.mUserExperiences;
                mUserSkills = jsonParser.mUserSkills;
            } else {
                Log.d("UserProfileRepo", "getFullProfile: unSuccessful");
                mMessage = jsonParser.getFullProfileResponseFromJSON(result.errorBody().string());
            }
        } catch (IOException | JSONException e) {
            mMessage = "An error occurred";
            e.printStackTrace();
        }
        return mMessage;
    }

    //education
    public String addNewEducation(HashMap<String, String> map, String token) {
        JSONParser jsonParser = new JSONParser();
        invokeAPI().addNewEducation(token, map)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if (response.isSuccessful()) {
                                Log.d("UserProfileRepo", "addNewEducation OnSuccess: " + response.body().string());
                                mMessage = jsonParser.getResponseFromEducationRequest(response.body().string());
                                mUserEducations = jsonParser.mUserEducations;
                            } else {
                                Log.d("UserProfileRepo", "addNewEducation UnSuccess: " + response.errorBody().string());
                                mMessage = jsonParser.getResponseFromEducationRequest(response.errorBody().string());
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
        return mMessage;
    }

    public String updateExistingEducation(String token, String educationId, HashMap<String, String> map) {
        JSONParser jsonParser = new JSONParser();
        invokeAPI().updateExistingEducation(token, educationId, map)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if (response.isSuccessful()) {
                                Log.d("UserProfileRepo", "updateExistingEducation: isSuccessful");
                                mMessage = jsonParser.getResponseFromEducationRequest(response.body().string());
                                mUserEducations = jsonParser.mUserEducations;
                            } else {
                                Log.d("UserProfileRepo", "updateExistingEducation: unSuccessful");
                                mMessage = jsonParser.getResponseFromEducationRequest(response.errorBody().string());
                            }
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("UserProfileRepo", "OnFailure: " + t.getMessage());
                        mMessage = t.getMessage();
                    }
                });
        return mMessage;
    }

    public String deleteEducation(String token, String educationId) {
        invokeAPI().deleteSkill(token, educationId)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if (response.isSuccessful()) {
                                Log.d("UserProfileRepo", "deleteEducation: isSuccessful");
                                mMessage = "Deleted User Education Successfully";
                            } else {
                                Log.d("UserProfileRepo", "deleteEducation: unSuccessful");
                                JSONObject parent = new JSONObject(response.errorBody().string());
                                mMessage = parent.getString("message");
                            }
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("UserProfileRepo", "OnFailure: " + t.getMessage());
                        mMessage = t.getMessage();
                    }
                });
        return mMessage;
    }

    //experience
    public String addNewExperience(HashMap<String, String> map, String token) {
        JSONParser jsonParser = new JSONParser();
        invokeAPI().addNewExperience(token, map)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if (response.isSuccessful()) {
                                Log.d("UserProfileRepo", "addNewExperience OnSuccess: " + response.body().string());
                                mMessage = jsonParser.getResponseFromExperienceRequest(response.body().string());
                                mUserExperiences = jsonParser.mUserExperiences;
                            } else {
                                Log.d("UserProfileRepo", "UnSuccess: " + response.errorBody().string());
                                mMessage = jsonParser.getResponseFromExperienceRequest(response.errorBody().string());
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
        return mMessage;
    }

    public String updateExistingExperience(String token, String experienceId, HashMap<String, String> map) {
        JSONParser jsonParser = new JSONParser();
        invokeAPI().updateExistingExperience(token, experienceId, map)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if (response.isSuccessful()) {
                                Log.d("UserProfileRepo", "Update OnSuccess: " + response.body().string());
                                mMessage = jsonParser.getResponseFromExperienceRequest(response.body().string());
                                mUserExperiences = jsonParser.mUserExperiences;
                            } else {
                                Log.d("UserProfileRepo", "Update UnSuccess: " + response.errorBody());
                                mMessage = jsonParser.getResponseFromExperienceRequest(response.errorBody().string());
                            }
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("UserProfileRepo", "Update OnFailure: " + t.getMessage());
                        mMessage = t.getMessage();
                    }
                });
        return mMessage;
    }

    public String deleteExperience(String token, String experienceId) {
        invokeAPI().deleteExperience(token, experienceId)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if (response.isSuccessful()) {
                                Log.d("UserProfileRepo", "deleteExperience OnSuccess: " +
                                        response.body().string());
                                mMessage = "Deleted User Experience Successfully";
                            } else {
                                Log.d("UserProfileRepo", "deleteExperience UnSuccess: " +
                                        response.body().string());
                                JSONObject parent = new JSONObject(response.errorBody().string());
                                mMessage = parent.getString("message");
                            }
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("UserProfileRepo", "deleteExperience OnFailure: " + t.getMessage());
                        mMessage = t.getMessage();
                    }
                });
        return mMessage;
    }

    //skills
    public String addNewSkill(HashMap<String, String> map, String token) {
        JSONParser jsonParser = new JSONParser();
        invokeAPI().addNewSkill(token, map)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if (response.isSuccessful()) {
                                Log.d("UserProfileRepo", "addNewSkill OnSuccess: ");
                                mMessage = jsonParser.getResponseFromSkillRequest(response.body().string());
                                mUserSkills = jsonParser.mUserSkills;
                            } else {
                                Log.d("UserProfileRepo", "addNewSkill UnSuccess: " + response.errorBody());
                                mMessage = jsonParser.getResponseFromSkillRequest(response.errorBody().string());
                            }
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("UserProfileRepo", "addNewSkill OnFailure: " + t.getMessage());
                        mMessage = t.getMessage();
                    }
                });
        return mMessage;
    }

    public String searchSkill(String token, String searchText) {
        JSONParser jsonParser = new JSONParser();
        invokeAPI().searchForSkill(token, searchText)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if (response.isSuccessful()) {
                                Log.d("UserProfileRepo", "searchSkill OnSuccess: ");
                                mMessage = jsonParser.getResponseFromSkillRequest(response.body().string());
                                mUserSkills = jsonParser.mUserSkills;
                            } else {
                                Log.d("UserProfileRepo", "searchSkill UnSuccess: ");
                                mMessage = jsonParser.getResponseFromSkillRequest(response.errorBody().string());
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

    public String deleteSkill(String token, String skillId) {
        invokeAPI().deleteSkill(token, skillId)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if (response.isSuccessful()) {
                                Log.d("UserProfileRepo", "deleteSkill OnSuccess: " +
                                        response.body().string());
                                mMessage = "Deleted User Skill Successfully";
                            } else {
                                Log.d("UserProfileRepo", "deleteSkill UnSuccess: " +
                                        response.body().string());
                                JSONObject parent = new JSONObject(response.errorBody().string());
                                mMessage = parent.getString("message");
                            }
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("UserProfileRepo", "deleteSkill OnFailure: " + t.getMessage());
                        mMessage = t.getMessage();
                    }
                });
        return mMessage;
    }

    //certification
    public String addNewCertificate(HashMap<String, String> map, String token) {
        JSONParser jsonParser = new JSONParser();
        invokeAPI().addNewCertificate(token, map)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if (response.isSuccessful()) {
                                Log.d("UserProfileRepo", "addNewCertificate OnSuccess: ");
                                mMessage = jsonParser.getResponseFromCertificateRequest(response.body().string());
                                mUserCertificates = jsonParser.mUserCertificates;
                            } else {
                                Log.d("UserProfileRepo", "addNewCertificate UnSuccess: ");
                                mMessage = jsonParser.getResponseFromCertificateRequest(response.errorBody().string());
                            }
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("UserProfileRepo", "addNewCertificate OnFailure: " + t.getMessage());
                        mMessage = t.getMessage();
                    }
                });
        return mMessage;
    }

    public String updateExistingCertificate(String token, String certificateId, HashMap<String, String> map) {
        JSONParser jsonParser = new JSONParser();
        invokeAPI().updateExistingCertificate(token, certificateId, map)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if (response.isSuccessful()) {
                                Log.d("UserProfileRepo", "Update OnSuccess: " + response.body().string());
                                mMessage = jsonParser.getResponseFromCertificateRequest(response.body().string());
                                mUserCertificates = jsonParser.mUserCertificates;
                            } else {
                                Log.d("UserProfileRepo", "Update UnSuccess: " + response.errorBody());
                                mMessage = jsonParser.getResponseFromCertificateRequest(response.errorBody().string());
                            }
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("UserProfileRepo", "Update OnFailure: " + t.getMessage());
                        mMessage = t.getMessage();
                    }
                });
        return mMessage;
    }

    public String deleteCertificate(String token, String certificateId) {
        invokeAPI().deleteCertificate(token, certificateId)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if (response.isSuccessful()) {
                                Log.d("UserProfileRepo", "deleteCertificate OnSuccess: " +
                                        response.body().string());
                                mMessage = "Deleted User Certificate Successfully";
                            } else {
                                Log.d("UserProfileRepo", "deleteCertificate UnSuccess: " +
                                        response.body().string());
                                JSONObject parent = new JSONObject(response.errorBody().string());
                                mMessage = parent.getString("message");
                            }
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("UserProfileRepo", "deleteCertificate OnFailure: " + t.getMessage());
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

}
