package com.dbe.repositories.applicant;

import com.dbe.domain.applicant.Application;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ApplicationRepository extends CrudRepository<Application,Long> {

    @Query("select a from Application a join a.applicant app join a.vacancy v where app.id=:applicantId and v.id=:vacancyId")
    Application findByApplicantAndVacancy(@Param("applicantId")Long applicantId, @Param("vacancyId")Long vacancyId);

    @Query("select a from Application a join a.vacancy v where v.id=:vacancyId")
    List<Application> findByVacancyId(@Param("vacancyId")Long vacancyId);
}
