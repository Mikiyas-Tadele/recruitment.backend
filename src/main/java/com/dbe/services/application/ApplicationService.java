package com.dbe.services.application;

import com.dbe.domain.applicant.AppliedJobView;
import com.dbe.domain.applicant.AppliedPersonelView;
import com.dbe.services.application.model.ApplicantModel;
import com.dbe.services.application.model.ApplicationModel;
import com.dbe.services.application.model.SearchModel;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

public interface ApplicationService {

    void addOrCreateApplicant(ApplicantModel applicantModel);

    ApplicantModel getApplicantModel();

    void storeFile(MultipartFile file, Long applicationId);

    ApplicationModel applyForPosition(ApplicationModel model);

    List<AppliedPersonelView> appliedPersonelForVacancy(Long vacancyId) throws ParseException;

    List<AppliedPersonelView> advanceSearch(SearchModel searchModel);

    List<AppliedPersonelView> advanceSearchForExcelExport(SearchModel searchModel);

    List<AppliedJobView> getAppliedJobs();


}
