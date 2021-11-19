package com.dbe.web;

import com.dbe.domain.applicant.AppliedJobView;
import com.dbe.domain.applicant.AppliedPersonelView;
import com.dbe.domain.applicant.FinalResultView;
import com.dbe.domain.internal_vacancy.Employee;
import com.dbe.domain.internal_vacancy.InternalApplicantByPositionView;
import com.dbe.domain.internal_vacancy.InternalApplicationView;
import com.dbe.domain.internal_vacancy.InternalPositionByApplicantView;
import com.dbe.services.application.ApplicationService;
import com.dbe.services.application.model.*;
import com.dbe.utilities.exception.StorageException;
import com.dbe.utilities.file_services.FileStorageService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(ResourceConstants.APPLICATION_CONTROLLER)
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private FileStorageService storageService;

    @PostMapping("/applicant")
    public void addOrCreateApplicant(@RequestBody ApplicantModel applicantModel){
        applicationService.addOrCreateApplicant(applicantModel);
    }

    @GetMapping("/applicant")
    public ApplicantModel getApplicant(){
        return applicationService.getApplicantModel();
    }

    @RequestMapping("/store")
    public void storeFile(@RequestParam MultipartFile file,@RequestParam Long applicationId) {
        applicationService.storeFile(file, applicationId);

    }

    @RequestMapping("/internal-application-store")
    public void storeInternalApplication(@RequestParam MultipartFile file,@RequestParam Long vacancyId){
        applicationService.storeInternalApplicationFile(file,vacancyId);
    }

    @RequestMapping("/internal-application-store-with-correction")
    public void storeInternalApplicationFileWithCorrection(@RequestParam MultipartFile file,@RequestParam Long vacancyId){
        applicationService.storeInternalApplicationWithCorrection(file,vacancyId);
    }


    @RequestMapping("/interanal-application-closing")
    public void closeInternalApplication(){
        applicationService.closeInternalApplication();
    }

    @RequestMapping("/download-Internal-applicant-File")
    public ResponseEntity<Resource> downloadInternalApplicantFile(@RequestParam Long employeeId,@RequestParam Long vacancyId,
                                             HttpServletRequest request) {
        Resource resource = storageService.loadInternalApplicantFileAsResource(employeeId,vacancyId);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            throw new StorageException("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping("/apply")
    public ApplicationModel applyForPosition(@RequestBody ApplicationModel applicationModel){
       return applicationService.applyForPosition(applicationModel);
    }

    @GetMapping("/appliedPersonel/{id}")
    public List<AppliedPersonelView> getAppliedPersonelForVacancy(@PathVariable Long id) throws ParseException {
        return applicationService.appliedPersonelForVacancy(id);
    }
    @RequestMapping("/downloadFile")
    public ResponseEntity<Resource> download(@RequestParam Long documentId, @RequestParam Long applicationId, HttpServletRequest request) {
        Resource resource = storageService.loadAsResource(documentId, applicationId);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            throw new StorageException("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping("/search")
    public List<AppliedPersonelView> advanceSearch(@RequestBody SearchModel searchModel){
        return  applicationService.advanceSearch(searchModel);
    }

    @PostMapping("/search-excel")
    public List<AppliedPersonelView> advanceSearchForExcel(@RequestBody SearchModel searchModel){
        return applicationService.advanceSearchForExcelExport(searchModel);
    }


    @GetMapping("/applied-jobs")
    public List<AppliedJobView> getAppliedJobs(){
        return applicationService.getAppliedJobs();
    }

    @PostMapping("/internalApplication")
    public void applyForInternalPosition(@RequestBody Long[] ids){
        applicationService.applyForInternalPositions(ids);
    }

    @GetMapping("/internalApplications/{id}")
    List<InternalApplicationView> getInternalApplicationsByVacancy(@PathVariable Long id){
        return  applicationService.getInternalApplicationByVacancy(id);
    }

    @GetMapping("/internalApplicantsByPosition")
    public List<InternalApplicantByPositionView> getInternalApplicantsByPosition(){
        return applicationService.getApplicantsByPosition();
    }

    @GetMapping("/internalPositionByApplicant")
    public List<InternalPositionByApplicantView> getInternalPositionByApplicant(){
        List<InternalPositionByApplicantView> i=applicationService.getPositionByApplicant();
        return applicationService.getPositionByApplicant();
    }

    @GetMapping("/internalApplicantsByNonManagerPosition")
    public List<InternalApplicantByPositionView> getInternalApplicantsByNonManagerPosition(){
        return applicationService.getApplicantByNonManagerialPosition();
    }

    @GetMapping("/internalNonManagerPositionByApplicant")
    public List<InternalPositionByApplicantView> getInternalNonManagerPositionByApplicant(){
        return applicationService.getNonManagerialPositionByApplicant();
    }

    @GetMapping("/employeeInfo/{username:.+}")
    public Employee getEmployeeInfo(@PathVariable String username){
        Employee employee=applicationService.getEmployeeInfo(username);
        return employee;
    }

    @GetMapping("/fileNameToDownload/{vacancyId}/{employeeId}")
    public String getFileNameToDownload(@PathVariable("vacancyId")Long vacancyId,@PathVariable("employeeId")Long employeeId){
        return JSONObject.quote(applicationService.getFileNameGivenVacancyAndEmployeeId(vacancyId,employeeId));
    }

    @GetMapping("/fileNameToDownloadExternal/{vacancyId}/{userId}")
    public String getFileNameToDownloadForExternalUse(@PathVariable("vacancyId")Long vacancyId,@PathVariable("userId")Long userId){
        return JSONObject.quote(applicationService.getFileNameGivenVacancy(vacancyId,userId ));
    }

    @GetMapping("/missingfiles")
    public void missingFile(){
        try {
            applicationService.ApllicantsForResearchWithoutMsc();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/emailsToBesent")
    public void emailsToBesent(){
        try {
            applicationService.ApllicantsWithFileError();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/organizeFiles")
    public void organizeFiles(){
        try {
            storageService.organizeApplicationLetters();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/all-applicants-for-wriiten-exam/{id}")
    public List<ApplicantForWrittenExamModel> getAllApplicantsSelectedForWrittenExam(@PathVariable Long id){
        return applicationService.getApplicantsForWrittenExam(id);
    }

    @GetMapping("/all-applicants-for-interview/{id}")
    public List<ApplicantForInterviewModel> getAllApplicantsSelectedForInterview(@PathVariable Long id){
        return applicationService.getApplicantsForInterview(id);
    }

    @PostMapping("/applicants-for-written-exam")
    public void addOrUpdateApplicantsForWritenExam(@RequestBody List<ApplicantForWrittenExamModel> writtenExamModels){
        applicationService.addOrUpdateApplicantsSelectedForWrittenExam(writtenExamModels);
    }

    @PostMapping("/remove-applicants-for-written-exam")
    public void removeApplicantsForWritenExam(@RequestBody List<ApplicantForWrittenExamModel> writtenExamModels){
        applicationService.removeApplicantsSelectedForWrittenExam(writtenExamModels);
    }

    @PostMapping("/remove-applicants-for-interview-exam")
    public void removeApplicantsForInterviewExam(@RequestBody List<ApplicantForInterviewModel> interviewModels){
        applicationService.removeApplicantsSelectedForInterview(interviewModels);
    }

    @PostMapping("/applicants-for-interview-exam")
    public void addOrUpdateApplicantsForInterview(@RequestBody List<ApplicantForInterviewModel> interviewModels){
        applicationService.addOrUpdateApplicantsSelectedForInterview(interviewModels);
    }

    @PostMapping("/move-applicants-to-final-stage")
    public void moveApplicantsToFinalStage(@RequestBody List<ApplicantForInterviewModel> interviewModels){
        applicationService.movetoFinalStage(interviewModels);
    }

    @GetMapping("/employeeApplicationInfo/{username:.+}")
    public List<InternalApplicationModel> getEmployeeApplicationInfo(@PathVariable String username){
         return applicationService.getInternalApplicationInfo(username);
    }

    @GetMapping("/deleteAttachedFile/{id}")
    public void deleteFile(@PathVariable Long id){
        storageService.deleteInternalFile(id);
    }

    @GetMapping("/closeFileAttachementSession")
    public void closeFileAttachementSession(){
        applicationService.closeFileAttachementSession();
    }

    @GetMapping("/applicants-final-result/{id}")
    public List<FinalResultView> getAllApplicantsWithFinalResult(@PathVariable Long id){
        return applicationService.getAllApplicantsWithFinalResults(id);
    }



}
