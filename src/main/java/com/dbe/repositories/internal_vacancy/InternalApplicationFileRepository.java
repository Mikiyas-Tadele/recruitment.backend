package com.dbe.repositories.internal_vacancy;

import com.dbe.domain.internal_vacancy.InternalApplicationFile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface InternalApplicationFileRepository extends CrudRepository<InternalApplicationFile,Long> {
    @Query("select iv from InternalApplicationFile iv join iv.application a where a.id=:applicationId")
    InternalApplicationFile findbyApplicationId(@Param("applicationId")Long applicationId);
}
