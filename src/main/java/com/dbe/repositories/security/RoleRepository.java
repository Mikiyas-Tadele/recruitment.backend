package com.dbe.repositories.security;

import com.dbe.domain.security.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Mikiyas on 08/03/2017.
 */
public interface RoleRepository extends CrudRepository<Role,Long> {
    Role findByName(String name);

}
