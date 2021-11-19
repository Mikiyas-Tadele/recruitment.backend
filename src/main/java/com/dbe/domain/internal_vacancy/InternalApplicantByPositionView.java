package com.dbe.domain.internal_vacancy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="INTERNAL_APPLICANT_BY_POSITION",schema = "recruitmentDB")
public class InternalApplicantByPositionView {
    @Id
    private Long employeeId;
    private String employeeName;
    @Column(name="POSITION_ONE")
    private String positionOne;
    @Column(name="POSITION_TWO")
    private String positionTwo;
    @Column(name="POSITION_THREE")
    private String positionThree;
    @Column(name="POSITION_FOUR")
    private String positionFour;
    @Column(name="POSITION_FIVE")
    private String positionFive;
    @Column(name="POSITION_SIX")
    private String positionSix;
    private Long vacancyId1;
    private Long vacancyId2;
    private Long vacancyId3;
    private Long vacancyId4;
    private Long vacancyId5;
    private Long vacancyId6;
    private Long managerial;
    private Long manageria2;
    private Long manageria3;
    private Long managerial4;
    private Long managerial5;
    private Long managerial6;
    private Date appliedDate;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPositionOne() {
        return positionOne;
    }

    public void setPositionOne(String positionOne) {
        this.positionOne = positionOne;
    }

    public String getPositionTwo() {
        return positionTwo;
    }

    public void setPositionTwo(String positionTwo) {
        this.positionTwo = positionTwo;
    }

    public String getPositionThree() {
        return positionThree;
    }

    public void setPositionThree(String positionThree) {
        this.positionThree = positionThree;
    }

    public Long getVacancyId1() {
        return vacancyId1;
    }

    public void setVacancyId1(Long vacancyId1) {
        this.vacancyId1 = vacancyId1;
    }

    public Long getVacancyId2() {
        return vacancyId2;
    }

    public void setVacancyId2(Long vacancyId2) {
        this.vacancyId2 = vacancyId2;
    }

    public Long getVacancyId3() {
        return vacancyId3;
    }

    public void setVacancyId3(Long vacancyId3) {
        this.vacancyId3 = vacancyId3;
    }

    public Long getManagerial() {
        return managerial;
    }

    public void setManagerial(Long managerial) {
        this.managerial = managerial;
    }

    public Long getManageria2() {
        return manageria2;
    }

    public void setManageria2(Long manageria2) {
        this.manageria2 = manageria2;
    }

    public Long getManageria3() {
        return manageria3;
    }

    public void setManageria3(Long manageria3) {
        this.manageria3 = manageria3;
    }

    public Date getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(Date appliedDate) {
        this.appliedDate = appliedDate;
    }

    public String getPositionFour() {
        return positionFour;
    }

    public void setPositionFour(String positionFour) {
        this.positionFour = positionFour;
    }

    public String getPositionFive() {
        return positionFive;
    }

    public void setPositionFive(String positionFive) {
        this.positionFive = positionFive;
    }

    public String getPositionSix() {
        return positionSix;
    }

    public void setPositionSix(String positionSix) {
        this.positionSix = positionSix;
    }

    public Long getVacancyId4() {
        return vacancyId4;
    }

    public void setVacancyId4(Long vacancyId4) {
        this.vacancyId4 = vacancyId4;
    }

    public Long getVacancyId5() {
        return vacancyId5;
    }

    public void setVacancyId5(Long vacancyId5) {
        this.vacancyId5 = vacancyId5;
    }

    public Long getVacancyId6() {
        return vacancyId6;
    }

    public void setVacancyId6(Long vacancyId6) {
        this.vacancyId6 = vacancyId6;
    }

    public Long getManagerial4() {
        return managerial4;
    }

    public void setManagerial4(Long managerial4) {
        this.managerial4 = managerial4;
    }

    public Long getManagerial5() {
        return managerial5;
    }

    public void setManagerial5(Long managerial5) {
        this.managerial5 = managerial5;
    }

    public Long getManagerial6() {
        return managerial6;
    }

    public void setManagerial6(Long managerial6) {
        this.managerial6 = managerial6;
    }
}
