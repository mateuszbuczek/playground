package com.example.ipl.data;

import com.example.ipl.Team;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

  private final EntityManager entityManager;

  @Override
  @Transactional
  public void afterJob(JobExecution jobExecution) {
    if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
        log.info("IPL data import completed");

        Set<Team> teams = createTeamSet();
        updateTeamTotalWins(teams);
        teams.forEach(entityManager::persist);
        teams.forEach(team -> log.info("{}", team));
    }
  }

    private void updateTeamTotalWins(Set<Team> teams) {
        entityManager.createQuery("select winner, count(winner) from Match group by winner", Object[].class)
                .getResultList()
                .stream()
                .filter(entry-> !entry[0].equals("NA"))
                .forEach(entry -> teams.stream()
                        .filter(team -> team.getTeamName().equals(entry[0]))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("error for team: " + entry[0]))
                        .setTotalWins((Long) entry[1]));
    }

    private Set<Team> createTeamSet() {
        return Stream.of(
                entityManager
                        .createQuery("select team1, count(team1) from Match group by team1", Object[].class)
                        .getResultList(),
                entityManager
                        .createQuery("select team2, count(team2) from Match group by team2", Object[].class)
                        .getResultList())
                .flatMap(Collection::stream)
                .map(entry -> Team.builder()
                        .teamName((String) entry[0])
                        .totalMatches((Long) entry[1])
                        .build())
                .collect(Collectors.toSet());
    }
}
