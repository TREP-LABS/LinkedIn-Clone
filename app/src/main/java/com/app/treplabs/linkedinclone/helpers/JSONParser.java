package com.app.treplabs.linkedinclone.helpers;

import com.app.treplabs.linkedinclone.models.User;
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
            for (int i = 0; i < skills.length(); i++){
                JSONObject skill = (JSONObject) skills.get(i);
                String skillId = skill.getString("id");
                String name = skill.getString("name");
                mUserSkills.add(new UserSkill(name, skillId));
            }
        }
        return message;
    }
}
