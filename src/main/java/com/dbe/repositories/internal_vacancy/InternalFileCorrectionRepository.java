package com.dbe.repositories.internal_vacancy;

import com.dbe.domain.internal_vacancy.InternalApplicationFile;
import com.dbe.domain.internal_vacancy.InternalFileCorrection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface InternalFileCorrectionRepository  extends CrudRepository<InternalFileCorrection,Long>{
    @Query("select iv from InternalFileCorrection iv where iv.employeeId=:employeeId and iv.vacancyId=:vacancyId")
    InternalFileCorrection findbyEmployeeAndVacancy(@Param("employeeId")Long employeeId, @Param("vacancyId")Long vacancyId);
}
