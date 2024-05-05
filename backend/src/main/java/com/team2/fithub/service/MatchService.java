package com.team2.fithub.service;

import com.team2.fithub.model.dto.Match;

public interface MatchService {
	
	int addMatch(Match match);
	
	int removeMatch(int id);
}
