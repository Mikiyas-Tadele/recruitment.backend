package com.dbe.services.application.model;

public class ApplicantForWrittenExamModel {
    private Long id;
    private Long  applicantId;
    private String applicantName;
    private Long vacancyId;
    private String vacancyTitle;
    private Double  examResult;
    private Boolean isSelected;
    private String examCode;
    private String examCodePrefix;
    private Boolean addOrRemove;


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

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public Long getVacancyId() {
        return vacancyId;
    }

    public void setVacancyId(Long vacancyId) {
        this.vacancyId = vacancyId;
    }

    public String getVacancyTitle() {
        return vacancyTitle;
    }

    public void setVacancyTitle(String vacancyTitle) {
        this.vacancyTitle = vacancyTitle;
    }

    public Double getExamResult() {
        return examResult;
    }

    public void setExamResult(Double examResult) {
        this.examResult = examResult;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }

    public String getExamCodePrefix() {
        return examCodePrefix;
    }

    public void setExamCodePrefix(String examCodePrefix) {
        this.examCodePrefix = examCodePrefix;
    }

    public Boolean getAddOrRemove() {
        return addOrRemove;
    }

    public void setAddOrRemove(Boolean addOrRemove) {
        this.addOrRemove = addOrRemove;
    }
}
