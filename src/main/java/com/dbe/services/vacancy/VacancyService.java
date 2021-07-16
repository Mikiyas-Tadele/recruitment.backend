package com.dbe.services.vacancy;

import com.dbe.services.vacancy.model.VacancyModel;

import java.util.List;

public interface VacancyService {

    void addOrUpdateVacancy(VacancyModel vacancyModel);
    List<VacancyModel> getAllVacancies();
    VacancyModel getVacancy(Long id);
    void deleteVacancy(VacancyModel vacancyModel);


}
