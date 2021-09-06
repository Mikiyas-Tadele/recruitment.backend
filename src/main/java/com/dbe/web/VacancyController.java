package com.dbe.web;

import com.dbe.services.vacancy.VacancyService;
import com.dbe.services.vacancy.model.InternalVacancyModel;
import com.dbe.services.vacancy.model.VacancyModel;
import com.dbe.services.vacancy.model.VacancyModelDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(ResourceConstants.VACANCY_CONTROLLER)
public class VacancyController {

    @Autowired
    private VacancyService vacancyService;

    @PostMapping("/vacancy")
    public VacancyModel addOrUpdateVacancy(@RequestBody VacancyModel vacancyModel){
        return vacancyService.addOrUpdateVacancyDetail(vacancyModel);
    }

    @DeleteMapping("/vacancy")
    public void deleteVacancy(@RequestBody VacancyModel vacancyModel){
        vacancyService.deleteVacancy(vacancyModel);
    }

    @RequestMapping("/vacancies")
    public List<VacancyModel> getVacancies(){
        return vacancyService.getAllVacancies();
    }

    @RequestMapping("/vacancy/{id}")
    public VacancyModel getVacancy(@PathVariable Long id){
        return vacancyService.getVacancy(id);
    }

    @PostMapping("/vacancyDetail")
    public void  addOrUpdateVacancyModelDetail(@RequestBody VacancyModelDetail vacancyModelDetail){
        vacancyService.addOrUpdateVacancyDetail(vacancyModelDetail);
    }

    @GetMapping("/vacancyDetails/{vacancyId}")
    public List<VacancyModelDetail> getVacancyDetails(@PathVariable Long vacancyId){
        return vacancyService.getAllDetailsForVacancy(vacancyId);
    }

    @GetMapping("/active-vacancies")
    public List<VacancyModel> getAllActiveVacancies(){
        return vacancyService.getAllActiveVacancies();
    }

    @PostMapping("/internal-vacancy")
    public void addOrUpdateInternalVacancy(@RequestBody InternalVacancyModel internalVacancyModel){
        vacancyService.addOrUpdateInternalVacancy(internalVacancyModel);
    }

    @GetMapping("/internal-vacancies")
    public List<InternalVacancyModel> getInternalVacancies(){
        return  vacancyService.getAllInternalVacancies();
    }

    @GetMapping("/internal-vacancies/{placement}")
    public List<InternalVacancyModel> getAllVacanciesByPlacement(@PathVariable String placement){
        return vacancyService.getALLInternalVacanciesByPlacement(placement);
    }

    @GetMapping("/internal-vacancy/{id}")
    public InternalVacancyModel getInternalVacancy(@PathVariable Long id){
        return vacancyService.getInternalVacancyGivenId(id);
    }
}
