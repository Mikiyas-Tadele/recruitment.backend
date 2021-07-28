package com.dbe.repositories.applicant;

import com.dbe.domain.applicant.AppliedPersonelView;
import com.dbe.repositories.ReadOnlyRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;

public interface AppliedPersonelViewRepository extends ReadOnlyRepository<AppliedPersonelView,Long>,JpaSpecificationExecutor<AppliedPersonelView> {

    List<AppliedPersonelView> findByVacancyId(Long vacancyId);
}
