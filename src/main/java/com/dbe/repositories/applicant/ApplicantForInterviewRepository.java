package com.dbe.repositories.applicant;

import com.dbe.domain.applicant.ApplicantForInterview;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicantForInterviewRepository extends CrudRepository<ApplicantForInterview,Long> {
    @Query("select a from ApplicantForInterview a join a.vacancy v where v.id=:id")
    List<ApplicantForInterview> findByVacancyId(@Param("id")Long id);
}
