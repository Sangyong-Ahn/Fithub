package com.team2.fithub.model.dao;

import java.util.List;

import com.team2.fithub.model.dto.Time;

public interface TimeDao {
	
	int insertTime(Time time);
	
	Time selectTime(int id);
	
	List<Time> selectTimeByProgram(int programId);
	
	int increaseReserveNum(int id);
	
	int decreaseReserveNum(int id);
}
