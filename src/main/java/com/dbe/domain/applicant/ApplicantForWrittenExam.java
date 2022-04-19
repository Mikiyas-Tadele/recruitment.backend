package com.dbe.domain.applicant;

import com.dbe.domain.vacancy.Vacancy;

import javax.persistence.*;

@Entity
@Table(name="APPLICANT_FOR_WRITTEN_EXAM",schema = "recruitmentDB")
public class ApplicantForWrittenExam {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "written_exam_applicant")
    @SequenceGenerator(name = "written_exam_applicant",schema = "recruitmentDB",allocationSize =1,sequenceName = "WRITTEN_EXAM_SEQ_ID")
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
    @Column(name="EXAM_CODE")
    private String examCode;

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

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }
}
