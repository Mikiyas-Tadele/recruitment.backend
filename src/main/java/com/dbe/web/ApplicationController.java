package com.dbe.web;

import com.dbe.services.application.ApplicationService;
import com.dbe.services.application.model.ApplicantModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(ResourceConstants.APPLICATION_CONTROLLER)
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping("/applicant")
    public void addOrCreateApplicant(@RequestBody ApplicantModel applicantModel){
        applicationService.addOrCreateApplicant(applicantModel);
    }

    @GetMapping("/applicant")
    public ApplicantModel getApplicant(){
        return applicationService.getApplicantModel();
    }
}
