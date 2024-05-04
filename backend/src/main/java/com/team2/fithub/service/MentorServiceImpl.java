package com.team2.fithub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team2.fithub.model.dao.MentorDao;
import com.team2.fithub.model.dto.Mentor;

@Service
public class MentorServiceImpl implements MentorService {

	private MentorDao mentorDao;
	
	@Autowired
	public MentorServiceImpl(MentorDao mentorDao) {
		this.mentorDao = mentorDao;
	}

	@Autowired
	public void setMentorRepo(MentorDao mentorDao) {
		this.mentorDao = mentorDao;
	}

	public MentorDao getMentorRepo() {
		return this.mentorDao;
	}
	
	@Override
	@Transactional
	public int addMentor(Mentor mentor) {
		return mentorDao.insertMentor(mentor);
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
}
