package com.dbe.domain.applicant;

import com.dbe.domain.vacancy.Vacancy;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Application",schema = "recruitmentDB")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "application_sequence")
    @SequenceGenerator(name = "application_sequence",schema = "recruitmentDB",allocationSize =1,sequenceName = "APPLICATION_SEQ_ID")
    private Long id;
    @ManyToOne
    @JoinColumn(name="applicantId",referencedColumnName = "id")
    private Applicant applicant;
    @ManyToOne
    @JoinColumn(name="vacancyId",referencedColumnName = "id")
    private Vacancy vacancy;
    @Column(name="applied_date")
    private Date appliedDate;
    private Long Status;
    @OneToOne
    @JoinColumn(name="fileId",referencedColumnName = "id")
    private ApplicantFile applicantFile;
    private String applicationLetter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy;
    }

    public Date getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(Date appliedDate) {
        this.appliedDate = appliedDate;
    }

    public ApplicantFile getApplicantFile() {
        return applicantFile;
    }

    public void setApplicantFile(ApplicantFile applicantFile) {
        this.applicantFile = applicantFile;
    }

    public String getApplicationLetter() {
        return applicationLetter;
    }

    public void setApplicationLetter(String applicationLetter) {
        this.applicationLetter = applicationLetter;
    }

    public Long getStatus() {
        return Status;
    }

    public void setStatus(Long status) {
        Status = status;
    }
}
