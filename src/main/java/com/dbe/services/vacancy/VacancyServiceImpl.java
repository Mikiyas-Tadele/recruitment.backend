package com.dbe.services.vacancy;

import com.dbe.domain.vacancy.Vacancy;
import com.dbe.domain.vacancy.VacancyDetail;
import com.dbe.repositories.vacancyRepository.VacancyDetailRepository;
import com.dbe.repositories.vacancyRepository.VacancyRepository;
import com.dbe.services.vacancy.model.VacancyModel;
import com.dbe.services.vacancy.model.VacancyModelDetail;
import com.dbe.utilities.models.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VacancyServiceImpl implements VacancyService {

    @Autowired
    private VacancyRepository vacancyRepository;
    @Autowired
    private VacancyDetailRepository vacancyDetailRepository;

    @Override
    public VacancyModel addOrUpdateVacancyDetail(VacancyModel vacancyModel) {
        Vacancy vacancy=getVacancyFromModel(vacancyModel);
      vacancyRepository.save(vacancy);

      return getModelFromVacancyEntity(vacancy);

    }

    @Override
    public List<VacancyModel> getAllVacancies() {

        List<VacancyModel> models=new ArrayList<>();
        Iterable<Vacancy> vacancies=vacancyRepository.findActiveVacancies(SystemConstants.ACTIVE_VACANCY);
        for (Vacancy vacancy:vacancies) {
            models.add(getModelFromVacancyEntity(vacancy));
        }

        return models;
    }

    @Override
    public VacancyModel getVacancy(Long id) {
        Vacancy vacancy=vacancyRepository.findOne(id);

        return getModelFromVacancyEntity(vacancy);
    }

    @Override
    public void deleteVacancy(VacancyModel vacancyModel) {
        vacancyRepository.delete(vacancyModel.getId());

    }


    private Vacancy getVacancyFromModel(VacancyModel vacancyModel){
        Vacancy  vacancy= vacancyModel!=null && vacancyModel.getId()!=null
                ? vacancyRepository.findOne(vacancyModel.getId()):new Vacancy();
        vacancy.setLocation(vacancyModel.getLocation());
        vacancy.setDeadlineDate(vacancyModel.getDeadlineDate());
        vacancy.setPostedDate(vacancyModel.getPostedDate());
        vacancy.setQualification(vacancyModel.getQualification());
        vacancy.setTitle(vacancyModel.getTitle());
        vacancy.setWorkExperience(vacancyModel.getWorkExperience());
        vacancy.setStatus(SystemConstants.ACTIVE_VACANCY);


        return vacancy;
    }

    private VacancyModel getModelFromVacancyEntity(Vacancy vacancy){
        VacancyModel vacancyModel=new VacancyModel();
        vacancyModel.setId(vacancy.getId());
        vacancyModel.setWorkExperience(vacancy.getWorkExperience());
        vacancyModel.setTitle(vacancy.getTitle());
        vacancyModel.setQualification(vacancy.getQualification());
        vacancyModel.setPostedDate(vacancy.getPostedDate());
        vacancyModel.setDeadlineDate(vacancy.getDeadlineDate());
        vacancyModel.setLocation(vacancy.getLocation());
        Duration duration=Duration.between(convertToLocalDateTimeViaInstant(vacancy.getPostedDate()),
                LocalDateTime.now());
        long minutes = duration.toMinutes();
        if (minutes < 59) {
            vacancyModel.setMinutesElapsedSinceCreation(minutes + " minutes ago");
        } else if (minutes > 60) {
            long hours = duration.toHours();
            if (hours < 24) {
                vacancyModel.setMinutesElapsedSinceCreation(hours + " hours ago");
            } else {
                long days = duration.toDays();
                vacancyModel.setMinutesElapsedSinceCreation(days + " days ago");
            }
        }
        List<VacancyModelDetail> vacancyModelDetails=new ArrayList<>();
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
        VacancyDetail vacancyDetail=vacancyModelDetail!=null && vacancyModelDetail.getId()!=null?
                 vacancyDetailRepository.findOne(vacancyModelDetail.getId()):new VacancyDetail();
        vacancyDetail.setDescription(vacancyModelDetail.getDescription());
        vacancyDetail.setTitle(vacancyModelDetail.getTitle());
        vacancyDetail.setVacancy(vacancyRepository.findOne(vacancyModelDetail.getVacancyId()));

        vacancyDetailRepository.save(vacancyDetail);

    }

    @Override
    public List<VacancyModelDetail> getAllDetailsForVacancy(Long vacancyId) {
        List<VacancyModelDetail> vacancyModelDetails=new ArrayList<>();
        List<VacancyDetail> vacancyDetails=vacancyDetailRepository.findByVacancyId(vacancyId);
        for (VacancyDetail vacancyDetail:vacancyDetails) {
            VacancyModelDetail vacancyModelDetail=new VacancyModelDetail();
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
      List<Vacancy> vacancies=vacancyRepository.findActiveVacancies(SystemConstants.ACTIVE_VACANCY);
      boolean change=false;
        for (Vacancy vacancy:vacancies) {
            LocalDateTime deadLineDate=convertToLocalDateTimeViaInstant(vacancy.getDeadlineDate());
            LocalDateTime today=LocalDateTime.now();
            if(today.isAfter(deadLineDate)){
                change=true;
                vacancy.setStatus(SystemConstants.INACTIVE_VACANCY);
            }
        }

        if(change){
            vacancyRepository.save(vacancies);
        }
    }
}
