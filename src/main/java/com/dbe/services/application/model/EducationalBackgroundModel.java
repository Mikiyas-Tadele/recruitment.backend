package com.dbe.services.application.model;

public class EducationalBackgroundModel {
    private Long id;
    private String fieldOfEducation;
    private Long qualification;
    private String qualificationDesc;
    private String university;
    private String yearOfGraduation;
    private Long applicantId;
    private Double cgpa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldOfEducation() {
        return fieldOfEducation;
    }

    public void setFieldOfEducation(String fieldOfEducation) {
        this.fieldOfEducation = fieldOfEducation;
    }

    public Long getQualification() {
        return qualification;
    }

    public void setQualification(Long qualification) {
        this.qualification = qualification;
    }

    public String getQualificationDesc() {
        return qualificationDesc;
    }

    public void setQualificationDesc(String qualificationDesc) {
        this.qualificationDesc = qualificationDesc;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getYearOfGraduation() {
        return yearOfGraduation;
    }

    public void setYearOfGraduation(String yearOfGraduation) {
        this.yearOfGraduation = yearOfGraduation;
    }

    public Long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public Double getCgpa() {
        return cgpa;
    }

    public void setCgpa(Double cgpa) {
        this.cgpa = cgpa;
    }
}
