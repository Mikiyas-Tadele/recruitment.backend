package com.dbe.web;

import com.dbe.domain.applicant.AppliedPersonelView;
import com.dbe.services.application.ApplicationService;
import com.dbe.services.application.model.ApplicantModel;
import com.dbe.services.application.model.ApplicationModel;
import com.dbe.services.application.model.SearchModel;
import com.dbe.utilities.exception.StorageException;
import com.dbe.utilities.file_services.FileModel;
import com.dbe.utilities.file_services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
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
    public void storeFile(@RequestParam MultipartFile file) {
        applicationService.storeFile(file);

    }
    @PostMapping("/apply")
    public void applyForPosition(@RequestBody ApplicationModel applicationModel){
        applicationService.applyForPosition(applicationModel);
    }

    @GetMapping("/appliedPersonel/{id}")
    public List<AppliedPersonelView> getAppliedPersonelForVacancy(@PathVariable Long id) throws ParseException {
        return applicationService.appliedPersonelForVacancy(id);
    }
    @RequestMapping("/downloadFile")
    public ResponseEntity<Resource> download(@RequestParam Long documentId, HttpServletRequest request) {
        Resource resource = storageService.loadAsResource(documentId);

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
}
