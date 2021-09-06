package com.dbe.services.vacancy.model;

import java.util.Date;

public class InternalVacancyModel {
    private Long id;
    private String position;
    private String qualifications;
    private Long jobGrade;
    private Long noRequired;
    private String placementOfWork;
    private Date postDate;
    private Date endDate;
    private Long parent;
    private Long managerial;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public Long getJobGrade() {
        return jobGrade;
    }

    public void setJobGrade(Long jobGrade) {
        this.jobGrade = jobGrade;
    }

    public Long getNoRequired() {
        return noRequired;
    }

    public void setNoRequired(Long noRequired) {
        this.noRequired = noRequired;
    }

    public String getPlacementOfWork() {
        return placementOfWork;
    }

    public void setPlacementOfWork(String placementOfWork) {
        this.placementOfWork = placementOfWork;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public Long getManagerial() {
        return managerial;
    }

    public void setManagerial(Long managerial) {
        this.managerial = managerial;
    }
}
