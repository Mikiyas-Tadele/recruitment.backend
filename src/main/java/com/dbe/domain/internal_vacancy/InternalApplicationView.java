package com.dbe.domain.internal_vacancy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="INTERNALAPPLICATIONVIEW",schema = "recruitmentDB")
public class InternalApplicationView {
    @Id
    private Long id;
    private String name;
    private String position;
    @Column(name="WORK_UNIT")
    private String workUnit;
    private String location;
    @Column(name="APPLIED_DATE")
    private Date appliedDate;
    private Long vacancyId;
    private Long employeeId;
    private String  appliedPosition;
    @Column(name="POSITION_ORDER")
    private Long positionOrder;
    @Column(name="PLACEMENT_OF_WORK")
    private String placementOfWork;
    private Long managerial;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(Date appliedDate) {
        this.appliedDate = appliedDate;
    }

    public Long getVacancyId() {
        return vacancyId;
    }

    public void setVacancyId(Long vacancyId) {
        this.vacancyId = vacancyId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getAppliedPosition() {
        return appliedPosition;
    }

    public void setAppliedPosition(String appliedPosition) {
        this.appliedPosition = appliedPosition;
    }

    public Long getPositionOrder() {
        return positionOrder;
    }

    public void setPositionOrder(Long positionOrder) {
        this.positionOrder = positionOrder;
    }

    public String getPlacementOfWork() {
        return placementOfWork;
    }

    public void setPlacementOfWork(String placementOfWork) {
        this.placementOfWork = placementOfWork;
    }

    public Long getManagerial() {
        return managerial;
    }

    public void setManagerial(Long managerial) {
        this.managerial = managerial;
    }
}
