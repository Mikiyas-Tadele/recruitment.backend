package com.dbe.domain.internal_vacancy;

import javax.persistence.*;

@Entity
@Table(name="INTERNAL_APPLICATION_FILE_COR",schema = "recruitmentDB")
public class InternalFileCorrection {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "file_correction")
    @SequenceGenerator(name = "file_correction",schema = "recruitmentDB",allocationSize =1,sequenceName = "SEQ_FILE_CORRECTION")
    private Long id;
    private Long employeeId;
    private Long vacancyId;
    private String fileName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getVacancyId() {
        return vacancyId;
    }

    public void setVacancyId(Long vacancyId) {
        this.vacancyId = vacancyId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
