package com.dbe.web;

import com.dbe.domain.applicant.AppliedJobView;
import com.dbe.domain.applicant.AppliedPersonelView;
import com.dbe.domain.internal_vacancy.InternalApplicantByPositionView;
import com.dbe.domain.internal_vacancy.InternalApplicationView;
import com.dbe.domain.internal_vacancy.InternalPositionByApplicantView;
import com.dbe.services.application.ApplicationService;
import com.dbe.services.application.model.ApplicantModel;
import com.dbe.services.application.model.ApplicationModel;
import com.dbe.services.application.model.SearchModel;
import com.dbe.utilities.exception.StorageException;
import com.dbe.utilities.file_services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
    List<InternalApplicantByPositionView> getInternalApplicantsByPosition(){
        return applicationService.getApplicantsByPosition();
    }

    @GetMapping("/internalPositionByApplicant")
    List<InternalPositionByApplicantView> getInternalPositionByApplicant(){
        return applicationService.getPositionByApplicant();
    }
}
