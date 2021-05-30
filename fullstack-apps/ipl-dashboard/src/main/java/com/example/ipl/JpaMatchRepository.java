package com.example.ipl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface JpaMatchRepository extends CrudRepository<Match, Long> {

  List<Match> getByTeam1OrTeam2OrderByDateDesc(String team1, String team2, Pageable pageable);

  List<Match> getByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(
          String team1, LocalDate from1, LocalDate to1,
          String team2, LocalDate from2, LocalDate to2);

  @Query("select m from Match m where (m.team1 = :teamName or m.team2 = :teamName) and m.date between :startDate and :endDate order by date desc")
  List<Match> getMatchesByTeamBetweenDates(String teamName, LocalDate startDate, LocalDate endDate);

  default List<Match> findLatestMatchesByTeamName(String teamName, int count) {
    Pageable pageable = PageRequest.of(0, count);
    return getByTeam1OrTeam2OrderByDateDesc(teamName, teamName, pageable);
  }

  default List<Match> findMatchesByTeamNameInYear(String teamName, int year) {
    LocalDate startDate = LocalDate.of(year, 1, 1);
    LocalDate endDate = LocalDate.of(year + 1, 1, 1);
    return getMatchesByTeamBetweenDates(teamName, startDate, endDate);
  }
}
