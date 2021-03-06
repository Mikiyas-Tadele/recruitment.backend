package com.dbe.services.application;

import com.dbe.domain.applicant.*;
import com.dbe.domain.internal_vacancy.*;
import com.dbe.domain.security.UserEntity;
import com.dbe.repositories.applicant.*;
import com.dbe.repositories.internal_vacancy.*;
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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private InternalApplicationRepository internalApplicationRepository;

    @Autowired
    private InternalVacancyRepository internalVacancyRepository;

    @Autowired
    private InternalApplicationViewRepository internalApplicationViewRepository;

    @Autowired
    private InternalApplicationFileRepository internalApplicationFileRepository;

    @Autowired
    private InternalApplicantByPositionViewRepository internalApplicantByPositionViewRepository;

    @Autowired
    private InternalPositionByApplicantViewRepository internalPositionByApplicantViewRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    private EmployeeMissingFileRepository employeeMissingFileRepository;

    @Autowired
    private ApplicantForWrittenExamRepository applicantForWrittenExamRepository;

    @Autowired
    private ApplicantForInterviewRepository applicantForInterviewRepository;

    @Autowired
    private InternalFileCorrectionRepository internalFileCorrectionRepository;

    @Autowired
    private ResearchManagerialPosWithoutMscRepository researchManagerialPosWithoutMscRepository;

    @Autowired
    private FinalResultViewRepository finalResultViewRepository;

    @Autowired
    private ShortListApplicantsRepository shortListApplicantsRepository;


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
        applicant.setCurrentLocation(applicantModel.getCurrentLocation());
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
            applicantModel.setCurrentLocation(applicant.getCurrentLocation());
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
        fileModel.setFileName(UUID.randomUUID()+StringUtils.cleanPath(file.getOriginalFilename()));
        fileModel.setFileSize(file.getSize());
        if(applicationId==0){
            ApplicantFile existingApplicantFile=applicantFileRepository.findByUserId(userEntity.get().getId(), SystemConstants.CV_FILE);
            if(existingApplicantFile!=null){
                storageService.delete(userEntity.get().getId(), SystemConstants.CV_FILE,0l );
            }
        }
        if(applicationId!=0){
            List<ApplicantFile> existingApplicantFile=applicantFileRepository.findByVacancyIdAndUserId(applicationId,userEntity.get().getId());
            if(existingApplicantFile!=null && existingApplicantFile.size()>0){
                storageService.delete(userEntity.get().getId(), SystemConstants.QUALIFICATION_FILE,applicationId);
            }
        }
        storageService.store(file, fileModel);
        ApplicantFile applicantFile=new ApplicantFile();
        applicantFile.setFileName(fileModel.getFileName());
        applicantFile.setFileSize(fileModel.getFileSize());
        applicantFile.setUserEntity(userEntity.get());
        applicantFile.setVacancy(vacancyRepository.findOne(applicationId));
        applicantFile.setFileType(applicationId==0?SystemConstants.CV_FILE:SystemConstants.QUALIFICATION_FILE);

       applicantFileRepository.save(applicantFile);
    }

    @Override
    public void storeInternalApplicationFile(MultipartFile file, Long vacancyId) {
        IAuthenticationFacade authenticationFacade= new AuthenticationFacade();
        UserPrinciple authentication= (UserPrinciple) authenticationFacade.getAuthentication().getPrincipal();
        Optional<UserEntity> userEntity= userRepository.findByUsernameAndEnabled(authentication.getUsername(),true);
        Employee  employee=employeeRepository.findByEmail(userEntity.get().getUsername());
        FileModel fileModel = new FileModel();
            fileModel.setFileName(UUID.randomUUID()+StringUtils.cleanPath(file.getOriginalFilename()));
            fileModel.setFileSize(file.getSize());
            storageService.store(file, fileModel);
            InternalApplicationFile applicantFile=new InternalApplicationFile();
            applicantFile.setFileName(fileModel.getFileName());
            applicantFile.setFileSize(fileModel.getFileSize());
            applicantFile.setEmployeeId(employee.getEmployeeId());
            applicantFile.setVacancyId(vacancyId);
            internalApplicationFileRepository.save(applicantFile);

    }

    @Override
    public void storeInternalApplicationWithCorrection(MultipartFile file, Long vacancyId) {
        IAuthenticationFacade authenticationFacade= new AuthenticationFacade();
        UserPrinciple authentication= (UserPrinciple) authenticationFacade.getAuthentication().getPrincipal();
        Optional<UserEntity> userEntity= userRepository.findByUsernameAndEnabled(authentication.getUsername(),true);
        Employee  employee=employeeRepository.findByEmail(userEntity.get().getUsername());
        FileModel fileModel = new FileModel();
        fileModel.setFileName(UUID.randomUUID()+StringUtils.cleanPath(file.getOriginalFilename()));
        fileModel.setFileSize(file.getSize());
        storageService.store(file, fileModel);
        InternalFileCorrection applicantFile=new InternalFileCorrection();
        applicantFile.setFileName(fileModel.getFileName());
        applicantFile.setEmployeeId(employee.getEmployeeId());
        applicantFile.setVacancyId(vacancyId);
        internalFileCorrectionRepository.save(applicantFile);
    }

    @Override
    public void closeInternalApplication() {
        IAuthenticationFacade authenticationFacade= new AuthenticationFacade();
        UserPrinciple authentication= (UserPrinciple) authenticationFacade.getAuthentication().getPrincipal();
        Optional<UserEntity> userEntity= userRepository.findByUsernameAndEnabled(authentication.getUsername(),true);
        Employee  employee=employeeRepository.findByEmail(userEntity.get().getUsername());
            UserEntity userToDisable=userEntity.get();
            userToDisable.setApplied(true);
            userRepository.save(userToDisable);
            try {
                sendConfirmationEmail(employee);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            }

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
             throw  new ApplicationException("Please fill your profile before applying!");
         }

         Application existingApplication=applicationRepository.findByApplicantAndVacancy(applicant.getId(),model.getVacancyId());
         if(existingApplication!=null){
             storageService.delete(userEntity.get().getId(), SystemConstants.QUALIFICATION_FILE,model.getVacancyId() );
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
        return getFilteredApplicantProfile(personelViewList);

    }


    @Override
    public List<AppliedPersonelView> advanceSearchForExcelExport(SearchModel searchModel) {
        return getFilteredApplicantProfile(advanceSearch(searchModel));
    }

    private List<AppliedPersonelView> getFilteredApplicantProfile(List<AppliedPersonelView> personelViewList ){
        List<AppliedPersonelView> groupedList=new ArrayList<>();
        long i=1;
        boolean totalExperienceNotCalculated=true;
        for (AppliedPersonelView appliedPersonelView:personelViewList) {
            AppliedPersonelView appliedPersonel=new AppliedPersonelView();
            appliedPersonel.setApplicantId(appliedPersonelView.getApplicantId());
            appliedPersonel.setAppliedDate(appliedPersonelView.getAppliedDate());
            if(groupedList.size()>0 && groupedList.stream().anyMatch(g->g.getApplicantId().equals(appliedPersonelView.getApplicantId()))){
                appliedPersonel.setId(i++);
                appliedPersonel.setFullName(null);
                appliedPersonel.setAge(null);
                appliedPersonel.setDisability(null);
                appliedPersonel.setGender(null);
                appliedPersonel.setMobilePhone1(null);
                appliedPersonel.setMobilePhone2(null);
                appliedPersonel.setFixedLinePhone(null);
                appliedPersonel.setUserId(null);
                appliedPersonel.setEmail(null);
                appliedPersonel.setSelected(null);
            }else{
                totalExperienceNotCalculated=true;
                appliedPersonel.setId(i++);
                appliedPersonel.setFullName(appliedPersonelView.getFullName());
                appliedPersonel.setAge(appliedPersonelView.getAge());
                appliedPersonel.setDisability(appliedPersonelView.getDisability());
                appliedPersonel.setGender(appliedPersonelView.getGender());
                appliedPersonel.setMobilePhone1(appliedPersonelView.getMobilePhone1());
                appliedPersonel.setMobilePhone2(appliedPersonelView.getMobilePhone2());
                appliedPersonel.setFixedLinePhone(appliedPersonelView.getFixedLinePhone());
                appliedPersonel.setUserId(appliedPersonelView.getUserId());
                appliedPersonel.setEmail(appliedPersonelView.getEmail());
                appliedPersonel.setApplicationId(appliedPersonelView.getApplicationId());
                appliedPersonel.setApplicationLetter(appliedPersonelView.getApplicationLetter());
                appliedPersonel.setSelected(appliedPersonelView.getSelected());
            }

            if(groupedList.size()>0 &&  (groupedList.stream().anyMatch(e->e.getFieldOfEducation()!=null && e.getFieldOfEducation().equals(appliedPersonelView.getFieldOfEducation())
                    && e.getYearOfGraduation()!=null && e.getYearOfGraduation().equals(appliedPersonelView.getYearOfGraduation())
                    && e.getApplicantId()!=null && e.getApplicantId().equals(appliedPersonelView.getApplicantId())))){
                appliedPersonel.setFieldOfEducation(null);
                appliedPersonel.setYearOfGraduation(null);
                appliedPersonel.setCgpa(null);
                appliedPersonel.setQualificationDesc(null);
                appliedPersonel.setUniversity(null);
            }else{
                appliedPersonel.setFieldOfEducation(appliedPersonelView.getFieldOfEducation());
                appliedPersonel.setYearOfGraduation(appliedPersonelView.getYearOfGraduation());
                appliedPersonel.setCgpa(appliedPersonelView.getCgpa());
                appliedPersonel.setQualificationDesc(appliedPersonelView.getQualificationDesc());
                appliedPersonel.setUniversity(appliedPersonelView.getUniversity());
            }
            if(appliedPersonelView.getOrganization()!=null && appliedPersonelView.getPosition()!=null) {
                if (groupedList.size() > 0 && groupedList.stream().anyMatch(e -> e.getOrganization() !=null && e.getOrganization().equals(appliedPersonelView.getOrganization())
                        && e.getPosition()!=null && e.getPosition().equals(appliedPersonelView.getPosition())
                        && e.getApplicantId()!=null && e.getApplicantId().equals(appliedPersonelView.getApplicantId()))) {
                    appliedPersonel.setOrganization(null);
                    appliedPersonel.setPosition(null);
                    appliedPersonel.setSalary(null);
                    appliedPersonel.setStartDate(null);
                    appliedPersonel.setEndDate(null);
                    appliedPersonel.setTotalExperience(null);
                } else {
                    appliedPersonel.setOrganization(appliedPersonelView.getOrganization());
                    appliedPersonel.setPosition(appliedPersonelView.getPosition());
                    appliedPersonel.setSalary(appliedPersonelView.getSalary());
                    appliedPersonel.setStartDate(appliedPersonelView.getStartDate());
                    appliedPersonel.setEndDate(appliedPersonelView.getEndDate());
                    if (appliedPersonelView.getStartDate() != null && appliedPersonelView.getEndDate() != null) {
                        List<WorkExperienceDateModel> workExperienceDateModels = new ArrayList<>();
                        Applicant applicant = applicantRepository.findOne(appliedPersonelView.getApplicantId());
                        if (applicant!= null) {
                            if(applicant.getWorkExperiences().size()>0 && totalExperienceNotCalculated) {
                                for (WorkExperience workExperience : applicant.getWorkExperiences()) {
                                    WorkExperienceDateModel workExperienceDateModel = new WorkExperienceDateModel();
                                    workExperienceDateModel.setStartDate(convertToLocalDateTimeViaInstant(workExperience.getStartDate()));
                                    workExperienceDateModel.setEndDate(convertToLocalDateTimeViaInstant(workExperience.getEndDate()));
                                    workExperienceDateModels.add(workExperienceDateModel);
                                }
                                    appliedPersonel.setTotalExperience(getTotalWorkExperience(workExperienceDateModels));
                                    totalExperienceNotCalculated=false;
                                }
                            }
                    }

                }
            }
                if(groupedList.size()>0 &&  (groupedList.stream().anyMatch(e->e.getCertTitle()!=null && e.getCertTitle().equals(appliedPersonelView.getCertTitle())
                        && e.getCertInstutiion()!=null && e.getCertInstutiion().equals(appliedPersonelView.getCertInstutiion())
                        && e.getApplicantId()!=null && e.getApplicantId().equals(appliedPersonelView.getApplicantId())))){
                    appliedPersonel.setCertTitle(null);
                    appliedPersonel.setCertInstutiion(null);
                    appliedPersonel.setCertDate(null);
                }else{
                    appliedPersonel.setCertTitle(appliedPersonelView.getCertTitle());
                    appliedPersonel.setCertInstutiion(appliedPersonelView.getCertInstutiion());
                    appliedPersonel.setCertDate(appliedPersonelView.getCertDate());
                }


            groupedList.add(appliedPersonel);
        }

        Collections.sort(groupedList,Comparator.comparing(AppliedPersonelView::getId));

        return groupedList;
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
//        return getFilteredApplicantProfile(appliedPersonelViews);
        return getFilteredApplicantProfile(appliedPersonelViews);
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



        return (double) Math.round((totalWorkExperience/356.0)*100d)/100d;
    }

    @Override
    public void applyForInternalPositions(Long[] ids) {
        IAuthenticationFacade authenticationFacade= new AuthenticationFacade();
        UserPrinciple authentication= (UserPrinciple) authenticationFacade.getAuthentication().getPrincipal();
        Optional<UserEntity> userEntity= userRepository.findByUsernameAndEnabled(authentication.getUsername(),true);
        Employee employee=employeeRepository.findByEmail(userEntity.get().getUsername());
        long i=1;
        for (Long vacancyId:ids) {
            InternalApplication internalApplication=new InternalApplication();
            InternalVacancy internalVacancy=internalVacancyRepository.findOne(vacancyId);
            internalApplication.setAppliedDate(new Date());
            internalApplication.setEmployeeId(employee.getEmployeeId());
            internalApplication.setInternalVacancy(internalVacancy);
            internalApplication.setPositionOrder(i++);

            internalApplicationRepository.save(internalApplication);
        }


    }

    @Override
    public List<InternalApplicationView> getInternalApplicationByVacancy(Long vacancyId) {
        return internalApplicationViewRepository.findByVacancyId(vacancyId);
    }

    @Override
    public List<InternalApplicantByPositionView> getApplicantsByPosition() {
         List<InternalApplicantByPositionView> applicants=internalApplicantByPositionViewRepository.findAll();
        for (InternalApplicantByPositionView applicant:applicants) {
           if(applicant.getManagerial()!=null && applicant.getManagerial()==0){
                applicant.setPositionOne(null);
                applicant.setVacancyId1(null);
            }if(applicant.getManageria2()!=null && applicant.getManageria2()==0){
               applicant.setPositionTwo(null);
               applicant.setVacancyId2(null);
           }if(applicant.getManageria3()!=null && applicant.getManageria3()==0){
               applicant.setPositionThree(null);
               applicant.setVacancyId3(null);
           }if(applicant.getManagerial4()!=null && applicant.getManagerial4()==0){
                applicant.setPositionFour(null);
                applicant.setVacancyId4(null);
            }if(applicant.getManagerial5()!=null && applicant.getManagerial5()==0){
                applicant.setPositionFive(null);
                applicant.setVacancyId5(null);
            }if(applicant.getManagerial6()!=null && applicant.getManagerial6()==0){
                applicant.setPositionSix(null);
                applicant.setVacancyId6(null);
            }

        }
        return applicants.stream().filter(s->s.getPositionThree()!=null || s.getPositionTwo()!=null || s.getPositionOne()!=null).collect(Collectors.toList());
    }

    @Override
    public List<InternalPositionByApplicantView> getPositionByApplicant() {
        List<InternalPositionByApplicantView> internalPositionByApplicantViews= internalPositionByApplicantViewRepository.findByManagerialPositions(1l);
        return internalPositionByApplicantViews;
    }

    @Override
    public List<InternalApplicantByPositionView> getApplicantByNonManagerialPosition() {
        List<InternalApplicantByPositionView> applicants=internalApplicantByPositionViewRepository.findAll();
        for (InternalApplicantByPositionView applicant:applicants) {
            if(applicant.getManageria3()!=null && applicant.getManagerial()==1){
                applicant.setPositionOne(null);
                applicant.setVacancyId1(null);
            }if(applicant.getManageria3()!=null && applicant.getManageria2()==1){
                applicant.setPositionTwo(null);
                applicant.setVacancyId2(null);
            }if(applicant.getManageria3()!=null && applicant.getManageria3()==1){
                applicant.setPositionThree(null);
                applicant.setVacancyId3(null);
            }

        }
        return applicants.stream().filter(s->s.getPositionThree()!=null || s.getPositionTwo()!=null || s.getPositionOne()!=null).collect(Collectors.toList());
    }

    @Override
    public List<InternalPositionByApplicantView> getNonManagerialPositionByApplicant() {
        return internalPositionByApplicantViewRepository.findByManagerialPositions(0l);
    }

    @Override
    public Employee getEmployeeInfo(String username) {
        Employee employee = employeeRepository.findByEmail(username);
        return employee;
    }

    @Override
    public String getFileNameGivenVacancyAndEmployeeId(Long vacancyId, Long employeeId) {
        return internalApplicationFileRepository.findbyEmployeeAndVacancy(employeeId,vacancyId).getFileName();
    }

    @Override
    public String getFileNameGivenVacancy(Long vacancyId, Long userId) {
        if(vacancyId!=0){
            List<ApplicantFile> applicantFiles=applicantFileRepository.findByVacancyIdAndUserId(vacancyId,userId);
            if(applicantFiles.size()>0){
                return applicantFiles.get(0).getFileName();
            }
        }else{
            ApplicantFile applicationFile=applicantFileRepository.findByUserId(userId,SystemConstants.CV_FILE);
            return applicationFile.getFileName();
        }

        return null;
    }

    private void sendConfirmationEmail(Employee employee) throws UnsupportedEncodingException, MessagingException {
        List<InternalApplicationView> appliedFor=internalApplicationViewRepository.findByEmployeeId(employee.getEmployeeId());
        Collections.sort(appliedFor,Comparator.comparing(InternalApplicationView::getPositionOrder));
        String toAddress = employee.getEmail();
        String fromAddress = "hrm@dbe.com.et";
        String senderName = "Placement Teams";
        String subject = "Application Confirmation";
        String content = "Dear [[name]],<br>"
         +"You have successfully re-applied for the positions below<br>";
        int i=1;
        for (InternalApplicationView applicationView:appliedFor) {
            LocalDate localDate=LocalDate.now();
            LocalDate appliedDate=convertToLocalDateViaInstant(applicationView.getAppliedDate());
            if(localDate.isEqual(appliedDate)) {
                String placement = applicationView.getManagerial() == 0 ? applicationView.getPlacementOfWork() : "";
                content += "<h5>" + i + "." + applicationView.getAppliedPosition() + "-" + placement + "</h5>";
                i = i + 1;
            }
        }
                content+= "Thank you,<br>"
                + "Placement Teams, <br>"
                + "Development Bank of Ethiopia";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        content = content.replace("[[name]]", employee.getName());

        helper.setText(content, true);

        javaMailSender.send(message);

    }

    @Override
    public void sendToEmployeesWithMissingFiles() throws UnsupportedEncodingException, MessagingException {
        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee : employees) {
            List<EmployeeMissingFile> employeeMissingFiles = employeeMissingFileRepository.findEmployees(employee.getEmployeeId());
            if (employeeMissingFiles.size()==1) {
                String toAddress = employee.getEmail();
                String fromAddress = "hrm@dbe.com.et";
                String senderName = "Placement Teams";
                String subject = "Application Confirmation";
                String content = "Dear [[name]],<br>"
                        + "The motivation letter/letters received for the following position/positions is/are not readable.<br>"
                        +"Thus, please re-send the motivation letter for the indicated position/positions up to September 30, 2021, 5:00 p.m.East Africa Time via the following email addresses:<br>" +
                        "abebawsiraj543@gmail.com<br>" +
                        "chernetayalew@yahoo.com<br>";
                for (EmployeeMissingFile employeeMissingFile : employeeMissingFiles) {
                    content+="<h5>"+employeeMissingFile.getAppliedPosition()+"</h5>";
                }
                content += "Thank you,<br>"
                        + "Placement Teams, <br>"
                        + "Development Bank of Ethiopia";
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message);
                helper.setFrom(fromAddress, senderName);
                helper.setTo(toAddress);
                helper.setSubject(subject);
                content = content.replace("[[name]]", employee.getName());

                helper.setText(content, true);

                javaMailSender.send(message);
            }
        }
    }

    @Override
    public void ApllicantsForResearchWithoutMsc() throws UnsupportedEncodingException, MessagingException {
        List<ResearchManagerialPosWithoutMsc> withoutMscs=researchManagerialPosWithoutMscRepository.findAll();
        for (ResearchManagerialPosWithoutMsc withoutMsc:withoutMscs) {
            String toAddress = withoutMsc.getEmail();
            String fromAddress = "hrm@dbe.com.et";
            String senderName = "Placement Teams";
            String subject = "Erroneous message";
            String content = "Dear [[name]],<br>"
                    + "\n"
                    +"Due to technical issues, on October 11, 2021 you received the following erroneous message.<br>"
            +"\"The motivation letter received for the following position/positions is not readable. <br>"
            +"Thus, please re-send the motivation letter for the indicated position/positions up to September 30, 2021, 5:00 p.m. East Africa Time via the following email addresses:"
            +"abebawsiraj543@gmail.com<br>"
            +"chernetayalew@yahoo.com<br>\""
            +"Please disregard this message, apologies for the inconvenience that might be created.<br>";
            content += "Regards,<br>"
                    + "Placement Teams, <br>"
                    + "Development Bank of Ethiopia";
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(fromAddress, senderName);
            helper.setTo(toAddress);
            helper.setSubject(subject);
            content = content.replace("[[name]]", withoutMsc.getName());

            helper.setText(content, true);

            javaMailSender.send(message);
        }

    }

    @Override
    public void ApllicantsWithFileError() throws UnsupportedEncodingException, MessagingException {
//        List<ShortListApplicants> withoutMscs=shortListApplicantsRepository.findAll();
        for (ShortListApplicants withoutMsc:shortListApplicantsRepository.findAll()) {
            String toAddress = withoutMsc.getEmail();
            String fromAddress = "hrm3@dbe.com.et";
            String senderName = "???????????? ?????????????????? ??????????????? ??????????????????";
            String subject = "????????? ????????????????????? ?????? ??????????????????";
            String content = "??????????????? ????????? ?????? ??????????????? ????????? ???????????? ???????????? ??????????????????\n" +
                    "???????????? ????????? ??????????????? ???????????? ?????????????????? ????????? ????????????\n" +
                    "????????? ???????????? ??????  ???????????????????????? ???????????? ???1-5 ?????????\n" +
                    "?????????????????? ???.???.??? ???????????? 23 ?????? 2021 ????????? ??????????????????\n" +
                    "??????????????? ????????? ?????????????????????\n" +
                    "\n" +
                    "???????????? ??????????????? ??????????????? ??????????????? ???????????? ?????? ???????????? ????????????\n" +
                    "?????????????????? ??????????????? ????????? ??????????????? ??????????????? ????????? ???????????????\n" +
                    "????????? ?????????????????? ?????????????????? ????????? ??????????????? ???????????????\n" +
                    "?????????????????? ?????????????????????\n" +
                    "\n" +
                    "????????? ???????????? ????????? ????????????????????? ???????????? ???????????? ???????????????\n" +
                    "????????????????????? ????????? ???????????? 29 ?????? 2014 ???.???. ????????? ???7???30\n" +
                    "????????? ???????????? ????????? ?????????????????? ????????? ?????? ????????? ?????????????????? ??????????????? ????????????????????? ???????????????????????????<br><br><br>";
            content += "Thank you,<br>"
                    + "???????????? ?????????????????? ??????????????? ??????????????????, <br>"
                    + "Development Bank of Ethiopia";
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(fromAddress, senderName);
            helper.setTo(toAddress);
            helper.setSubject(subject);
            content = content.replace("[[name]]", withoutMsc.getName());

            helper.setText(content, true);

            javaMailSender.send(message);
        }

    }

    @Override
    public void addOrUpdateApplicantsSelectedForWrittenExam(List<ApplicantForWrittenExamModel> applicantForWrittenExamModels) {
        if(applicantForWrittenExamModels.size()>0) {
            List<ApplicantForWrittenExam> applicants=new ArrayList<>();
            List<Application> applications=new ArrayList<>();
            int i=0;
            List<Integer> randomNumberList=generateExamCodeNumber(applicantForWrittenExamModels.size());
            List<ApplicantForWrittenExam> existingWrittenExamApplicants=applicantForWrittenExamRepository.findByVacancyId(applicantForWrittenExamModels.get(0).getVacancyId());
            for (ApplicantForWrittenExamModel writtenExamModel : applicantForWrittenExamModels) {
                ApplicantForWrittenExam applicantForWrittenExam = writtenExamModel.getId() != null ?
                        applicantForWrittenExamRepository.findOne(writtenExamModel.getId()) :
                        new ApplicantForWrittenExam();
                applicantForWrittenExam.setSelected(true);
                applicantForWrittenExam.setExamResult(writtenExamModel.getExamResult());
                applicantForWrittenExam.setApplicant(applicantRepository.findOne(writtenExamModel.getApplicantId()));
                applicantForWrittenExam.setVacancy(vacancyRepository.findOne(writtenExamModel.getVacancyId()));
                if(writtenExamModel.getAddOrRemove()) {
                    applicantForWrittenExam.setExamCode(writtenExamModel.getExamCodePrefix() + randomNumberList.get(i++));
                }

                applicants.add(applicantForWrittenExam);

                Application application=applicationRepository.findByApplicantAndVacancy(writtenExamModel.getApplicantId(),writtenExamModel.getVacancyId());
                application.setStatus(SystemConstants.SELECTED_FOR_WRITTEN_EXAM);
                applications.add(application);
            }
            applicantForWrittenExamRepository.save(applicants);
            applicationRepository.save(applications);
            if(existingWrittenExamApplicants.size()>0 && applicantForWrittenExamModels.get(0).getAddOrRemove()){
                checkForExistingCode(applicantForWrittenExamModels.get(0).getVacancyId(),applicantForWrittenExamModels.get(0).getExamCodePrefix());
            }
        }

    }

    @Override
    public void removeApplicantsSelectedForWrittenExam(List<ApplicantForWrittenExamModel> applicantForWrittenExamModels) {
        if(applicantForWrittenExamModels.size()>0){
            List<ApplicantForWrittenExam> applicants=new ArrayList<>();
            List<Application> applications=new ArrayList<>();
            for (ApplicantForWrittenExamModel writtenExamModel:applicantForWrittenExamModels) {
                ApplicantForWrittenExam applicant=applicantForWrittenExamRepository.findOne(writtenExamModel.getId());
                applicants.add(applicant);
                Application application=applicationRepository.findByApplicantAndVacancy(writtenExamModel.getApplicantId(),writtenExamModel.getVacancyId());
                application.setStatus(SystemConstants.NOT_SELECTED);
                applications.add(application);
            }

            applicantForWrittenExamRepository.delete(applicants);

        }
    }

    private void changeApplicantsNotSlectedStatus(Long vacancyId){
      List<Application> applications=applicationRepository.findByVacancyId(vacancyId);
      List<ApplicantForWrittenExam> forWrittenExamList=applicantForWrittenExamRepository.findByVacancyId(vacancyId);


    }

    @Override
    public void addOrUpdateApplicantsSelectedForInterview(List<ApplicantForInterviewModel> applicantForInterviewModels) {
        if(applicantForInterviewModels.size()>0){
            List<ApplicantForInterview> applicants=new ArrayList<>();
            List<Application> applications=new ArrayList<>();
            for (ApplicantForInterviewModel interviewModel:applicantForInterviewModels) {
                ApplicantForInterview applicantForInterview=interviewModel.getId()!=null?
                        applicantForInterviewRepository.findOne(interviewModel.getId()):
                        new ApplicantForInterview();
                applicantForInterview.setSelected(true);
                applicantForInterview.setExamResult(interviewModel.getExamResult());
                applicantForInterview.setApplicant(applicantRepository.findOne(interviewModel.getApplicantId()));
                applicantForInterview.setVacancy(vacancyRepository.findOne(interviewModel.getVacancyId()));

                applicants.add(applicantForInterview);

                Application application=applicationRepository.findByApplicantAndVacancy(interviewModel.getApplicantId(),interviewModel.getVacancyId());
                application.setStatus(SystemConstants.SELECTED_FOR_INTERVIEW);
                applications.add(application);
            }

            applicantForInterviewRepository.save(applicants);
            applicationRepository.save(applications);
        }

    }

    @Override
    public void removeApplicantsSelectedForInterview(List<ApplicantForInterviewModel> applicantForInterviewModels) {
        if(applicantForInterviewModels.size()>0){
            List<ApplicantForInterview> applicants=new ArrayList<>();
            for (ApplicantForInterviewModel interviewModel:applicantForInterviewModels) {
                ApplicantForInterview applicant=applicantForInterviewRepository.findOne(interviewModel.getId());
                applicants.add(applicant);
            }

            applicantForInterviewRepository.delete(applicants);
        }
    }

    @Override
    public List<ApplicantForWrittenExamModel> getApplicantsForWrittenExam(Long vacancyId) {

        List<ApplicantForWrittenExamModel> forWrittenExamModels =new ArrayList<>();
        Iterable<ApplicantForWrittenExam> applicants=applicantForWrittenExamRepository.findByVacancyId(vacancyId);
        for (ApplicantForWrittenExam applicantForWrittenExam:applicants) {
             ApplicantForWrittenExamModel writtenExamModel=new ApplicantForWrittenExamModel();
             writtenExamModel.setId(applicantForWrittenExam.getId());
             writtenExamModel.setApplicantId(applicantForWrittenExam.getApplicant().getId());
             writtenExamModel.setApplicantName(applicantForWrittenExam.getApplicant().getUserEntity().getFullName());
             writtenExamModel.setExamResult(applicantForWrittenExam.getExamResult());
             writtenExamModel.setSelected(applicantForWrittenExam.getSelected());
             writtenExamModel.setVacancyId(applicantForWrittenExam.getVacancy().getId());
             writtenExamModel.setVacancyTitle(applicantForWrittenExam.getVacancy().getTitle());
             writtenExamModel.setExamCode(applicantForWrittenExam.getExamCode());

            forWrittenExamModels.add(writtenExamModel);
        }

        return forWrittenExamModels;
    }

    @Override
    public List<ApplicantForInterviewModel> getApplicantsForInterview(Long vacancyId) {

        List<ApplicantForInterviewModel> forInterviewModels=new ArrayList<>();
        Iterable<ApplicantForInterview> applicants=applicantForInterviewRepository.findByVacancyId(vacancyId);
        for (ApplicantForInterview applicantForInterview:applicants) {
            ApplicantForInterviewModel interviewModel=new ApplicantForInterviewModel();
            interviewModel.setId(applicantForInterview.getId());
            interviewModel.setApplicantId(applicantForInterview.getApplicant().getId());
            interviewModel.setApplicantName(applicantForInterview.getApplicant().getUserEntity().getFullName());
            interviewModel.setExamResult(applicantForInterview.getExamResult());
            interviewModel.setVacancyId(applicantForInterview.getVacancy().getId());
            interviewModel.setVacancyTitle(applicantForInterview.getVacancy().getTitle());
            interviewModel.setSelected(applicantForInterview.getSelected());
            interviewModel.setExamCode(applicantForWrittenExamRepository.findByApplicantAndVacancyId(vacancyId,applicantForInterview.getApplicant().getId()).getExamCode());

            forInterviewModels.add(interviewModel);
        }


        return forInterviewModels;
    }

    @Override
    public void movetoFinalStage(List<ApplicantForInterviewModel> applicantForInterviewModels) {
        if(applicantForInterviewModels.size()>0){
            List<ApplicantForInterview> applicants=new ArrayList<>();
            List<Application> applications=new ArrayList<>();
            for (ApplicantForInterviewModel interviewModel:applicantForInterviewModels) {
                ApplicantForInterview applicantForInterview= applicantForInterviewRepository.findOne(interviewModel.getId());
                applicantForInterview.setFinal(true);
                applicants.add(applicantForInterview);

                Application application=applicationRepository.findByApplicantAndVacancy(interviewModel.getApplicantId(),interviewModel.getVacancyId());
                application.setStatus(SystemConstants.SELECTED_FOR_POSITION);
                applications.add(application);
            }

            applicantForInterviewRepository.save(applicants);
            applicationRepository.save(applications);
        }
    }

    @Override
    public List<FinalResultView> getAllApplicantsWithFinalResults(Long vacancyId) {
        return finalResultViewRepository.findByVacancyId(vacancyId);
    }

    @Override
    public List<InternalApplicationModel> getInternalApplicationInfo(String empId) {
        Employee employee = employeeRepository.findByEmail(empId);
        List<InternalApplicationView> appliedFor=internalApplicationViewRepository.findByEmployeeId(employee.getEmployeeId());
        Collections.sort(appliedFor,Comparator.comparing(InternalApplicationView::getPositionOrder));
        List<InternalApplicationModel> applicationModels=new ArrayList<>();
        for (InternalApplicationView file:appliedFor) {
            InternalApplicationModel applicationModel=new InternalApplicationModel();
            applicationModel.setEmployeeId(file.getEmployeeId());
            applicationModel.setVacancyId(file.getVacancyId());
            applicationModel.setPosition(internalVacancyRepository.findOne(file.getVacancyId()).getPosition());

            applicationModels.add(applicationModel);
        }

        return applicationModels;

    }

    @Override
    public void closeFileAttachementSession() {
        IAuthenticationFacade authenticationFacade= new AuthenticationFacade();
        UserPrinciple authentication= (UserPrinciple) authenticationFacade.getAuthentication().getPrincipal();
        Optional<UserEntity> userEntity= userRepository.findByUsernameAndEnabled(authentication.getUsername(),true);
        UserEntity userEntityValue=userEntity.get();
        userEntityValue.setFileError(false);
        userRepository.save(userEntityValue);
    }

    private LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private void checkForExistingCode(Long vacancyId, String codePrefix){
        List<ApplicantForWrittenExam> applicants=applicantForWrittenExamRepository.findByVacancyId(vacancyId);
        List<ApplicantForWrittenExam> toBeSavedapplicants=new ArrayList<>();
        List<Integer> randomNumbers=generateExamCodeNumber(applicants.size());
        int i=0;
        for (ApplicantForWrittenExam applicantForWrittenExam:applicants) {
            applicantForWrittenExam.setExamCode(codePrefix+randomNumbers.get(i++));
            toBeSavedapplicants.add(applicantForWrittenExam);
        }

        applicantForWrittenExamRepository.save(toBeSavedapplicants);

    }

    private  List<Integer> generateExamCodeNumber(int length) {
        List<Integer> randomNumberList=new ArrayList<>();
        for(int i=1;i<=length;i++){
            randomNumberList.add(i);
        }
        Collections.shuffle(randomNumberList);

        return randomNumberList;
    }
}
