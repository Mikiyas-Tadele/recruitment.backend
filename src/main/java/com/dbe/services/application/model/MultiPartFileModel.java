package com.dbe.services.application.model;

import org.springframework.web.multipart.MultipartFile;

public class MultiPartFileModel {
    MultipartFile file;
    Long vacancyId;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Long getVacancyId() {
        return vacancyId;
    }

    public void setVacancyId(Long vacancyId) {
        this.vacancyId = vacancyId;
    }
}
