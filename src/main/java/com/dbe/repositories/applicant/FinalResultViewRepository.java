package com.dbe.repositories.applicant;

import com.dbe.domain.applicant.FinalResultView;
import com.dbe.repositories.ReadOnlyRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinalResultViewRepository extends ReadOnlyRepository<FinalResultView,Long>{

    List<FinalResultView> findByVacancyId(Long vacancyId);

}
