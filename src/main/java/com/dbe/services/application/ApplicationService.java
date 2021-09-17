package com.dbe.services.application;

import com.dbe.domain.applicant.AppliedJobView;
import com.dbe.domain.applicant.AppliedPersonelView;
import com.dbe.domain.internal_vacancy.Employee;
import com.dbe.domain.internal_vacancy.InternalApplicantByPositionView;
import com.dbe.domain.internal_vacancy.InternalApplicationView;
import com.dbe.domain.internal_vacancy.InternalPositionByApplicantView;
import com.dbe.services.application.model.ApplicantModel;
import com.dbe.services.application.model.ApplicationModel;
import com.dbe.services.application.model.MultiPartFileModel;
import com.dbe.services.application.model.SearchModel;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

public interface ApplicationService {

    void addOrCreateApplicant(ApplicantModel applicantModel);

    ApplicantModel getApplicantModel();

    void storeFile(MultipartFile file, Long applicationId);
    void storeInternalApplicationFile(MultipartFile file, Long vacancyId);

    ApplicationModel applyForPosition(ApplicationModel model);

    List<AppliedPersonelView> appliedPersonelForVacancy(Long vacancyId) throws ParseException;

    List<AppliedPersonelView> advanceSearch(SearchModel searchModel);

    List<AppliedPersonelView> advanceSearchForExcelExport(SearchModel searchModel);

    List<AppliedJobView> getAppliedJobs();

    void applyForInternalPositions(Long[] ids);

    void closeInternalApplication();

    List<InternalApplicationView> getInternalApplicationByVacancy(Long vacancyId);

    List<InternalApplicantByPositionView> getApplicantsByPosition();
    List<InternalPositionByApplicantView> getPositionByApplicant();

    List<InternalApplicantByPositionView> getApplicantByNonManagerialPosition();
    List<InternalPositionByApplicantView> getNonManagerialPositionByApplicant();

    Employee getEmployeeInfo(String username);

    String getFileNameGivenVacancyAndEmployeeId(Long vacancyId,Long employeeId);


}
