package com.app.treplabs.linkedinclone.models;

public class UserEducation {
    private String mSchoolName;
    private String mSchoolDegree;
    private String mFromYearToYear;

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
}
