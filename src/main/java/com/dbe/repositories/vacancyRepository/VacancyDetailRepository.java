package com.dbe.repositories.vacancyRepository;

import com.dbe.domain.vacancy.VacancyDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VacancyDetailRepository extends CrudRepository<VacancyDetail,Long> {

    @Query("select vd from VacancyDetail vd join vd.vacancy v where v.id=:vacancyId")
    List<VacancyDetail> findByVacancyId(@Param("vacancyId")Long vacancyId);
}
