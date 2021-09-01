package com.dbe.repositories.internal_vacancy;

import com.dbe.domain.internal_vacancy.InternalVacancy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InternalVacancyRepository extends CrudRepository<InternalVacancy,Long> {

    @Query("select i from InternalVacancy i where i.placementOfWork=:pw")
    List<InternalVacancy> findByPlacementOfWork(@Param("pw") String pw);
}
