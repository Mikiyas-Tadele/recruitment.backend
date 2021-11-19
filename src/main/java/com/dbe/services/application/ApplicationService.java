package com.dbe.services.application;

import com.dbe.domain.applicant.AppliedJobView;
import com.dbe.domain.applicant.AppliedPersonelView;
import com.dbe.domain.applicant.FinalResultView;
import com.dbe.domain.internal_vacancy.Employee;
import com.dbe.domain.internal_vacancy.InternalApplicantByPositionView;
import com.dbe.domain.internal_vacancy.InternalApplicationView;
import com.dbe.domain.internal_vacancy.InternalPositionByApplicantView;
import com.dbe.services.application.model.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;

public interface ApplicationService {

    void addOrCreateApplicant(ApplicantModel applicantModel);

    ApplicantModel getApplicantModel();

    void storeFile(MultipartFile file, Long applicationId);
    void storeInternalApplicationFile(MultipartFile file, Long vacancyId);

    void storeInternalApplicationWithCorrection(MultipartFile file, Long vacancyId);

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
    String getFileNameGivenVacancy(Long vacancyId, Long userId);

    void sendToEmployeesWithMissingFiles() throws UnsupportedEncodingException, MessagingException;

    void addOrUpdateApplicantsSelectedForWrittenExam(List<ApplicantForWrittenExamModel> applicantForWrittenExamModels);

    void removeApplicantsSelectedForWrittenExam(List<ApplicantForWrittenExamModel> applicantForWrittenExamModels);

    void addOrUpdateApplicantsSelectedForInterview(List<ApplicantForInterviewModel> applicantForInterviewModels);

    void removeApplicantsSelectedForInterview(List<ApplicantForInterviewModel> applicantForInterviewModels);

    List<ApplicantForWrittenExamModel> getApplicantsForWrittenExam(Long vacancyId);

    List<ApplicantForInterviewModel> getApplicantsForInterview(Long vacancyId);

    List<InternalApplicationModel> getInternalApplicationInfo(String empId);

    void closeFileAttachementSession();

    void ApllicantsForResearchWithoutMsc() throws UnsupportedEncodingException, MessagingException;
    void ApllicantsWithFileError() throws UnsupportedEncodingException, MessagingException;

    List<FinalResultView> getAllApplicantsWithFinalResults(Long vacancyId);

    void movetoFinalStage(List<ApplicantForInterviewModel> applicantForInterviewModels);


}
