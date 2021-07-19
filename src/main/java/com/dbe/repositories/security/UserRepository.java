package com.dbe.repositories.security;


import com.dbe.domain.security.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by Mikiyas on 08/03/2017.
 */
public interface UserRepository extends CrudRepository<UserEntity,Long> {
    //UserEntity findByUsername(String name);
    UserEntity findByUsernameAndPassword(String name, String password);
    Optional<UserEntity> findByUsername(String username);
    Boolean existsByUsername(String username);
}
