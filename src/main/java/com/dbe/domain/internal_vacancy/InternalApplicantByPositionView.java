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
}
