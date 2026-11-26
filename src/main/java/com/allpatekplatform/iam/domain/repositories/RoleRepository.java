package com.allpatekplatform.iam.domain.repositories;

import com.allpatekplatform.iam.domain.model.entities.Role;
import com.allpatekplatform.iam.domain.model.valueobjects.Roles;

import java.util.List;
import java.util.Optional;

/**
 * IAM role repository port.
 */
public interface RoleRepository {
    Optional<Role> findByName(Roles name);

    List<Role> findAll();

    Role save(Role role);

    boolean existsByName(Roles name);
}

