package com.example.vavrweb.user.registration.adapters;

import com.example.vavrweb.user.registration.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface JpaUserRepository extends CrudRepository<User, Long> {
    boolean existsByUsername(String username);
}
