package com.dbe.domain.applicant;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="certification",schema="recruitmentDB")
public class Certification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "cert_sequence")
    @SequenceGenerator(name = "cert_sequence",schema = "recruitmentDB",allocationSize =1,sequenceName = "CERTIFICATION_ID_SEQ")
    private Long id;
    private String title;
    private String instution;
    @Column(name="award_date")
    private Date awardDate;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id",name = "applicantId")
    private Applicant applicant;

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

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }
}
