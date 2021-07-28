package com.dbe.services.application;

import com.dbe.domain.applicant.AppliedPersonelView;
import com.dbe.services.application.model.ApplicantModel;
import com.dbe.services.application.model.ApplicationModel;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

public interface ApplicationService {

    void addOrCreateApplicant(ApplicantModel applicantModel);

    ApplicantModel getApplicantModel();

    void storeFile(MultipartFile file);

    void applyForPosition(ApplicationModel model);

    List<AppliedPersonelView> appliedPersonelForVacancy(Long vacancyId) throws ParseException;

}
