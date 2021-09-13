package com.dbe.repositories.internal_vacancy;

import com.dbe.domain.internal_vacancy.InternalApplicationView;
import com.dbe.repositories.ReadOnlyRepository;

import java.util.List;

public interface InternalApplicationViewRepository extends ReadOnlyRepository<InternalApplicationView,Long> {
    List<InternalApplicationView> findByVacancyId(Long vacancyId);
    List<InternalApplicationView> findByEmployeeId(Long employeeId);
}
