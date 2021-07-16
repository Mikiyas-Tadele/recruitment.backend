package com.dbe.services.vacancy;

import com.dbe.domain.vacancy.Vacancy;
import com.dbe.repositories.VacancyRepository;
import com.dbe.services.vacancy.model.VacancyModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VacancyServiceImpl implements VacancyService {

    @Autowired
    private VacancyRepository vacancyRepository;

    @Override
    public void addOrUpdateVacancy(VacancyModel vacancyModel) {
      vacancyRepository.save(getVacancyFromModel(vacancyModel));
    }

    @Override
    public List<VacancyModel> getAllVacancies() {

        List<VacancyModel> models=new ArrayList<>();
        Iterable<Vacancy> vacancies=vacancyRepository.findAll();
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

        return vacancyModel;
    }
}
