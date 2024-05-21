package com.team2.fithub.service;

import java.util.List;

import com.team2.fithub.model.dto.Mentor;
import com.team2.fithub.model.dto.Program;

public interface MentorService {
	
	int addMentor(Mentor mentor);
	
	Mentor findMentorByEmail(String email);
	Mentor findMentor(int id);
	
	List<Mentor> findAllMentor();
	
	int modifyMentor(Mentor mentor);
	
	int removeMentor(int id);

	List<Program> findAllProgramByMentor(int id);
}
