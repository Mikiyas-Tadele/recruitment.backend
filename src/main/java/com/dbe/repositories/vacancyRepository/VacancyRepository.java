package com.dbe.repositories.vacancyRepository;


import com.dbe.domain.vacancy.Vacancy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VacancyRepository extends CrudRepository<Vacancy,Long> {
    @Query("select v from Vacancy v where v.status=:status ")
    List<Vacancy> findActiveVacancies(@Param("status")Long status);
}
