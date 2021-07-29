package com.dbe.repositories.security;

import com.dbe.domain.security.VerificationToken;
import org.springframework.data.repository.CrudRepository;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken,Long> {
    VerificationToken findByToken(String token);
}
