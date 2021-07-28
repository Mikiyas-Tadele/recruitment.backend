package com.dbe.repositories.applicant;

import com.dbe.domain.applicant.Applicant;
import com.dbe.domain.applicant.EducationalBackground;
import com.dbe.domain.applicant.WorkExperience;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

public interface ApplicantRepository extends CrudRepository<Applicant,Long> {

    @Query("select a from Applicant a join a.userEntity u where u.id=:userId")
    Applicant findApplicantByUserId(@Param("userId")Long userId);

    @Query("select wexp from Applicant a join a.workExperiences wexp where wexp.id=:wexpId")
    WorkExperience findApplicantWorkExp(@Param("wexpId") Long wexpId);

    @Query("select edu from Applicant a join a.educationalBackgrounds edu where edu.id=:eduId")
    EducationalBackground findApplicantEduBkg(@Param("eduId") Long eduId);

}
