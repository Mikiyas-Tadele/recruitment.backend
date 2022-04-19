package com.dbe.domain.applicant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "final_result_view",schema = "recruitmentDB")
public class FinalResultView {
    @Id
    private Long id;
    @Column(name="applicant_name")
    private String applicantName;
    @Column(name="WRITTEN_EXAM_RESULT")
    private Double writtenExamResult;
    @Column(name = "INTERVIEW_RESULT")
    private Double interviewResult;
    @Column(name="total_result")
    private Double totalResult;
    private Long vacancyId;
    @Column(name="exam_code")
    private String examCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public Double getWrittenExamResult() {
        return writtenExamResult;
    }

    public void setWrittenExamResult(Double writtenExamResult) {
        this.writtenExamResult = writtenExamResult;
    }

    public Double getInterviewResult() {
        return interviewResult;
    }

    public void setInterviewResult(Double interviewResult) {
        this.interviewResult = interviewResult;
    }

    public Double getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(Double totalResult) {
        this.totalResult = totalResult;
    }

    public Long getVacancyId() {
        return vacancyId;
    }

    public void setVacancyId(Long vacancyId) {
        this.vacancyId = vacancyId;
    }

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }
}
