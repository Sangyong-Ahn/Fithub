package com.team2.fithub.service;

import java.util.List;


import com.team2.fithub.model.dto.Program;

public interface ProgramService {
	
	int addProgram(Program program);
	
	Program findProgram(int id);
	
	List<Program> findAllProgram();
}
