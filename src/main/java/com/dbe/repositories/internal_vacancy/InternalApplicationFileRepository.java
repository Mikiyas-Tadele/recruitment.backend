package com.dbe.repositories.internal_vacancy;

import com.dbe.domain.internal_vacancy.InternalApplicationFile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InternalApplicationFileRepository extends CrudRepository<InternalApplicationFile,Long> {
    @Query("select iv from InternalApplicationFile iv where iv.employeeId=:employeeId and iv.vacancyId=:vacancyId")
    InternalApplicationFile findbyEmployeeAndVacancy(@Param("employeeId")Long employeeId,@Param("vacancyId")Long vacancyId);

    @Query("select iv from InternalApplicationFile iv where iv.employeeId=:employeeId")
    List<InternalApplicationFile> findbyEmployee(@Param("employeeId")Long employeeId);

}
