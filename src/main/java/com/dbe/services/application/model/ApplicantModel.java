package com.dbe.services.application.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ApplicantModel {

    private Long id;
    private Long userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date dateOfBirth;
    private String gender;
    private Double cgpa;
    private String disability;
    private String mPhone1;
    private String mPhone2;
    private String fPhone;
    private String currentLocation;
    private String disabilityDescription;
    private List<EducationalBackgroundModel> educationalBackgrounds=new ArrayList<>();
    private List<WorkExperienceModel> workExperiences=new ArrayList<>();
    private List<CertificationModel> certifications=new ArrayList<>();

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Double getCgpa() {
        return cgpa;
    }

    public void setCgpa(Double cgpa) {
        this.cgpa = cgpa;
    }

    public String getDisability() {
        return disability;
    }

    public void setDisability(String disability) {
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

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
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

    public String getDisabilityDescription() {
        return disabilityDescription;
    }

    public void setDisabilityDescription(String disabilityDescription) {
        this.disabilityDescription = disabilityDescription;
    }

    public List<CertificationModel> getCertifications() {
        return certifications;
    }

    public void setCertifications(List<CertificationModel> certifications) {
        this.certifications = certifications;
    }
}
