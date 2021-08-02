package com.dbe.services.application.model;

public class ApplicationModel {
    private Long id;
    private String applicationLetter;
    private Long vacancyId;

    public String getApplicationLetter() {
        return applicationLetter;
    }

    public void setApplicationLetter(String applicationLetter) {
        this.applicationLetter = applicationLetter;
    }

    public Long getVacancyId() {
        return vacancyId;
    }

    public void setVacancyId(Long vacancyId) {
        this.vacancyId = vacancyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
