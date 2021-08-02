package com.dbe.services.application.model;

public class SearchModel {
    private Long age;
    private Double cgpa;
    private Long workExperience;
    private String gender;
    private String ageCriteria;
    private String cgpaCriteria;
    private String workExperienceCriteria;
    private Long vacancyId;
    private Long qualification;

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Double getCgpa() {
        return cgpa;
    }

    public void setCgpa(Double cgpa) {
        this.cgpa = cgpa;
    }

    public Long getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(Long workExperience) {
        this.workExperience = workExperience;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAgeCriteria() {
        return ageCriteria;
    }

    public void setAgeCriteria(String ageCriteria) {
        this.ageCriteria = ageCriteria;
    }

    public String getCgpaCriteria() {
        return cgpaCriteria;
    }

    public void setCgpaCriteria(String cgpaCriteria) {
        this.cgpaCriteria = cgpaCriteria;
    }

    public String getWorkExperienceCriteria() {
        return workExperienceCriteria;
    }

    public void setWorkExperienceCriteria(String workExperienceCriteria) {
        this.workExperienceCriteria = workExperienceCriteria;
    }

    public Long getVacancyId() {
        return vacancyId;
    }

    public void setVacancyId(Long vacancyId) {
        this.vacancyId = vacancyId;
    }

    public Long getQualification() {
        return qualification;
    }

    public void setQualification(Long qualification) {
        this.qualification = qualification;
    }
}
