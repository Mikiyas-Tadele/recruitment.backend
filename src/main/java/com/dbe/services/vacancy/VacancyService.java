package com.dbe.services.vacancy;

import com.dbe.services.vacancy.model.VacancyModel;
import com.dbe.services.vacancy.model.VacancyModelDetail;

import java.util.List;

public interface VacancyService {

    VacancyModel addOrUpdateVacancyDetail(VacancyModel vacancyModel);
    List<VacancyModel> getAllVacancies();
    VacancyModel getVacancy(Long id);
    void deleteVacancy(VacancyModel vacancyModel);

    void addOrUpdateVacancyDetail(VacancyModelDetail vacancyModelDetail);
    List<VacancyModelDetail> getAllDetailsForVacancy(Long vacancyId);
    void deleteVacancyModelDetail(Long id);


}
