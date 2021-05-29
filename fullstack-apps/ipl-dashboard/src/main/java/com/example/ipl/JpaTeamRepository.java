package com.example.ipl;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface JpaTeamRepository extends CrudRepository<Team, Long> {

    Optional<Team> findByTeamName(String teamName);
}
