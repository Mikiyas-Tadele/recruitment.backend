package com.dbe.repositories.applicant;

import com.dbe.domain.applicant.Applicant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ApplicantRepository extends CrudRepository<Applicant,Long> {

    @Query("select a from Applicant a join a.userEntity u where u.id=:userId")
    Applicant findApplicantByUserId(@Param("userId")Long userId);
}
