package com.dbe.utilities.Schedules;

import com.dbe.services.vacancy.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RecruitmentAppSchedule {

    @Autowired
    private VacancyService vacancyService;

    @Scheduled(cron="0 0 6 * * ?")
    public void makeVacanciesInActive(){
        vacancyService.makeDeadLinePassedVacanciesInActive();
    }
}
