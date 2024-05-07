package com.team2.fithub.model.dao;

import java.util.List;

import com.team2.fithub.model.dto.Program;
import com.team2.fithub.model.dto.SearchCondition;

public interface ProgramDao {
	
	int insertProgram(Program program);
    
    Program selectProgram(int id);
//    
//    void updateProgram(Program program);
//    
//    void deleteProgram(int id);
//    
    List<Program> selectAllProgram();
//    
//    List<Program> selectProgramsByMentorId(int mentorId);
    
    List<Program> searchProgram(SearchCondition condition);
    
}
