package com.team2.fithub.service;

import java.util.List;


import com.team2.fithub.model.dto.Program;
import com.team2.fithub.model.dto.SearchCondition;

public interface ProgramService {
	
	int addProgram(Program program);
	
	int updateProgramThumbnail(int programId, String thumbnail);
	
	String getProgramThumbnailPath(int programId);
	
	Program findProgram(int id);
	
	List<Program> findAllProgram();
	
	List<Program> searchProgram(SearchCondition condition);
}
