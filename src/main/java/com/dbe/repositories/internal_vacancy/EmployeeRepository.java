package com.dbe.repositories.internal_vacancy;

import com.dbe.domain.internal_vacancy.Employee;
import com.dbe.repositories.ReadOnlyRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends ReadOnlyRepository<Employee,Long> {

    @Query("select e from Employee e where e.email=:email")
    Employee findByEmail(@Param("email")String email);
}
