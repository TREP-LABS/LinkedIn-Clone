package com.app.treplabs.linkedinclone.models;

public class UserExperience {
    private String mJobTitle;
    private String mCompany;
    private String mFromDateToDate;
    private int mNoOfYears;

    public UserExperience(String jobTitle, String company, String fromDateToDate, int noOfYears) {
        mJobTitle = jobTitle;
        mCompany = company;
        mFromDateToDate = fromDateToDate;
        mNoOfYears = noOfYears;
    }

    public String getJobTitle() {
        return mJobTitle;
    }

    public void setJobTitle(String jobTitle) {
        mJobTitle = jobTitle;
    }

    public String getCompany() {
        return mCompany;
    }

    public void setCompany(String company) {
        mCompany = company;
    }

    public String getFromDateToDate() {
        return mFromDateToDate;
    }

    public void setFromDateToDate(String fromDateToDate) {
        mFromDateToDate = fromDateToDate;
    }

    public int getNoOfYears() {
        return mNoOfYears;
    }

    public void setNoOfYears(int noOfYears) {
        mNoOfYears = noOfYears;
    }
}
