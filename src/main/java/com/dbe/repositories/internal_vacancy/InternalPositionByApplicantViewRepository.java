package com.dbe.repositories.internal_vacancy;

import com.dbe.domain.internal_vacancy.InternalPositionByApplicantView;
import com.dbe.repositories.ReadOnlyRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InternalPositionByApplicantViewRepository extends ReadOnlyRepository<InternalPositionByApplicantView,Long> {
    @Query("select iapp from InternalPositionByApplicantView iapp where iapp.managerial=:managerialPosition")
    List<InternalPositionByApplicantView> findByManagerialPositions(@Param("managerialPosition") Long managerialPosition);
}
