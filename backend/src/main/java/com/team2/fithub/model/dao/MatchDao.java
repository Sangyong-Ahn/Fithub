package com.team2.fithub.model.dao;

import com.team2.fithub.model.dto.Match;

public interface MatchDao {
	
	int insertMatch(Match match);
	
	Match selectMatch(int id);
	
	int deleteMatch(int id);
}
