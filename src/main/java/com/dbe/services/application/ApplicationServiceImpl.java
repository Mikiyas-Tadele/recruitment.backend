package com.dbe.services.application;

import com.dbe.domain.applicant.*;
import com.dbe.domain.security.UserEntity;
import com.dbe.repositories.applicant.*;
import com.dbe.repositories.security.UserRepository;
import com.dbe.repositories.vacancyRepository.VacancyRepository;
import com.dbe.security.services.UserPrinciple;
import com.dbe.services.application.model.*;
import com.dbe.services.settings.SettingService;
import com.dbe.utilities.current_users.AuthenticationFacade;
import com.dbe.utilities.current_users.IAuthenticationFacade;
import com.dbe.utilities.exception.ApplicationException;
import com.dbe.utilities.file_services.FileModel;
import com.dbe.utilities.file_services.FileStorageService;
import com.dbe.utilities.models.SystemConstants;
import com.dbe.utilities.specifications.AppliedPersonelSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.springframework.data.jpa.domain.Specifications.where;

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

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private VacancyRepository vacancyRepository;

    @Autowired
    private AppliedPersonelViewRepository appliedPersonelViewRepository;

    @Autowired
    private AppliedJobViewRepository appliedJobViewRepository;

    @Autowired
    private SettingService settingService;



    @Override
    public void addOrCreateApplicant(ApplicantModel applicantModel) {
        IAuthenticationFacade authenticationFacade= new AuthenticationFacade();
        UserPrinciple authentication= (UserPrinciple) authenticationFacade.getAuthentication().getPrincipal();
        Optional<UserEntity> userEntity= userRepository.findByUsernameAndEnabled(authentication.getUsername(),true);
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
        applicant.setDisabilityDescription(applicantModel.getDisabilityDescription());
        getEducationalBackgroundSet(applicantModel, applicant);
        getWorkExperiences(applicantModel, applicant);
        getCertifications(applicantModel,applicant);

        applicantRepository.save(applicant);
    }


    @Override
    public ApplicantModel getApplicantModel() {
        IAuthenticationFacade authenticationFacade= new AuthenticationFacade();
        UserPrinciple authentication= (UserPrinciple) authenticationFacade.getAuthentication().getPrincipal();
        Optional<UserEntity> userEntity= userRepository.findByUsernameAndEnabled(authentication.getUsername(),true);

        Applicant applicant=applicantRepository.findApplicantByUserId(userEntity.get().getId());
        ApplicantModel applicantModel = new ApplicantModel();
        if(applicant!=null) {
            applicantModel.setDateOfBirth(applicant.getDateOfBirth());
            applicantModel.setDisability(applicant.getDisability());
            applicantModel.setfPhone(applicant.getfPhone());
            applicantModel.setmPhone1(applicant.getmPhone1());
            applicantModel.setmPhone2(applicant.getmPhone2());
            applicantModel.setGender(applicant.getGender());
            applicantModel.setFirstName(applicant.getFirstName());
            applicantModel.setMiddleName(applicant.getMiddleName());
            applicantModel.setLastName(applicant.getLastName());
            applicantModel.setUserId(userEntity.get().getId());
            applicantModel.setId(applicant.getId());
            applicantModel.setDisabilityDescription(applicant.getDisabilityDescription());
            List<EducationalBackgroundModel> educationalBackgroundModels = new ArrayList<>();
            for (EducationalBackground educationalBackground : applicant.getEducationalBackgrounds()) {
                EducationalBackgroundModel educationalBackgroundModel = new EducationalBackgroundModel();
                educationalBackgroundModel.setApplicantId(applicant.getId());
                educationalBackgroundModel.setFieldOfEducation(educationalBackground.getFieldOfEducation());
                educationalBackgroundModel.setSpecialization(educationalBackground.getSpecialization());
                educationalBackgroundModel.setQualification(educationalBackground.getQualification());
                educationalBackgroundModel.setQualificationDesc(settingService.getDescription(educationalBackground.getQualification()));
                educationalBackgroundModel.setUniversity(educationalBackground.getUniversity());
                educationalBackgroundModel.setYearOfGraduation(educationalBackground.getYearOfGraduation());
                educationalBackgroundModel.setCgpa(educationalBackground.getCgpa());
                educationalBackgroundModel.setId(educationalBackground.getId());

                educationalBackgroundModels.add(educationalBackgroundModel);
            }
            applicantModel.setEducationalBackgrounds(educationalBackgroundModels);

            List<WorkExperienceModel> workExperienceModels = new ArrayList<>();
            for (WorkExperience workExperience : applicant.getWorkExperiences()) {
                WorkExperienceModel workExperienceModel = new WorkExperienceModel();
                workExperienceModel.setApplicantId(applicant.getId());
                workExperienceModel.setEndDate(workExperience.getEndDate());
                workExperienceModel.setSalary(workExperience.getSalary());
                workExperienceModel.setStartDate(workExperience.getStartDate());
                workExperienceModel.setOrganization(workExperience.getOrganization());
                workExperienceModel.setPosition(workExperience.getPosition());
                workExperienceModel.setId(workExperience.getId());

                workExperienceModels.add(workExperienceModel);
            }

            applicantModel.setWorkExperiences(workExperienceModels);

            List<CertificationModel> certificationModels=new ArrayList<>();
            for (Certification certification: applicant.getCertifications()) {
                CertificationModel certificationModel=new CertificationModel();
                certificationModel.setId(certification.getId());
                certificationModel.setApplicantId(certification.getApplicant().getId());
                certificationModel.setAwardDate(certification.getAwardDate());
                certificationModel.setTitle(certification.getTitle());
                certificationModel.setInstitution(certification.getInstution());

                certificationModels.add(certificationModel);
            }

            applicantModel.setCertifications(certificationModels);

        }

        return applicantModel;

    }

    @Override
    public void storeFile(MultipartFile file, Long applicationId) {
        IAuthenticationFacade authenticationFacade= new AuthenticationFacade();
        UserPrinciple authentication= (UserPrinciple) authenticationFacade.getAuthentication().getPrincipal();
        Optional<UserEntity> userEntity= userRepository.findByUsernameAndEnabled(authentication.getUsername(),true);

        FileModel fileModel = new FileModel();
        fileModel.setFileName(StringUtils.cleanPath(file.getOriginalFilename()));
        fileModel.setFileSize(file.getSize());
        if(applicationId==0){
            ApplicantFile existingApplicantFile=applicantFileRepository.findByUserId(userEntity.get().getId(), SystemConstants.CV_FILE);
            if(existingApplicantFile!=null){
                storageService.delete(userEntity.get().getId(), SystemConstants.CV_FILE);
            }
        }
        storageService.store(file, fileModel);
        ApplicantFile applicantFile=new ApplicantFile();
        applicantFile.setFileName(fileModel.getFileName());
        applicantFile.setFileSize(fileModel.getFileSize());
        applicantFile.setUserEntity(userEntity.get());
        applicantFile.setApplication(applicationRepository.findOne(applicationId));
        applicantFile.setFileType(applicationId==0?SystemConstants.CV_FILE:SystemConstants.QUALIFICATION_FILE);

       applicantFileRepository.save(applicantFile);
    }

    private void getWorkExperiences(ApplicantModel applicantModel, Applicant applicant) {
        if(applicantModel.getWorkExperiences().size()>0){
            Set<WorkExperience> workExperiences=new HashSet<>();
            for (WorkExperienceModel workExperienceModel:applicantModel.getWorkExperiences()) {
                WorkExperience workExperience= workExperienceModel.getId()!=null?applicantRepository.findApplicantWorkExp(workExperienceModel.getId()):new WorkExperience();
                workExperience.setApplicant(applicant);
                workExperience.setEndDate(workExperienceModel.getEndDate());
                workExperience.setSalary(workExperienceModel.getSalary());
                workExperience.setStartDate(workExperienceModel.getStartDate());
                workExperience.setOrganization(workExperienceModel.getOrganization());
                workExperience.setPosition(workExperienceModel.getPosition());

                workExperiences.add(workExperience);
            }
            applicant.getWorkExperiences().clear();
            applicant.getWorkExperiences().addAll(workExperiences);
        }
    }

    private void getCertifications(ApplicantModel applicantModel,Applicant applicant){
            Set<Certification> certifications=new HashSet<>();
            for (CertificationModel certificationModel:applicantModel.getCertifications()) {
                Certification certification= certificationModel.getId()!=null?applicantRepository.findApllicantByCertifications(certificationModel.getId()):new Certification();
                certification.setApplicant(applicant);
                certification.setAwardDate(certificationModel.getAwardDate());
                certification.setInstution(certificationModel.getInstitution());
                certification.setTitle(certificationModel.getTitle());

                certifications.add(certification);
            }
            if(applicant.getId()!=null){
                applicant.getCertifications().removeAll(applicant.getCertifications());
            }
            applicant.getCertifications().addAll(certifications);
    }

    private void getEducationalBackgroundSet(ApplicantModel applicantModel, Applicant applicant) {
        if(applicantModel.getEducationalBackgrounds().size()>0){
            Set<EducationalBackground> educationalBackgrounds=new HashSet<>();
            for (EducationalBackgroundModel educationalBackgroundModel:applicantModel.getEducationalBackgrounds()) {
                EducationalBackground educationalBackground=educationalBackgroundModel.getId()!=null?applicantRepository.findApplicantEduBkg(educationalBackgroundModel.getId()):new EducationalBackground();
                educationalBackground.setApplicant(applicant);
                educationalBackground.setFieldOfEducation(educationalBackgroundModel.getFieldOfEducation());
                educationalBackground.setQualification(educationalBackgroundModel.getQualification());
                educationalBackground.setUniversity(educationalBackgroundModel.getUniversity());
                educationalBackground.setYearOfGraduation(educationalBackgroundModel.getYearOfGraduation());
                educationalBackground.setSpecialization(educationalBackgroundModel.getSpecialization());
                educationalBackground.setCgpa(educationalBackgroundModel.getCgpa());
                educationalBackgrounds.add(educationalBackground);
            }

            applicant.getEducationalBackgrounds().clear();
            applicant.getEducationalBackgrounds().addAll(educationalBackgrounds);
        }
    }

    @Override
    public ApplicationModel applyForPosition(ApplicationModel model) {
        IAuthenticationFacade authenticationFacade= new AuthenticationFacade();
        UserPrinciple authentication= (UserPrinciple) authenticationFacade.getAuthentication().getPrincipal();
        Optional<UserEntity> userEntity= userRepository.findByUsernameAndEnabled(authentication.getUsername(),true);
        Applicant applicant=applicantRepository.findApplicantByUserId(userEntity.get().getId());

         if(applicant==null){
             throw  new ApplicationException("Please add your Information and also Upload a CV!");
         }

         Application existingApplication=applicationRepository.findByApplicantAndVacancy(applicant.getId(),model.getVacancyId());
         if(existingApplication!=null){
             throw new ApplicationException("You have already Applied!");
         }

         Application application=new Application();
         application.setApplicant(applicant);
         application.setVacancy(vacancyRepository.findOne(model.getVacancyId()));
         application.setApplicationLetter(model.getApplicationLetter());
         application.setAppliedDate(new Date());
         application.setStatus(11l);

         applicationRepository.save(application);

         model.setId(application.getId());

      return model;
    }

    @Override
    public List<AppliedPersonelView> appliedPersonelForVacancy(Long vacancyId) throws ParseException {
        List<AppliedPersonelView> personelViewList=appliedPersonelViewRepository.findByVacancyId(vacancyId);
            List<WorkExperienceDateModel> workExperienceDateModels=new ArrayList<>();
        for (AppliedPersonelView appliedPersonelView:personelViewList) {
            if(appliedPersonelView.getStartDate()!=null && appliedPersonelView.getEndDate()!=null) {
                Applicant applicant = applicantRepository.findOne(appliedPersonelView.getApplicantId());
                for (WorkExperience workExperience : applicant.getWorkExperiences()) {
                    WorkExperienceDateModel workExperienceDateModel = new WorkExperienceDateModel();
                    workExperienceDateModel.setStartDate(convertToLocalDateTimeViaInstant(workExperience.getStartDate()));
                    workExperienceDateModel.setEndDate(convertToLocalDateTimeViaInstant(workExperience.getEndDate()));
                    workExperienceDateModels.add(workExperienceDateModel);
                }
                appliedPersonelView.setTotalExperience(getTotalWorkExperience(workExperienceDateModels));
            }


        }
        Collections.sort(personelViewList,Comparator.comparing(AppliedPersonelView::getApplicantId));
        return personelViewList;
    }

    @Override
    public List<AppliedPersonelView> advanceSearch(SearchModel searchModel) {
        List<AppliedPersonelView>  appliedPersonelViews=  appliedPersonelViewRepository.findAll(where(AppliedPersonelSpecification.vacancyPredicate(searchModel.getVacancyId()))
                .and(AppliedPersonelSpecification.genderPredicate(searchModel.getGender()))
                .and(AppliedPersonelSpecification.agePredicate(searchModel.getAge(),searchModel.getAgeCriteria()))
                .and(AppliedPersonelSpecification.cgpaPredicate(searchModel.getCgpa(),searchModel.getCgpaCriteria()))
                .and(AppliedPersonelSpecification.qualificationPredicate(searchModel.getQualification()))
                .and(AppliedPersonelSpecification.workExperiencePredicate(searchModel.getWorkExperience(),searchModel.getWorkExperienceCriteria()))
                .and(AppliedPersonelSpecification.graduationYearPredicate(searchModel.getGraduationYear(),searchModel.getGraduationYearCriteria())));
        Collections.sort(appliedPersonelViews,Comparator.comparing(AppliedPersonelView::getApplicantId));
        return appliedPersonelViews;
    }

    @Override
    public List<AppliedJobView> getAppliedJobs() {
        IAuthenticationFacade authenticationFacade= new AuthenticationFacade();
        UserPrinciple authentication= (UserPrinciple) authenticationFacade.getAuthentication().getPrincipal();
        Optional<UserEntity> userEntity= userRepository.findByUsernameAndEnabled(authentication.getUsername(),true);

        return appliedJobViewRepository.findByUserId(userEntity.get().getId()) ;
    }
    private LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    private double getTotalWorkExperience(List<WorkExperienceDateModel> workExperienceDateModels){
        Collections.sort(workExperienceDateModels,Comparator.comparing(WorkExperienceDateModel::getStartDate));
      WorkExperienceDateModel startDateModel= workExperienceDateModels.stream().min(Comparator.comparing(s->s.getStartDate())).get();
      WorkExperienceDateModel endDateModel =workExperienceDateModels.stream().max(Comparator.comparing(e-> e.getEndDate())).get();

        Duration duration=Duration.between(startDateModel.getStartDate(),endDateModel.getEndDate());
         double dateGaps=0.0;
         if(workExperienceDateModels.size()>1) {
             for (int i = 1; i < workExperienceDateModels.size(); i++) {
                 WorkExperienceDateModel prevWorkExperienceDateModel = workExperienceDateModels.get(i - 1);
                 WorkExperienceDateModel nextWorkExperienceDateModel = workExperienceDateModels.get(i);
                 long dateDiff = Duration.between(nextWorkExperienceDateModel.getStartDate(), prevWorkExperienceDateModel.getEndDate()).toDays()/365;
                 if (dateDiff > 1) {
                     dateGaps = dateDiff;
                 }

             }
         }

        Double totalWorkExperience=duration.toDays()-dateGaps;



        return Math.floor(totalWorkExperience/356.0);
    }
}
