import React, {useEffect, useState} from 'react';
import {useParams} from 'react-router-dom'
import {MatchDetailCard} from '../components/MatchDetailCard';
import {MatchSmallCard} from '../components/MatchSmallCard';
require('dotenv').config()

export type Match = {
    city: string
    date: string
    id: number
    playerOfMatch: string
    result: string
    team1: string
    team2: string
    venue: string
    winner: string
    resultMargin: string
}

type Team = {
    teamName: string
    totalMatches: string
    totalWins: string
    matches: Match[]
}

export const TeamPage = () => {

    const [team, setTeam] = useState<Team>();
    const {teamName} : {teamName: string} = useParams();

    useEffect(() => {
        const fetchMatches = async () => {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/teams/${teamName}`);
            const data = await response.json();
            setTeam(data);
        };
        fetchMatches();
    }, [teamName]);

    if (!team || !team.teamName) {
        return <h1>Team not found</h1>
    } else {
        return (
            <div className="TeamPage">
                <h1>{team?.teamName}</h1>
                {team?.matches[0].result && <MatchDetailCard teamName={team.teamName} match={team.matches[0]}/>}
                {team?.matches.slice(1).map(match => <MatchSmallCard teamName={team.teamName} match={match}/>)}
            </div>
        );
    }
}
