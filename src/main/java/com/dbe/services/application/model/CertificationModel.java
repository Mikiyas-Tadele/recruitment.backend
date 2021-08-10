package com.dbe.services.application.model;

import java.util.Date;

public class CertificationModel {
    private Long id;
    private String title;
    private String instution;
    private Date awardDate;
    private Long applicantId;

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

    public String getInstution() {
        return instution;
    }

    public void setInstution(String instution) {
        this.instution = instution;
    }

    public Date getAwardDate() {
        return awardDate;
    }

    public void setAwardDate(Date awardDate) {
        this.awardDate = awardDate;
    }

    public Long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }
}
