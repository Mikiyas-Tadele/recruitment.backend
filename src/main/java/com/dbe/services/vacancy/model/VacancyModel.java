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
    private List<VacancyModelDetail> vacancyModelDetailList=new ArrayList<>();


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
}
