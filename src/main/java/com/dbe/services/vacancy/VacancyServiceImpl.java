package com.dbe.services.vacancy;

import com.dbe.domain.internal_vacancy.InternalVacancy;
import com.dbe.domain.vacancy.Vacancy;
import com.dbe.domain.vacancy.VacancyDetail;
import com.dbe.repositories.internal_vacancy.InternalVacancyRepository;
import com.dbe.repositories.vacancyRepository.VacancyDetailRepository;
import com.dbe.repositories.vacancyRepository.VacancyRepository;
import com.dbe.services.vacancy.model.InternalVacancyModel;
import com.dbe.services.vacancy.model.VacancyModel;
import com.dbe.services.vacancy.model.VacancyModelDetail;
import com.dbe.utilities.models.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class VacancyServiceImpl implements VacancyService {

    @Autowired
    private VacancyRepository vacancyRepository;
    @Autowired
    private VacancyDetailRepository vacancyDetailRepository;
    @Autowired
    private InternalVacancyRepository internalVacancyRepository;

    @Override
    public VacancyModel addOrUpdateVacancyDetail(VacancyModel vacancyModel) {
        Vacancy vacancy = getVacancyFromModel(vacancyModel);
        vacancyRepository.save(vacancy);

        return getModelFromVacancyEntity(vacancy);

    }

    @Override
    public List<VacancyModel> getAllVacancies() {

        List<VacancyModel> models = new ArrayList<>();
        Iterable<Vacancy> vacancies = vacancyRepository.findAll();
        for (Vacancy vacancy : vacancies) {
            models.add(getModelFromVacancyEntity(vacancy));
        }

        Collections.sort(models, Comparator.comparing(VacancyModel::getPostedDate).reversed());

        return models;
    }

    @Override
    public List<VacancyModel> getAllActiveVacancies() {
        List<VacancyModel> models = new ArrayList<>();
        Iterable<Vacancy> vacancies = vacancyRepository.findActiveVacancies(SystemConstants.ACTIVE_VACANCY);
        for (Vacancy vacancy : vacancies) {
            models.add(getModelFromVacancyEntity(vacancy));
        }


        Collections.sort(models, Comparator.comparing(VacancyModel::getPostedDate).reversed());

        return models;
    }

    @Override
    public VacancyModel getVacancy(Long id) {
        Vacancy vacancy = vacancyRepository.findOne(id);

        return getModelFromVacancyEntity(vacancy);
    }

    @Override
    public void deleteVacancy(VacancyModel vacancyModel) {
        vacancyRepository.delete(vacancyModel.getId());

    }


    private Vacancy getVacancyFromModel(VacancyModel vacancyModel) {
        Vacancy vacancy = vacancyModel != null && vacancyModel.getId() != null
                ? vacancyRepository.findOne(vacancyModel.getId()) : new Vacancy();
        vacancy.setLocation(vacancyModel.getLocation());
        vacancy.setDeadlineDate(vacancyModel.getDeadlineDate());
        vacancy.setPostedDate(vacancyModel.getPostedDate());
        vacancy.setQualification(vacancyModel.getQualification());
        vacancy.setTitle(vacancyModel.getTitle());
        vacancy.setWorkExperience(vacancyModel.getWorkExperience());
        vacancy.setStatus(SystemConstants.ACTIVE_VACANCY);
        vacancy.setEmploymentCondition(vacancyModel.getEmploymentCondition());
        vacancy.setRequiredNumber(vacancyModel.getRequiredNumber());
        vacancy.setSalary(vacancyModel.getSalary());
        vacancy.setSalaryDescription(vacancyModel.getSalaryDescription());


        return vacancy;
    }

    private VacancyModel getModelFromVacancyEntity(Vacancy vacancy) {
        VacancyModel vacancyModel = new VacancyModel();
        vacancyModel.setId(vacancy.getId());
        vacancyModel.setWorkExperience(vacancy.getWorkExperience());
        vacancyModel.setTitle(vacancy.getTitle());
        vacancyModel.setQualification(vacancy.getQualification());
        vacancyModel.setPostedDate(vacancy.getPostedDate());
        vacancyModel.setDeadlineDate(vacancy.getDeadlineDate());
        vacancyModel.setLocation(vacancy.getLocation());
        vacancyModel.setClosed(vacancy.getStatus() == 2l ? true : false);
        vacancyModel.setEmploymentCondition(vacancy.getEmploymentCondition());
        vacancyModel.setRequiredNumber(vacancy.getRequiredNumber());
        vacancyModel.setSalary(vacancy.getSalary());
        vacancyModel.setSalaryDescription(vacancy.getSalaryDescription());
        Duration duration = Duration.between(LocalDateTime.now(), convertToLocalDateTimeViaInstant(vacancy.getDeadlineDate()).plusDays(1));
        long days = duration.toDays();
        String daysLeft = "";
        if (days == 1) {
            daysLeft = days + " Day left before Deadline";
        } else if (days > 0) {
            daysLeft = days + " Days left before Deadline";
        } else if (days == 0) {
            daysLeft = " Today is Deadline Date";
        } else {
            daysLeft = Math.abs(days) + " days has passed since closing";
        }
        vacancyModel.setMinutesElapsedSinceCreation(daysLeft);
        List<VacancyModelDetail> vacancyModelDetails = new ArrayList<>();
        for (VacancyDetail vacancyDetail : vacancy.getVacancyDetails()) {
            VacancyModelDetail vacancyModelDetail = new VacancyModelDetail();
            vacancyModelDetail.setVacancyId(vacancyDetail.getVacancy().getId());
            vacancyModelDetail.setTitle(vacancyDetail.getTitle());
            vacancyModelDetail.setDescription(vacancyDetail.getDescription());
            vacancyModelDetails.add(vacancyModelDetail);
        }
        vacancyModel.getVacancyModelDetailList().addAll(vacancyModelDetails);

        return vacancyModel;
    }

    @Override
    public void addOrUpdateVacancyDetail(VacancyModelDetail vacancyModelDetail) {
        VacancyDetail vacancyDetail = vacancyModelDetail != null && vacancyModelDetail.getId() != null ?
                vacancyDetailRepository.findOne(vacancyModelDetail.getId()) : new VacancyDetail();
        vacancyDetail.setDescription(vacancyModelDetail.getDescription());
        vacancyDetail.setTitle(vacancyModelDetail.getTitle());
        vacancyDetail.setVacancy(vacancyRepository.findOne(vacancyModelDetail.getVacancyId()));

        vacancyDetailRepository.save(vacancyDetail);

    }

    @Override
    public List<VacancyModelDetail> getAllDetailsForVacancy(Long vacancyId) {
        List<VacancyModelDetail> vacancyModelDetails = new ArrayList<>();
        List<VacancyDetail> vacancyDetails = vacancyDetailRepository.findByVacancyId(vacancyId);
        for (VacancyDetail vacancyDetail : vacancyDetails) {
            VacancyModelDetail vacancyModelDetail = new VacancyModelDetail();
            vacancyModelDetail.setDescription(vacancyDetail.getDescription());
            vacancyModelDetail.setTitle(vacancyDetail.getTitle());
            vacancyModelDetail.setVacancyId(vacancyDetail.getVacancy().getId());

            vacancyModelDetails.add(vacancyModelDetail);
        }

        return vacancyModelDetails;

    }


    @Override
    public void deleteVacancyModelDetail(Long id) {
        vacancyDetailRepository.delete(id);
    }

    private LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    @Override
    public void makeDeadLinePassedVacanciesInActive() {
        List<Vacancy> vacancies = vacancyRepository.findActiveVacancies(SystemConstants.ACTIVE_VACANCY);
        boolean change = false;
        for (Vacancy vacancy : vacancies) {
            LocalDateTime deadLineDate = convertToLocalDateTimeViaInstant(vacancy.getDeadlineDate());
            LocalDateTime today = LocalDateTime.now();
            if (today.isAfter(deadLineDate.plusDays(1))) {
                change = true;
                vacancy.setStatus(SystemConstants.INACTIVE_VACANCY);
            }
        }

        if (change) {
            vacancyRepository.save(vacancies);
        }
    }

    @Override
    public List<InternalVacancyModel> getAllInternalVacancies() {
        List<InternalVacancyModel> vacancyModels = new ArrayList<>();
        Iterable<InternalVacancy> internalVacancies = internalVacancyRepository.findActiveVacancies(1l);
        getInternalVacancyModel(vacancyModels, internalVacancies);

        return vacancyModels;
    }

    @Override
    public List<InternalVacancyModel> getALLInternalVacanciesByPlacement(String placement) {
        List<InternalVacancyModel> vacancyModels = new ArrayList<>();
        Iterable<InternalVacancy> internalVacancies = internalVacancyRepository.findByPlacementOfWork(placement);
        getInternalVacancyModel(vacancyModels, internalVacancies);
        Collections.sort(vacancyModels,Comparator.comparing(InternalVacancyModel::getId));

        return vacancyModels;
    }

    private void getInternalVacancyModel(List<InternalVacancyModel> vacancyModels, Iterable<InternalVacancy> internalVacancies) {
        for (InternalVacancy internalVacancy : internalVacancies) {
            InternalVacancyModel vacancyModel = new InternalVacancyModel();
            vacancyModel.setId(internalVacancy.getId());
            vacancyModel.setJobGrade(internalVacancy.getJobGrade());
            vacancyModel.setNoRequired(internalVacancy.getNoRequired());
            vacancyModel.setEndDate(internalVacancy.getEndDate());
            vacancyModel.setPostDate(internalVacancy.getPostDate());
            vacancyModel.setPosition(internalVacancy.getPosition());
            vacancyModel.setPlacementOfWork(internalVacancy.getPlacementOfWork());
            vacancyModel.setQualifications(internalVacancy.getQualifications());
            vacancyModel.setParent(internalVacancy.getParent());
            vacancyModel.setManagerial(internalVacancy.getManagerial());
            vacancyModel.setFieldOfStudy(internalVacancy.getFieldOfStudy());
            vacancyModel.setEducationLevel(internalVacancy.getEducationLevel());
            vacancyModel.setLocation(internalVacancy.getLocation());
            vacancyModel.setCluster(internalVacancy.getCluster());

            vacancyModels.add(vacancyModel);
        }
    }

    @Override
    public InternalVacancyModel getInternalVacancyGivenId(Long id) {

        InternalVacancy internalVacancy = internalVacancyRepository.findOne(id);

        InternalVacancyModel vacancyModel = new InternalVacancyModel();
        vacancyModel.setId(internalVacancy.getId());
        vacancyModel.setJobGrade(internalVacancy.getJobGrade());
        vacancyModel.setNoRequired(internalVacancy.getNoRequired());
        vacancyModel.setEndDate(internalVacancy.getEndDate());
        vacancyModel.setPostDate(internalVacancy.getPostDate());
        vacancyModel.setPosition(internalVacancy.getPosition());
        vacancyModel.setPlacementOfWork(internalVacancy.getPlacementOfWork());
        vacancyModel.setQualifications(internalVacancy.getQualifications());

        return vacancyModel;
    }

    @Override
    public void addOrUpdateInternalVacancy(InternalVacancyModel internalVacancyModel) {
        InternalVacancy internalVacancy = internalVacancyModel.getId() != null ?
                internalVacancyRepository.findOne(internalVacancyModel.getId()) :
                new InternalVacancy();
        internalVacancy.setJobGrade(internalVacancyModel.getJobGrade());
        internalVacancy.setNoRequired(internalVacancyModel.getNoRequired());
        internalVacancy.setEndDate(internalVacancyModel.getEndDate());
        internalVacancy.setPostDate(internalVacancyModel.getPostDate());
        internalVacancy.setPosition(internalVacancyModel.getPosition());
        internalVacancy.setPlacementOfWork(internalVacancyModel.getPlacementOfWork());
        internalVacancy.setQualifications(internalVacancyModel.getQualifications());

        internalVacancyRepository.save(internalVacancy);
    }

    @Override
    public void deleteInternalVacancy(Long id) {

    }

}
