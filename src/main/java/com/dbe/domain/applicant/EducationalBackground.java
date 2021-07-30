package com.dbe.domain.applicant;

import javax.persistence.*;

@Entity
@Table(name="EDUCATIONAL_BACKGROUND",schema = "recruitmentDB")
public class EducationalBackground {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "edu_sequence")
    @SequenceGenerator(name = "edu_sequence",schema = "recruitmentDB",allocationSize =1,sequenceName = "EDUCATION_SEQ_ID")
    private Long id;
    @Column(name="FIELD_OF_EDUCATION")
    private String fieldOfEducation;
    private String specialization;
    private Long qualification;
    private String university;
    @Column(name="YEAR_OF_GRADUATION")
    private String yearOfGraduation;
    private Double cgpa;
    @ManyToOne
    @JoinColumn(name="applicantId",referencedColumnName = "id")
    private Applicant applicant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldOfEducation() {
        return fieldOfEducation;
    }

    public void setFieldOfEducation(String fieldOfEducation) {
        this.fieldOfEducation = fieldOfEducation;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Long getQualification() {
        return qualification;
    }

    public void setQualification(Long qualification) {
        this.qualification = qualification;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getYearOfGraduation() {
        return yearOfGraduation;
    }

    public void setYearOfGraduation(String yearOfGraduation) {
        this.yearOfGraduation = yearOfGraduation;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public Double getCgpa() {
        return cgpa;
    }

    public void setCgpa(Double cgpa) {
        this.cgpa = cgpa;
    }
}
