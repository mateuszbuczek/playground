package com.example.ipl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JpaMatchRepository extends CrudRepository<Match, Long> {

  List<Match> getByTeam1OrTeam2OrderByDateDesc(String team1, String team2, Pageable pageable);

  default List<Match> findLatestMatchesByTeamName(String teamName, int count) {
    Pageable pageable = PageRequest.of(0, count);
    return getByTeam1OrTeam2OrderByDateDesc(teamName, teamName, pageable);
  }
}
