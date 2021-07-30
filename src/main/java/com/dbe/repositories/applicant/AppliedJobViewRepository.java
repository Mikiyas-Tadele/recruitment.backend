package com.dbe.repositories.applicant;

import com.dbe.domain.applicant.AppliedJobView;
import com.dbe.repositories.ReadOnlyRepository;

import java.util.List;

public interface AppliedJobViewRepository extends ReadOnlyRepository<AppliedJobView,Long> {
    List<AppliedJobView> findByUserId(Long userId);
}
