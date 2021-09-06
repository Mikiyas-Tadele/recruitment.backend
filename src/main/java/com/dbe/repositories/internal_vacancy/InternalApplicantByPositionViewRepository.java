package com.dbe.repositories.internal_vacancy;

import com.dbe.domain.internal_vacancy.InternalApplicantByPositionView;
import com.dbe.repositories.ReadOnlyRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InternalApplicantByPositionViewRepository extends ReadOnlyRepository<InternalApplicantByPositionView,Long> {

    @Query("select iapp from InternalApplicantByPositionView iapp where iapp.manageria2=:managerialPosition" +
            " and iapp.manageria3=:managerialPosition and iapp.managerial=:managerialPosition")
    List<InternalApplicantByPositionView> findByManagerialPositions(@Param("managerialPosition") Long managerialPosition);
}
