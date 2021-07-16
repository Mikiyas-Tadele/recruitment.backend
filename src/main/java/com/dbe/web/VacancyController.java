package com.dbe.web;

import com.dbe.services.vacancy.VacancyService;
import com.dbe.services.vacancy.model.VacancyModel;
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
    public void addOrUpdateVacancy(@RequestBody VacancyModel vacancyModel){
        vacancyService.addOrUpdateVacancy(vacancyModel);
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
}
