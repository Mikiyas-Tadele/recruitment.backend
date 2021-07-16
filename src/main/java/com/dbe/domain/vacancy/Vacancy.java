package com.dbe.domain.vacancy;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "vacancy",schema = "recruitmentDB")
public class Vacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "vacancy_sequence")
    @SequenceGenerator(name = "vacancy_sequence",schema = "recruitmentDB",allocationSize =1,sequenceName = "VACANCY_ID_SEQ")
    private Long id;
    private String title;
    private String qualification;
    @Column(name="work_experience")
    private String workExperience;
    private String location;
    @Column(name="posted_date")
    private Date postedDate;
    @Column(name="deadline_date")
    private  Date DeadlineDate;

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

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public Date getDeadlineDate() {
        return DeadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        DeadlineDate = deadlineDate;
    }
}
