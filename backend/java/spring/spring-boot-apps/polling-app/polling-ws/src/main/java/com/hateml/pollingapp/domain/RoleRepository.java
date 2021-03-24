package com.hateml.pollingapp.domain;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository {

    Optional<Role> findByName(RoleName roleName);
}
