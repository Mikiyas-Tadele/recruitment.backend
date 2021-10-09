package com.dbe.repositories.internal_vacancy;

import com.dbe.domain.internal_vacancy.EmployeeMissingFile;
import com.dbe.repositories.ReadOnlyRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeMissingFileRepository extends ReadOnlyRepository<EmployeeMissingFile,Long> {

    @Query("select e from EmployeeMissingFile e where e.employeeId=:empId")
    List<EmployeeMissingFile> findEmployees(@Param("empId")Long empId);
}
