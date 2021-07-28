package com.dbe.repositories.applicant;

import com.dbe.domain.applicant.AppliedPersonelView;
import com.dbe.repositories.ReadOnlyRepository;

import java.util.List;

public interface AppliedPersonelViewRepository extends ReadOnlyRepository<AppliedPersonelView,Long> {

    List<AppliedPersonelView> findByVacancyId(Long vacancyId);
}
