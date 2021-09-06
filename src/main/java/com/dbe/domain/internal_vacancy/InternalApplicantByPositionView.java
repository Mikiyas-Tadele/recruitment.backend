package com.dbe.domain.internal_vacancy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
    private Long vacancyId1;
    private Long vacancyId2;
    private Long vacancyId3;
    private Long managerial;
    private Long manageria2;
    private Long manageria3;

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
}
