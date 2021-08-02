package com.dbe.domain.applicant;

import com.dbe.domain.security.UserEntity;

import javax.persistence.*;

@Entity
@Table(name="applicant_file",schema = "recruitmentDB")
public class ApplicantFile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "applicant_file_sequence")
    @SequenceGenerator(name = "applicant_file_sequence",schema = "recruitmentDB",allocationSize =1,sequenceName = "APP_FILE_SEQ_ID")
    private Long id;
    private String fileName;
    private Long fileSize;
    private Long fileType;
    @ManyToOne
    @JoinColumn(name="userId",referencedColumnName = "id")
    private UserEntity userEntity;
    @ManyToOne
    @JoinColumn(name="applicationId",referencedColumnName = "id")
    private Application application;

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

    public Long getFileType() {
        return fileType;
    }

    public void setFileType(Long fileType) {
        this.fileType = fileType;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
