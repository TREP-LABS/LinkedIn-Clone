package com.app.treplabs.linkedinclone.helpers;

import com.app.treplabs.linkedinclone.models.User;
import com.app.treplabs.linkedinclone.models.UserCertificate;
import com.app.treplabs.linkedinclone.models.UserEducation;
import com.app.treplabs.linkedinclone.models.UserExperience;
import com.app.treplabs.linkedinclone.models.UserSkill;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONParser {
    public List<UserEducation> mUserEducations;
    public List<UserExperience> mUserExperiences;
    public List<UserSkill> mUserSkills;
    public List<UserCertificate> mUserCertificates;
    private boolean mSuccess;

    public String getBasicProfileResponseFromJSON(String string) throws JSONException {
        JSONObject parent = new JSONObject(string);
        mSuccess = parent.getBoolean("success");
        String message = parent.getString("message");
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
        return message;
    }

    public String getFullProfileResponseFromJSON(String string) throws JSONException {
        mUserEducations = new ArrayList<>();
        mUserExperiences = new ArrayList<>();
        mUserSkills = new ArrayList<>();
        String message = getBasicProfileResponseFromJSON(string);
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
            JSONArray skills = profile.getJSONArray("skills");
            for (int i = 0; i < skills.length(); i++) {
                JSONObject skill = (JSONObject) skills.get(i);
                String skillId = skill.getString("id");
                String name = skill.getString("name");
                mUserSkills.add(new UserSkill(name, skillId));
            }
        }
        return message;
    }

    public String getResponseFromEducationRequest(String string) throws JSONException {
        JSONObject parent = new JSONObject(string);
        mSuccess = parent.getBoolean("success");
        String message = parent.getString("message");
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
        return message;
    }

    public String getResponseFromExperienceRequest(String string) throws JSONException {
        JSONObject parent = new JSONObject(string);
        mSuccess = parent.getBoolean("success");
        String message = parent.getString("message");
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
        return message;
    }

    public String getResponseFromSkillRequest(String string) throws JSONException {
        JSONObject parent = new JSONObject(string);
        mSuccess = parent.getBoolean("success");
        String message = parent.getString("message");
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
        return message;
    }

    public String getResponseFromCertificateRequest(String string) throws JSONException {
        JSONObject parent = new JSONObject(string);
        mSuccess = parent.getBoolean("success");
        String message = parent.getString("message");
        if (mSuccess) {
            JSONObject jsonObject = parent.getJSONObject("data");
            JSONArray certificateArray = jsonObject.getJSONArray("certification");
            for (int i = 0; i < certificateArray.length(); i++) {
                JSONObject object = (JSONObject) certificateArray.get(i);
                String certificateId = object.getString("id");
                String name = object.getString("name");
                String authority = object.getString("authority");
                String number = object.getString("number");
                String startDate = object.getString("startDate");
                String endDate = object.getString("endDate");
                boolean isPresent = object.getBoolean("isPresent");
                String url = object.getString("url");

                for (UserCertificate userCertificate : mUserCertificates) {
                    if (certificateId.equals(userCertificate.getCertificateId())) {
                        userCertificate.setName(name);
                        userCertificate.setAuthority(authority);
                        userCertificate.setCertificateId(certificateId);
                        userCertificate.setNumber(number);
                        userCertificate.setUrl(url);
                        userCertificate.setEndDate(endDate);
                        userCertificate.setPresent(isPresent);
                        userCertificate.setStartDate(startDate);
                    } else {
                        mUserCertificates.add(new UserCertificate(name, authority, number, startDate,
                                endDate, certificateId, url, isPresent));
                    }
                }
            }
        }
        return message;
    }

}
