package com.dbe.services.vacancy.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VacancyModel {
    private Long id;
    private  String title;
    private  String qualification;
    private  String workExperience;
    private  String location;
    private  Date postedDate;
    private  Date deadlineDate;
    private  String minutesElapsedSinceCreation;
    private String employmentCondition;
    private Long requiredNumber;
    private Double salary;
    private String salaryDescription;
    private List<VacancyModelDetail> vacancyModelDetailList=new ArrayList<>();
    private boolean closed;
    private String cgpa;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public String getMinutesElapsedSinceCreation() {
        return minutesElapsedSinceCreation;
    }

    public void setMinutesElapsedSinceCreation(String minutesElapsedSinceCreation) {
        this.minutesElapsedSinceCreation = minutesElapsedSinceCreation;
    }

    public List<VacancyModelDetail> getVacancyModelDetailList() {
        return vacancyModelDetailList;
    }

    public void setVacancyModelDetailList(List<VacancyModelDetail> vacancyModelDetailList) {
        this.vacancyModelDetailList = vacancyModelDetailList;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public String getEmploymentCondition() {
        return employmentCondition;
    }

    public void setEmploymentCondition(String employmentCondition) {
        this.employmentCondition = employmentCondition;
    }

    public Long getRequiredNumber() {
        return requiredNumber;
    }

    public void setRequiredNumber(Long requiredNumber) {
        this.requiredNumber = requiredNumber;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getSalaryDescription() {
        return salaryDescription;
    }

    public void setSalaryDescription(String salaryDescription) {
        this.salaryDescription = salaryDescription;
    }

    public String getCgpa() {
        return cgpa;
    }

    public void setCgpa(String cgpa) {
        this.cgpa = cgpa;
    }
}
