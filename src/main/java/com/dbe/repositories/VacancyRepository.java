package com.dbe.repositories;


import com.dbe.domain.vacancy.Vacancy;
import org.springframework.data.repository.CrudRepository;

public interface VacancyRepository extends CrudRepository<Vacancy,Long> {
}
