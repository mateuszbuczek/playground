import React from 'react';
import {Match} from '../pages/TeamPage';
import {Link} from 'react-router-dom';

export const MatchDetailCard = ({match, teamName} : {match: Match, teamName: string}) => {
    const otherTeam = match.team1 === teamName ? match.team2 : match.team1;
    return (
        <div className="MatchDetailCard">
            <h3>Latest Matches</h3>
            <h1>vs <Link to={`/teams/${otherTeam}`}>{otherTeam}</Link></h1>
            <h2>{match.date}</h2>
            <h3>at {match.venue}</h3>
            <h3>{match.winner} won by {match.resultMargin} {match.result}</h3>
        </div>
    );
}
