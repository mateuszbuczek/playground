package com.example.ipl;

import lombok.Value;

@Value
class MatchInput {
  String id;
  String city;
  String date;
  String player_of_match;
  String venue;
  String neutral_venue;
  String team1;
  String team2;
  String toss_winner;
  String toss_decision;
  String winner;
  String result;
  String result_margin;
  String eliminator;
  String method;
  String umpire1;
  String umpire2;
}
