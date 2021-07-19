package com.dbe.repositories.applicant;

import com.dbe.domain.applicant.Application;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationRepository extends CrudRepository<Application,Long> {
}
