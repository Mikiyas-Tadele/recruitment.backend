package com.dbe.domain.internal_vacancy;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="RESPOSWITHOUTMSC",schema = "recruitmentDB")
public class ResearchManagerialPosWithoutMsc {
    @Id
    private Long employeeId;
    private String name;
    private String email;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
