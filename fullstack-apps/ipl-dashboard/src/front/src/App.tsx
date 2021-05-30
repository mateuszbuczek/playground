import React from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import {TeamPage} from './pages/TeamPage';
import {MatchPage} from './pages/MatchPage';

function App() {
    return (
        <div className="App">
            <h1>IPL Dashboard</h1>
            <Router>
                <Switch>
                    <Route path="/teams/:teamName/matches/:year">
                        <MatchPage/>
                    </Route>
                    <Route path="/teams/:teamName">
                        <TeamPage/>
                    </Route>
                </Switch>
            </Router>
        </div>
    );
}

export default App;
