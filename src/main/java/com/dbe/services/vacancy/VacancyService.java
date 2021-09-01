package com.dbe.services.vacancy;

import com.dbe.services.vacancy.model.InternalVacancyModel;
import com.dbe.services.vacancy.model.VacancyModel;
import com.dbe.services.vacancy.model.VacancyModelDetail;

import java.util.List;

public interface VacancyService {

    VacancyModel addOrUpdateVacancyDetail(VacancyModel vacancyModel);
    List<VacancyModel> getAllVacancies();
    List<VacancyModel> getAllActiveVacancies();
    VacancyModel getVacancy(Long id);
    void deleteVacancy(VacancyModel vacancyModel);
    void makeDeadLinePassedVacanciesInActive();

    void addOrUpdateVacancyDetail(VacancyModelDetail vacancyModelDetail);
    List<VacancyModelDetail> getAllDetailsForVacancy(Long vacancyId);
    void deleteVacancyModelDetail(Long id);

    //------Internal Vacancy---------
    List<InternalVacancyModel> getAllInternalVacancies();
    List<InternalVacancyModel> getALLInternalVacanciesByPlacement(String placement);
    InternalVacancyModel getInternalVacancyGivenId(Long id);
    void addOrUpdateInternalVacancy(InternalVacancyModel internalVacancyModel);
    void deleteInternalVacancy(Long id);


}
