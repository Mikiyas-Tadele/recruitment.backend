package com.dbe.domain.applicant;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "APPLICANTPROFILE",schema = "recruitmentDB")
public class AppliedPersonelView {
    @Id
    private Long id;
    private Long applicantId;
    private String fullName;
    private String email;
    private Date dob;
    private Long age;
    private String gender;
    private String disability;
    private String mobilePhone1;
    private String mobilePhone2;
    private String fixedLinePhone;
    @Column(name="FIELD_OF_EDUCATION")
    private String fieldOfEducation;
    private Double cgpa;
    private Long qualification;
    private String university;
    @Column(name="YEAR_OF_GRADUATION")
    private Long yearOfGraduation;
    private String organization;
    private String position;
    private Double salary;
    @Column(name="START_DATE")
    private Date startDate;
    @Column(name="END_DATE")
    private Date endDate;
    private Long vacancyId;
    private Long userId;
    private Double workExperienceInYears;
    private String applicationLetter;
    private Long applicationId;
    @Column(name="total_workexperience")
    private Double totalExperience;
    private String qualificationDesc;
    @Column(name="APPLIED_DATE")
    private Date appliedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDisability() {
        return disability;
    }

    public void setDisability(String disability) {
        this.disability = disability;
    }

    public String getMobilePhone1() {
        return mobilePhone1;
    }

    public void setMobilePhone1(String mobilePhone1) {
        this.mobilePhone1 = mobilePhone1;
    }

    public String getMobilePhone2() {
        return mobilePhone2;
    }

    public void setMobilePhone2(String mobilePhone2) {
        this.mobilePhone2 = mobilePhone2;
    }

    public String getFixedLinePhone() {
        return fixedLinePhone;
    }

    public void setFixedLinePhone(String fixedLinePhone) {
        this.fixedLinePhone = fixedLinePhone;
    }

    public String getFieldOfEducation() {
        return fieldOfEducation;
    }

    public void setFieldOfEducation(String fieldOfEducation) {
        this.fieldOfEducation = fieldOfEducation;
    }

    public Double getCgpa() {
        return cgpa;
    }

    public void setCgpa(Double cgpa) {
        this.cgpa = cgpa;
    }

    public Long getQualification() {
        return qualification;
    }

    public void setQualification(Long qualification) {
        this.qualification = qualification;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public Long getYearOfGraduation() {
        return yearOfGraduation;
    }

    public void setYearOfGraduation(Long yearOfGraduation) {
        this.yearOfGraduation = yearOfGraduation;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getVacancyId() {
        return vacancyId;
    }

    public void setVacancyId(Long vacancyId) {
        this.vacancyId = vacancyId;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Double getWorkExperienceInYears() {
        return workExperienceInYears;
    }

    public void setWorkExperienceInYears(Double workExperienceInYears) {
        this.workExperienceInYears = workExperienceInYears;
    }

    public Double getTotalExperience() {
        return totalExperience;
    }

    public void setTotalExperience(Double totalExperience) {
        this.totalExperience = totalExperience;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getApplicationLetter() {
        return applicationLetter;
    }

    public void setApplicationLetter(String applicationLetter) {
        this.applicationLetter = applicationLetter;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getQualificationDesc() {
        return qualificationDesc;
    }

    public void setQualificationDesc(String qualificationDesc) {
        this.qualificationDesc = qualificationDesc;
    }

    public Date getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(Date appliedDate) {
        this.appliedDate = appliedDate;
    }
}
