import React, {useEffect, useState} from 'react';
import {Match} from './TeamPage';
import {useParams} from 'react-router-dom';
import {MatchDetailCard} from '../components/MatchDetailCard';
require('dotenv').config()

export const MatchPage = () => {

    const [matches, setMatches] = useState<Match[]>([]);
    const {teamName, year} : { teamName: string, year: string} = useParams();

    useEffect(() => {
        const fetchMatchesInYear = async () => {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/teams/${teamName}/matches?${year}`);
            const data = await response.json();
            setMatches(data);
        };
        fetchMatchesInYear();
    }, [teamName, year]);


    return (

        <div className="MatchPage">
            <h2>MATCH PAGE</h2>
            {matches.map(match => <MatchDetailCard match={match} teamName={teamName}/>)}
        </div>
    );
};
