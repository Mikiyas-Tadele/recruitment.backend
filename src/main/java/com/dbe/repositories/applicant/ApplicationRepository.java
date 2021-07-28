package com.dbe.repositories.applicant;

import com.dbe.domain.applicant.Application;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

public interface ApplicationRepository extends CrudRepository<Application,Long> {

    @Query("select a from Application a join a.applicant app join a.vacancy v where app.id=:applicantId and v.id=:vacancyId")
    Application findByApplicantAndVacancy(@Param("applicantId")Long applicantId, @Param("vacancyId")Long vacancyId);
}
