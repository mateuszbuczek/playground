import React from 'react';
import {Match} from '../pages/TeamPage'
import {Link} from 'react-router-dom'

export const MatchSmallCard = ({match, teamName} : {match: Match, teamName: string}) => {
    const otherTeam = match.team1 === teamName ? match.team2 : match.team1;
    return (
        <div className="MatchSmallCard">
            <h3>vs <Link to={`/teams/${otherTeam}`}>{otherTeam}</Link></h3>
            <p>{match.winner} won by {match.resultMargin} {match.result}</p>
        </div>
    );
}
