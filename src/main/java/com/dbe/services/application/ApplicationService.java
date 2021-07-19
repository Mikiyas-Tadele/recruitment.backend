package com.dbe.services.application;

import com.dbe.services.application.model.ApplicantModel;

public interface ApplicationService {

    void addOrCreateApplicant(ApplicantModel applicantModel);

    ApplicantModel getApplicantModel();

}
