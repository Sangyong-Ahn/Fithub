package com.team2.fithub.model.dao;

import java.util.List;

import com.team2.fithub.model.dto.Match;

public interface MatchDao {
	
	int insertMatch(Match match);
	
	Match selectMatch(int id);
	
	List<Match> selectMatchByUser(int userId);
	
	int deleteMatch(int id);
}
