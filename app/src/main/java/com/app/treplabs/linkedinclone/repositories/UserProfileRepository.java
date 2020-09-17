package com.app.treplabs.linkedinclone.repositories;

import android.util.Log;
import com.app.treplabs.linkedinclone.models.User;
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
    private static UserProfileRepository instance;
    private String mMessage;
    private boolean mSuccess;

    public static UserProfileRepository getInstance() {
        if (instance == null) {
            instance = new UserProfileRepository();
            instance.initializeSampleSkills();
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
                getBasicProfileResponseFromJSON(result.body().string());
            } else {
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
        mSuccess = parent.getBoolean("success");
        mMessage = parent.getString("message");
        if (mSuccess) {
            JSONObject data = parent.getJSONObject("data");
            JSONObject user = data.getJSONObject("user");
            String id = user.getString("id");
            String firstname = user.getString("firstname");
            String lastname = user.getString("lastname");
            String email = user.getString("email");
            String slug = user.getString("slug");

            User.INSTANCE.setFirstName(firstname);
            User.INSTANCE.setLastName(lastname);
            User.INSTANCE.setUserId(id);
            User.INSTANCE.setEmail(email);
            User.INSTANCE.setSlug(slug);
        }
    }

    public String getFullProfile(String userId) {
        Call<ResponseBody> call = invokeAPI().getFullProfile(userId);
        try {
            Response<ResponseBody> result = call.execute();
            if (result.isSuccessful()) {
                Log.d("UserProfileRepo", "getFullProfile: isSuccessful");
                getFullProfileResponseFromJSON(result.body().string());
            } else {
                Log.d("UserProfileRepo", "getFullProfile: unSuccessful");
                getFullProfileResponseFromJSON(result.errorBody().string());
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return mMessage;
    }

    private void getFullProfileResponseFromJSON(String string) throws JSONException {
        getBasicProfileResponseFromJSON(string);
        if (mSuccess) {
            JSONObject profile = new JSONObject(string)
                    .getJSONObject("data")
                    .getJSONObject("user")
                    .getJSONObject("profile");
            JSONArray educations = profile.getJSONArray("educations");
            for (int i = 0; i < educations.length(); i++) {
                JSONObject education = (JSONObject) educations.get(i);
                String educationId = education.getString("id");
                String schoolName = education.getString("schoolName");
                String fieldOfStudy = education.getString("fieldOfStudy");
                int startDate = education.getInt("startDate");
                int endDate = education.getInt("endDate");
                mUserEducations.add(new UserEducation(schoolName, fieldOfStudy,
                        startDate + " - " + endDate, educationId));
            }
            JSONArray experiences = profile.getJSONArray("positions");
            for (int i = 0; i < experiences.length(); i++) {
                JSONObject experience = (JSONObject) experiences.get(i);
                String experienceId = experience.getString("id");
                String title = experience.getString("title");
                String summary = experience.getString("summary");
                String startDate = experience.getString("startDate");
                String endDate = experience.getString("endDate");
                boolean isCurrent = experience.getBoolean("isCurrent");
                String company = experience.getString("company");
                int noOfYears = Integer.parseInt(endDate.split(" ")[1]) -
                        Integer.parseInt(startDate.split(" ")[1]);
                mUserExperiences.add(new UserExperience(title, company,
                        startDate + " - " + endDate, noOfYears, isCurrent,
                        summary, experienceId));
            }
        }
    }

    //education
    public String addNewEducation(HashMap<String, String> map, String token) {
        Call<ResponseBody> call = invokeAPI().addNewEducation(token, map);
        try {
            Response<ResponseBody> result = call.execute();
            if (result.isSuccessful()) {
                Log.d("UserProfileRepo", "addNewEducation: isSuccessful");
                getResponseFromEducationRequest(result.body().string());
            } else {
                Log.d("UserProfileRepo", "addNewEducation: unSuccessful");
                getResponseFromEducationRequest(result.errorBody().string());
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return mMessage;
    }

    public String updateExistingEducation(String token, String educationId, HashMap<String, String> map) {
        Call<ResponseBody> call = invokeAPI().updateExistingEducation(token, educationId, map);
        try {
            Response<ResponseBody> result = call.execute();
            if (result.isSuccessful()) {
                Log.d("UserProfileRepo", "updateExistingEducation: isSuccessful");
                getResponseFromEducationRequest(result.body().string());
            } else {
                Log.d("UserProfileRepo", "updateExistingEducation: unSuccessful");
                getResponseFromEducationRequest(result.errorBody().string());
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return mMessage;
    }

    public String deleteEducation(String token, String educationId) {
        Call<ResponseBody> call = invokeAPI().deleteEducation(token, educationId);
        try {
            Response<ResponseBody> result = call.execute();
            if (result.isSuccessful()) {
                Log.d("UserProfileRepo", "deleteEducation: isSuccessful");
                mMessage = "Deleted User Education Successfully";
            } else {
                Log.d("UserProfileRepo", "deleteEducation: unSuccessful");
                JSONObject parent = new JSONObject(result.errorBody().string());
                mMessage = parent.getString("message");
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return mMessage;
    }

    private void getResponseFromEducationRequest(String string) throws JSONException {
        JSONObject parent = new JSONObject(string);
        mSuccess = parent.getBoolean("success");
        mMessage = parent.getString("message");
        if (mSuccess) {
            JSONObject jsonObject = parent.getJSONObject("data");
            JSONObject education = jsonObject.getJSONObject("education");
            String educationId = education.getString("id");
            String schoolName = education.getString("schoolName");
            String fieldOfStudy = education.getString("fieldOfStudy");
            int startDate = education.getInt("startDate");
            int endDate = education.getInt("endDate");

            for (UserEducation userEducation : mUserEducations) {
                if (educationId.equals(userEducation.getEducationId())) {
                    userEducation.setEducationId(educationId);
                    userEducation.setFromYearToYear(startDate + " - " + endDate);
                    userEducation.setSchoolDegree(fieldOfStudy);
                    userEducation.setSchoolName(schoolName);
                } else {
                    mUserEducations.add(new UserEducation(schoolName, fieldOfStudy,
                            startDate + " - " + endDate, educationId));
                }
            }
        }
    }

    //experience
    public String addNewExperience(HashMap<String, String> map, String token) {
        invokeAPI().addNewExperience(token, map)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if (response.isSuccessful()) {
                                Log.d("UserProfileRepo", "addNewExperience OnSuccess: " + response.body().string());
                                getResponseFromExperienceRequest(response.body().string());
                            } else {
                                Log.d("UserProfileRepo", "UnSuccess: " + response.errorBody());
                                getResponseFromExperienceRequest(response.errorBody().string());
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
        invokeAPI().updateExistingExperience(token, experienceId, map)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if (response.isSuccessful()) {
                                Log.d("UserProfileRepo", "Update OnSuccess: " + response.body().string());
                                getResponseFromExperienceRequest(response.body().string());
                            } else {
                                Log.d("UserProfileRepo", "Update UnSuccess: " + response.errorBody());
                                getResponseFromExperienceRequest(response.errorBody().string());
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

    private void getResponseFromExperienceRequest(String string) throws JSONException {
        JSONObject parent = new JSONObject(string);
        mSuccess = parent.getBoolean("success");
        mMessage = parent.getString("message");
        if (mSuccess) {
            JSONObject jsonObject = parent.getJSONObject("data");
            JSONObject experience = jsonObject.getJSONObject("position");
            String experienceId = experience.getString("id");
            String jobTitle = experience.getString("title");
            String jobSummary = experience.getString("summary");
            String startDate = experience.getString("startDate");
            String endDate = experience.getString("endDate");
            boolean isCurrent = experience.getBoolean("isCurrent");
            String company = experience.getString("company");
            int noOfYears = Integer.parseInt(endDate.split(" ")[1]) -
                    Integer.parseInt(startDate.split(" ")[1]);

            for (UserExperience userExperience : mUserExperiences) {
                if (experienceId.equals(userExperience.getExperienceId())) {
                    userExperience.setJobTitle(jobTitle);
                    userExperience.setCompany(company);
                    userExperience.setCurrent(isCurrent);
                    userExperience.setExperienceId(experienceId);
                    userExperience.setSummary(jobSummary);
                    userExperience.setFromDateToDate(startDate + " - " + endDate);
                    userExperience.setNoOfYears(noOfYears);
                } else {
                    mUserExperiences.add(new UserExperience(jobTitle, company,
                            startDate + " - " + endDate, noOfYears, isCurrent,
                            jobSummary, experienceId));
                }
            }
        }
    }

    //skills
    public String addNewSkill(HashMap<String, String> map, String token) {
        invokeAPI().addNewSkill(token, map)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if (response.isSuccessful()) {
                                Log.d("UserProfileRepo", "addNewSkill OnSuccess: " + response.body().string());
                                getResponseFromSkillRequest(response.body().string());
                            } else {
                                Log.d("UserProfileRepo", "addNewSkill UnSuccess: " + response.errorBody());
                                getResponseFromSkillRequest(response.errorBody().string());
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
        invokeAPI().searchForSkill(token, searchText)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if (response.isSuccessful()) {
                                Log.d("UserProfileRepo", "searchSkill OnSuccess: " + response.body().string());
                                getResponseFromSkillRequest(response.body().string());
                            } else {
                                Log.d("UserProfileRepo", "searchSkill UnSuccess: " + response.errorBody());
                                getResponseFromSkillRequest(response.errorBody().string());
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

    private void getResponseFromSkillRequest(String string) throws JSONException {
        JSONObject parent = new JSONObject(string);
        mSuccess = parent.getBoolean("success");
        mMessage = parent.getString("message");
        if (mSuccess) {
            JSONObject jsonObject = parent.getJSONObject("data");
            JSONArray skillArray = jsonObject.getJSONArray("skills");
            for (int i = 0; i < skillArray.length(); i++) {
                JSONObject object = (JSONObject) skillArray.get(i);
                String skillId = object.getString("id");
                String name = object.getString("name");
                mUserSkills.add(new UserSkill(name, skillId));
            }
        }
    }

    //samples
    private void initializeSampleSkills() {
        mUserSkills.add(new UserSkill("UI Design", ""));
        mUserSkills.add(new UserSkill("Graphics Design", ""));
        mUserSkills.add(new UserSkill("Adobe Photoshop", ""));
        mUserSkills.add(new UserSkill("Adobe Illustrator", ""));
        mUserSkills.add(new UserSkill("Android", ""));
        mUserSkills.add(new UserSkill("Web Development", ""));
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
