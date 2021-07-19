package com.dbe.services.application.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ApplicantModel {

    private Long id;
    private Long userId;
    private Date dateOfBirth;
    private Long gender;
    private Double cgpa;
    private Long disability;
    private String mPhone1;
    private String mPhone2;
    private String fPhone;
    private List<EducationalBackgroundModel> educationalBackgrounds=new ArrayList<>();
    private List<WorkExperienceModel> workExperiences=new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Long getGender() {
        return gender;
    }

    public void setGender(Long gender) {
        this.gender = gender;
    }

    public Double getCgpa() {
        return cgpa;
    }

    public void setCgpa(Double cgpa) {
        this.cgpa = cgpa;
    }

    public Long getDisability() {
        return disability;
    }

    public void setDisability(Long disability) {
        this.disability = disability;
    }

    public String getmPhone1() {
        return mPhone1;
    }

    public void setmPhone1(String mPhone1) {
        this.mPhone1 = mPhone1;
    }

    public String getmPhone2() {
        return mPhone2;
    }

    public void setmPhone2(String mPhone2) {
        this.mPhone2 = mPhone2;
    }

    public String getfPhone() {
        return fPhone;
    }

    public void setfPhone(String fPhone) {
        this.fPhone = fPhone;
    }

    public List<EducationalBackgroundModel> getEducationalBackgrounds() {
        return educationalBackgrounds;
    }

    public void setEducationalBackgrounds(List<EducationalBackgroundModel> educationalBackgrounds) {
        this.educationalBackgrounds = educationalBackgrounds;
    }

    public List<WorkExperienceModel> getWorkExperiences() {
        return workExperiences;
    }

    public void setWorkExperiences(List<WorkExperienceModel> workExperiences) {
        this.workExperiences = workExperiences;
    }
}
