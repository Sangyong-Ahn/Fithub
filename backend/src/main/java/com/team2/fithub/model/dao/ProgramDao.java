package com.team2.fithub.model.dao;

import java.util.List;

import com.team2.fithub.model.dto.Program;
import com.team2.fithub.model.dto.SearchCondition;

public interface ProgramDao {
	
	int insertProgram(Program program);
	
	int updateProgramThumbnailSavePath(int id, String thumbnailSavePath);
	
	int updateProgramThumbnail(int id, String thumbnail);
	
	String selectProgramThumbnailPath(int id);
    
    Program selectProgram(int id);
    
    Program selectProgramByTimeId(int timeId);
//    
//    void updateProgram(Program program);
//    
//    void deleteProgram(int id);
//    
    List<Program> selectAllProgram();
//    
    List<Program> selectProgramsByMentorId(int mentorId);
    
    List<Program> searchProgram(SearchCondition condition);
    
    int deleteProgram(int id);
    
}
