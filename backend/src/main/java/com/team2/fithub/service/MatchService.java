package com.team2.fithub.service;

import java.util.List;

import com.team2.fithub.model.dto.Match;

public interface MatchService {
	
	int addMatch(Match match);
	
	List<Match> findAllMatchById(int userId);
	
	int removeMatch(int id);
}
