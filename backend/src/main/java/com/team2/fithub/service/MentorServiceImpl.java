package com.team2.fithub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team2.fithub.model.dao.MentorDao;
import com.team2.fithub.model.dao.ProgramDao;
import com.team2.fithub.model.dao.TimeDao;
import com.team2.fithub.model.dao.UserDao;
import com.team2.fithub.model.dto.Mentor;
import com.team2.fithub.model.dto.Program;
import com.team2.fithub.model.dto.Review;
import com.team2.fithub.model.dto.Time;
import com.team2.fithub.model.dto.User;

@Service
public class MentorServiceImpl implements MentorService {

	private MentorDao mentorDao;
	private ProgramDao programDao;
	private TimeDao timeDao;
	private UserDao userDao;
	
	@Autowired
	public MentorServiceImpl(MentorDao mentorDao, ProgramDao programDao, TimeDao timeDao, UserDao userDao) {
		this.mentorDao = mentorDao;
		this.programDao = programDao;
		this.timeDao = timeDao;
		this.userDao = userDao;
	}
	
	@Override
	@Transactional
	public int addMentor(Mentor mentor) {
		try {
	        return mentorDao.insertMentor(mentor);
	    } catch (Exception e) {
	        throw e;
	    }
	}
	
	@Override
	public List<Mentor> findAllMentor() {
		return mentorDao.selectAllMentor();
	}

	@Override
	public Mentor findMentor(int id) {
		return mentorDao.selectMentor(id);
	}
	
	@Override
	public Mentor findMentorByEmail(String email) {
		return mentorDao.selectMentorByEmail(email);
	}

	@Transactional
	@Override
	public int modifyMentor(Mentor mentor) {
		return mentorDao.updateMentor(mentor);
	}

	@Transactional
	@Override
	public int removeMentor(int id) {
		return mentorDao.deleteMentor(id);
	}
	
	@Override
	public List<Program> findAllProgramByMentor(int id) {
		List<Program> programList = programDao.selectProgramsByMentorId(id);
		for(Program program : programList) {
			List<Time> times = timeDao.selectTimeByProgram(program.getId());
			for(Time time : times) {
				List<User> users = userDao.selectUsersByTimeId(time.getId());
				time.setUsers(users);
			}
			program.setTimes(times);
			
			Mentor mentorInfo = mentorDao.selectMentor(program.getMentorId());
			program.setMentorInfo(mentorInfo);
		}
		return programList;
	}
}
