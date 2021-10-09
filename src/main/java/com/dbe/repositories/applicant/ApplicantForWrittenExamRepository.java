package com.dbe.repositories.applicant;

import com.dbe.domain.applicant.ApplicantForWrittenExam;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicantForWrittenExamRepository extends CrudRepository<ApplicantForWrittenExam,Long> {
    @Query("select a from ApplicantForWrittenExam a join a.vacancy v where v.id=:id")
    List<ApplicantForWrittenExam> findByVacancyId(@Param("id")Long id);
}
