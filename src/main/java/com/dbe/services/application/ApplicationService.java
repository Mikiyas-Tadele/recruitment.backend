package com.dbe.services.application;

import com.dbe.services.application.model.ApplicantModel;
import org.springframework.web.multipart.MultipartFile;

public interface ApplicationService {

    void addOrCreateApplicant(ApplicantModel applicantModel);

    ApplicantModel getApplicantModel();

    void storeFile(MultipartFile file);

}
