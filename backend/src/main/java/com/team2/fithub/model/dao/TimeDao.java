package com.team2.fithub.model.dao;

import java.util.List;

import com.team2.fithub.model.dto.Time;

public interface TimeDao {
	
	int insertTime(Time time);
	
	List<Time> selectTimeByProgram(int programId);
}
