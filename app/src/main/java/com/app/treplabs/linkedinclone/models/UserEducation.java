package com.app.treplabs.linkedinclone.models;

public class UserEducation {
    private String mSchoolName;
    private String mSchoolDegree;
    private String mFromYearToYear;
    private String mEducationId;

    public UserEducation(String schoolName, String schoolDegree, String fromYearToYear, String educationId) {
        mSchoolName = schoolName;
        mSchoolDegree = schoolDegree;
        mFromYearToYear = fromYearToYear;
        mEducationId = educationId;
    }

    public UserEducation(String schoolName, String schoolDegree, String fromYearToYear) {
        mSchoolName = schoolName;
        mSchoolDegree = schoolDegree;
        mFromYearToYear = fromYearToYear;
    }

    public String getSchoolName() {
        return mSchoolName;
    }

    public void setSchoolName(String schoolName) {
        mSchoolName = schoolName;
    }

    public String getSchoolDegree() {
        return mSchoolDegree;
    }

    public void setSchoolDegree(String schoolDegree) {
        mSchoolDegree = schoolDegree;
    }

    public String getFromYearToYear() {
        return mFromYearToYear;
    }

    public void setFromYearToYear(String fromYearToYear) {
        mFromYearToYear = fromYearToYear;
    }

    public String getEducationId() {
        return mEducationId;
    }

    public void setEducationId(String educationId) {
        mEducationId = educationId;
    }
}
