package com.team2.fithub.model.dao;

import java.util.List;

import com.team2.fithub.model.dto.Program;

public interface ProgramDao {
	
	int insertProgram(Program program);
    
//    Program selectProgram(int id);
//    
//    void updateProgram(Program program);
//    
//    void deleteProgram(int id);
//    
    List<Program> selectAllProgram();
//    
//    List<Program> selectProgramsByUserId(int userId);
//    
//    List<Program> selectProgramsByMentorId(int mentorId);
    
}
