package com.dbe.domain.internal_vacancy;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="internal_application",schema = "recruitmentDB")
public class InternalApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "iApp_ID")
    @SequenceGenerator(name = "iApp_ID",schema = "recruitmentDB",allocationSize =1,sequenceName = "IAPP_ID_SEQ")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "VACANCYID",referencedColumnName = "id")
    private InternalVacancy internalVacancy;
    private Long employeeId;
    @Column(name="APPLIED_DATE")
    private Date appliedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public InternalVacancy getInternalVacancy() {
        return internalVacancy;
    }

    public void setInternalVacancy(InternalVacancy internalVacancy) {
        this.internalVacancy = internalVacancy;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Date getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(Date appliedDate) {
        this.appliedDate = appliedDate;
    }
}
