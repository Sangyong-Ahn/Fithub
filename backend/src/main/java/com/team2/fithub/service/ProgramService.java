package com.team2.fithub.service;

import java.util.List;


import com.team2.fithub.model.dto.Program;

public interface ProgramService {
	
	int addProgram(Program program);
	
	List<Program> findAllProgram();
}
