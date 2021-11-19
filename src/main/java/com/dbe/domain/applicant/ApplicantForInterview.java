package com.dbe.domain.applicant;

import com.dbe.domain.vacancy.Vacancy;

import javax.persistence.*;

@Entity
@Table(name = "APPLICANT_FOR_INTERVIEW",schema = "recruitmentDB")
public class ApplicantForInterview {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "interview_applicant")
    @SequenceGenerator(name = "interview_applicant",schema = "recruitmentDB",allocationSize =1,sequenceName = "INTERVIEW_SEQ_ID")
    private Long id;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id",name = "applicantId")
    private Applicant applicant;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id",name = "vacancyId")
    private Vacancy vacancy;
    @Column(name="exam_result")
    private Double  examResult;
    private Boolean isSelected;
    private Boolean isFinal;

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

    public Boolean getFinal() {
        return isFinal;
    }

    public void setFinal(Boolean aFinal) {
        isFinal = aFinal;
    }
}
