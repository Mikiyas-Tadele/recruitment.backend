package com.dbe.domain.applicant;

import com.dbe.domain.security.UserEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="applicant",schema = "recruitmentDB")
public class Applicant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "applicant_sequence")
    @SequenceGenerator(name = "applicant_sequence",schema = "recruitmentDB",allocationSize =1,sequenceName = "APP_SEQ_ID")
    private Long id;
    @OneToOne
    @JoinColumn(name = "userId",referencedColumnName = "id")
    private UserEntity userEntity;
    private String firstName;
    private String middleName;
    private String lastName;
    @Column(name="dob")
    private Date dateOfBirth;
    private String gender;
    private String disability;
    @Column(name="mobilephone1")
    private String mPhone1;
    @Column(name="mobilephone2")
    private String mPhone2;
    @Column(name="fixedlinephone")
    private String fPhone;
    @OneToMany(mappedBy = "applicant",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<EducationalBackground> educationalBackgrounds = new HashSet<>();
    @OneToMany(mappedBy = "applicant",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<WorkExperience> workExperiences = new HashSet<>();
    @OneToMany(mappedBy = "applicant",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Application> applications;
    @OneToMany(mappedBy = "applicant",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Certification> certifications=new HashSet<>();
    @Column(name="DISABILITY_DESCRIPTION")
    private String  disabilityDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDisability() {
        return disability;
    }

    public void setDisability(String disability) {
        this.disability = disability;
    }

    public String getmPhone1() {
        return mPhone1;
    }

    public void setmPhone1(String mPhone1) {
        this.mPhone1 = mPhone1;
    }

    public String getmPhone2() {
        return mPhone2;
    }

    public void setmPhone2(String mPhone2) {
        this.mPhone2 = mPhone2;
    }

    public String getfPhone() {
        return fPhone;
    }

    public void setfPhone(String fPhone) {
        this.fPhone = fPhone;
    }

    public Set<EducationalBackground> getEducationalBackgrounds() {
        return educationalBackgrounds;
    }

    public void setEducationalBackgrounds(Set<EducationalBackground> educationalBackgrounds) {
        this.educationalBackgrounds = educationalBackgrounds;
    }

    public Set<WorkExperience> getWorkExperiences() {
        return workExperiences;
    }

    public void setWorkExperiences(Set<WorkExperience> workExperiences) {
        this.workExperiences = workExperiences;
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }

    public String getDisabilityDescription() {
        return disabilityDescription;
    }

    public void setDisabilityDescription(String disabilityDescription) {
        this.disabilityDescription = disabilityDescription;
    }

    public Set<Certification> getCertifications() {
        return certifications;
    }

    public void setCertifications(Set<Certification> certifications) {
        this.certifications = certifications;
    }
}
