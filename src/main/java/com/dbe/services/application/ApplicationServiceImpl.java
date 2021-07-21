package com.dbe.services.application;

import com.dbe.domain.applicant.Applicant;
import com.dbe.domain.applicant.ApplicantFile;
import com.dbe.domain.applicant.EducationalBackground;
import com.dbe.domain.applicant.WorkExperience;
import com.dbe.domain.security.UserEntity;
import com.dbe.repositories.applicant.ApplicantFileRepository;
import com.dbe.repositories.applicant.ApplicantRepository;
import com.dbe.repositories.security.UserRepository;
import com.dbe.security.services.UserPrinciple;
import com.dbe.services.application.model.ApplicantModel;
import com.dbe.services.application.model.EducationalBackgroundModel;
import com.dbe.services.application.model.WorkExperienceModel;
import com.dbe.utilities.current_users.AuthenticationFacade;
import com.dbe.utilities.current_users.IAuthenticationFacade;
import com.dbe.utilities.file_services.FileModel;
import com.dbe.utilities.file_services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class ApplicationServiceImpl implements  ApplicationService {

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private FileStorageService storageService;

    @Autowired
    private ApplicantFileRepository applicantFileRepository;



    @Override
    public void addOrCreateApplicant(ApplicantModel applicantModel) {
        IAuthenticationFacade authenticationFacade= new AuthenticationFacade();
        UserPrinciple authentication= (UserPrinciple) authenticationFacade.getAuthentication().getPrincipal();
        Optional<UserEntity> userEntity= userRepository.findByUsername(authentication.getUsername());
        Applicant applicant=applicantRepository.findApplicantByUserId(userEntity.get().getId());
        if(applicant==null){
            applicant=new Applicant();
            applicant.setUserEntity(userEntity.get());
        }
        applicant.setDateOfBirth(applicantModel.getDateOfBirth());
        applicant.setDisability(applicantModel.getDisability());
        applicant.setfPhone(applicantModel.getfPhone());
        applicant.setmPhone1(applicantModel.getmPhone1());
        applicant.setmPhone2(applicantModel.getmPhone2());
        applicant.setGender(applicantModel.getGender());
        applicant.setFirstName(applicantModel.getFirstName());
        applicant.setMiddleName(applicantModel.getMiddleName());
        applicant.setLastName(applicantModel.getLastName());
        getEducationalBackgroundSet(applicantModel, applicant);
        getWorkExperiences(applicantModel, applicant);

        applicantRepository.save(applicant);
    }


    @Override
    public ApplicantModel getApplicantModel() {
        IAuthenticationFacade authenticationFacade= new AuthenticationFacade();
        UserPrinciple authentication= (UserPrinciple) authenticationFacade.getAuthentication().getPrincipal();
        Optional<UserEntity> userEntity= userRepository.findByUsername(authentication.getUsername());

        Applicant applicant=applicantRepository.findApplicantByUserId(userEntity.get().getId());
        ApplicantModel applicantModel=new ApplicantModel();
        applicantModel.setDateOfBirth(applicant.getDateOfBirth());
        applicantModel.setDisability(applicant.getDisability());
        applicantModel.setfPhone(applicant.getfPhone());
        applicantModel.setmPhone1(applicant.getmPhone1());
        applicantModel.setmPhone2(applicant.getmPhone2());
        applicantModel.setGender(applicant.getGender());
        applicantModel.setFirstName(applicant.getFirstName());
        applicantModel.setMiddleName(applicant.getMiddleName());
        applicantModel.setLastName(applicant.getLastName());
        List<EducationalBackgroundModel> educationalBackgroundModels=new ArrayList<>();
        for (EducationalBackground educationalBackground:applicant.getEducationalBackgrounds()) {
            EducationalBackgroundModel educationalBackgroundModel=new EducationalBackgroundModel();
            educationalBackgroundModel.setApplicantId(applicant.getId());
            educationalBackgroundModel.setFieldOfEducation(educationalBackground.getFieldOfEducation());
            educationalBackgroundModel.setQualification(educationalBackground.getQualification());
            educationalBackgroundModel.setUniversity(educationalBackground.getUniversity());
            educationalBackgroundModel.setYearOfGraduation(educationalBackground.getYearOfGraduation());
            educationalBackgroundModel.setCgpa(educationalBackground.getCgpa());

            educationalBackgroundModels.add(educationalBackgroundModel);
        }
        applicantModel.setEducationalBackgrounds(educationalBackgroundModels);

        List<WorkExperienceModel> workExperienceModels=new ArrayList<>();
        for (WorkExperience workExperience:applicant.getWorkExperiences()) {
            WorkExperienceModel workExperienceModel=new WorkExperienceModel();
            workExperienceModel.setApplicantId(applicant.getId());
            workExperienceModel.setEndDate(workExperience.getEndDate());
            workExperienceModel.setSalary(workExperience.getSalary());
            workExperienceModel.setStartDate(workExperience.getStartDate());
            workExperienceModel.setOrganization(workExperience.getOrganization());
            workExperienceModel.setPosition(workExperience.getPosition());

            workExperienceModels.add(workExperienceModel);
        }

        applicantModel.setWorkExperiences(workExperienceModels);

        return applicantModel;

    }

    @Override
    public void storeFile(MultipartFile file) {
        IAuthenticationFacade authenticationFacade= new AuthenticationFacade();
        UserPrinciple authentication= (UserPrinciple) authenticationFacade.getAuthentication().getPrincipal();
        Optional<UserEntity> userEntity= userRepository.findByUsername(authentication.getUsername());

        FileModel fileModel = new FileModel();
        fileModel.setFileName(StringUtils.cleanPath(file.getOriginalFilename()));
        fileModel.setFileSize(file.getSize());

        storageService.store(file, fileModel);
        ApplicantFile applicantFile=new ApplicantFile();
        applicantFile.setFileName(fileModel.getFileName());
        applicantFile.setFileSize(fileModel.getFileSize());
        applicantFile.setUserEntity(userEntity.get());

       applicantFileRepository.save(applicantFile);
    }

    private void getWorkExperiences(ApplicantModel applicantModel, Applicant applicant) {
        if(applicantModel.getWorkExperiences().size()>0){
            Set<WorkExperience> workExperiences=new HashSet<>();
            for (WorkExperienceModel workExperienceModel:applicantModel.getWorkExperiences()) {
                WorkExperience workExperience=new WorkExperience();
                workExperience.setApplicant(applicant);
                workExperience.setEndDate(workExperienceModel.getEndDate());
                workExperience.setSalary(workExperienceModel.getSalary());
                workExperience.setStartDate(workExperienceModel.getStartDate());
                workExperience.setOrganization(workExperienceModel.getOrganization());
                workExperience.setPosition(workExperienceModel.getPosition());

                workExperiences.add(workExperience);
            }

            applicant.setWorkExperiences(workExperiences);
        }
    }

    private void getEducationalBackgroundSet(ApplicantModel applicantModel, Applicant applicant) {
        if(applicantModel.getEducationalBackgrounds().size()>0){
            Set<EducationalBackground> educationalBackgrounds=new HashSet<>();
            for (EducationalBackgroundModel educationalBackgroundModel:applicantModel.getEducationalBackgrounds()) {
                EducationalBackground educationalBackground=new EducationalBackground();
                educationalBackground.setApplicant(applicant);
                educationalBackground.setFieldOfEducation(educationalBackgroundModel.getFieldOfEducation());
                educationalBackground.setQualification(educationalBackgroundModel.getQualification());
                educationalBackground.setUniversity(educationalBackgroundModel.getUniversity());
                educationalBackground.setYearOfGraduation(educationalBackgroundModel.getYearOfGraduation());
                educationalBackground.setCgpa(educationalBackgroundModel.getCgpa());

                educationalBackgrounds.add(educationalBackground);
            }

            applicant.setEducationalBackgrounds(educationalBackgrounds);
        }
    }
}
