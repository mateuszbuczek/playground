package com.example.ipl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
@CrossOrigin
public class TeamController {

  private final JpaTeamRepository teamRepository;
  private final JpaMatchRepository matchRepository;

  @GetMapping(value = "/{teamName}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Team getTeam(@PathVariable String teamName) {
    Team team =
        teamRepository
            .findByTeamName(teamName)
            .orElseThrow(() -> new RuntimeException("was not able to find team by name"));
    team.setMatches(matchRepository.findLatestMatchesByTeamName(teamName, 4));
    return team;
  }

  @GetMapping(value = "/{teamName}/matches", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Match> getTeamMatches(
      @PathVariable String teamName, @RequestParam("year") Integer year) {
    return matchRepository.findMatchesByTeamNameInYear(teamName, year);
  }
}
