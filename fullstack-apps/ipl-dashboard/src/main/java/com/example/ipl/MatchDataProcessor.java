package com.example.ipl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;

@Slf4j
class MatchDataProcessor implements ItemProcessor<MatchInput, Match> {

    @Override
    public Match process(final MatchInput item) throws Exception {
        Match match = new Match();
        match.setId(Long.valueOf(item.getId()));
        match.setCity(item.getCity());
        match.setDate(LocalDate.parse(item.getDate()));
        match.setPlayerOfMatch(item.getPlayer_of_match());
        match.setVenue(item.getVenue());

        boolean isTeam1TossWinner = item.getToss_winner().equals(item.getTeam1());
        if ("bat".equals(match.getTossDecision())) {
            match.setTeam1(item.getToss_winner());
            match.setTeam2(isTeam1TossWinner ? item.getTeam2() : item.getTeam1());
        } else {
            match.setTeam1(isTeam1TossWinner ? item.getTeam2() : item.getTeam1());
            match.setTeam2(item.getToss_winner());
        }

        match.setTossWinner(item.getToss_winner());
        match.setTossDecision(item.getToss_decision());
        match.setWinner(item.getWinner());
        match.setResult(item.getResult());
        match.setResultMargin(item.getResult_margin());
        match.setUmpire1(item.getUmpire1());
        match.setUmpire2(item.getUmpire2());
        return match;
    }
}
