package com.dbe.domain.internal_vacancy;

import javax.persistence.*;

@Entity
@Table(name="internal_application_file",schema = "recruitmentDB")
public class InternalApplicationFile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "iApp_ID")
    @SequenceGenerator(name = "iApp_ID",schema = "recruitmentDB",allocationSize =1,sequenceName = "IAPP_SEQ_ID")
    private Long id;
    private String fileName;
    private Long fileSize;
    @ManyToOne
    @JoinColumn(name="applicationId",referencedColumnName = "id")
    private InternalApplication application;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public InternalApplication getApplication() {
        return application;
    }

    public void setApplication(InternalApplication application) {
        this.application = application;
    }
}
