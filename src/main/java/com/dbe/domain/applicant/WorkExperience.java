package com.dbe.domain.applicant;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="WORK_EXPERIENCE",schema = "recruitmentDB")
public class WorkExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "workexp_sequence")
    @SequenceGenerator(name = "workexp_sequence",schema = "recruitmentDB",allocationSize =1,sequenceName = "WORKEXP_SEQ_ID")
    private Long id;
    private String position;
    private Double salary;
    private String organization;
    @Column(name="start_date")
    private Date startDate;
    @Column(name="end_date")
    private Date endDate;
    @ManyToOne
    @JoinColumn(name="applicantId",referencedColumnName = "id")
    private Applicant applicant;

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

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }
}
