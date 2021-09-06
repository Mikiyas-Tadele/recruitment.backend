package com.dbe.domain.internal_vacancy;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Internal_Vacancy")
public class InternalVacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "iVacancy_ID")
    @SequenceGenerator(name = "iVacancy_ID",schema = "recruitmentDB",allocationSize =1,sequenceName = "IVACANCY_SEQ_ID")
    private Long id;
    private String position;
    private String qualifications;
    @Column(name="JOB_GRADE")
    private Long jobGrade;
    @Column(name="no_required")
    private Long noRequired;
    @Column(name="placement_of_work")
    private String placementOfWork;
    @Column(name="post_date")
    private Date postDate;
    @Column(name="end_date")
    private Date endDate;
    private Long parent;
    private Long status;
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

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getManagerial() {
        return managerial;
    }

    public void setManagerial(Long managerial) {
        this.managerial = managerial;
    }
}
