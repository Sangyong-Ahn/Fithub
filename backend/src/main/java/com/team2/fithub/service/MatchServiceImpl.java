package com.team2.fithub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team2.fithub.model.dao.MatchDao;
import com.team2.fithub.model.dao.MentorDao;
import com.team2.fithub.model.dao.ProgramDao;
import com.team2.fithub.model.dao.TimeDao;
import com.team2.fithub.model.dto.Match;
import com.team2.fithub.model.dto.Mentor;
import com.team2.fithub.model.dto.Program;
import com.team2.fithub.model.dto.Time;

import jakarta.transaction.Transactional;

@Service
public class MatchServiceImpl implements MatchService{
	
	private MatchDao matchDao;
	private TimeDao timeDao;
	private ProgramDao programDao;
	private MentorDao mentorDao;
	
	@Autowired
    public MatchServiceImpl(MatchDao matchDao, TimeDao timeDao, ProgramDao programDao, MentorDao mentorDao) {
        this.matchDao = matchDao;
        this.timeDao = timeDao;
        this.programDao = programDao;
        this.mentorDao = mentorDao;
    }
	
	@Override
	@Transactional
	public int addMatch(Match match) {
		try {
			
			// todo
			// 프로그램별로 한 시간대에만 예약 가능하게 처리 필요
	        
			int timeId = match.getTimeId();
			Time time = timeDao.selectTime(timeId);
			if(time == null) {
				throw new RuntimeException("Time not found for timeId: " + timeId);
			}
			if (time.getCapacity() > time.getReserveNum()) {
				int m_result = matchDao.insertMatch(match);
				if(m_result != 1) {
					throw new RuntimeException("Failed to insert match");
				}
				int t_update = timeDao.increaseReserveNum(timeId);
				if(t_update != 1) {
					throw new RuntimeException("Failed to update reserveNum for timeId: " + timeId);
				}
				return 1;
			} else {
				throw new RuntimeException("Reservation is not available for timeId: " + timeId);
			}
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return 0;
	}

	@Override
	@Transactional
	public int removeMatch(int id) {
		try {
			Match match = matchDao.selectMatch(id);
			if(match == null) {
				throw new RuntimeException("Match not found for id: " + id);
			}
			int timeId = match.getTimeId();
			int m_result = matchDao.deleteMatch(id);
			if(m_result != 1) {
				throw new RuntimeException("Failed to delete for id: " + id);
			}
			int t_update = timeDao.decreaseReserveNum(timeId);
			if(t_update != 1) {
				throw new RuntimeException("Failed to update reserveNum for timeId: " + timeId);
			}
			return 1;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return 0;
	    }
	}

	@Override
	public List<Match> findAllMatchById(int userId) {
		List<Match> matches = matchDao.selectMatchByUser(userId);
        for (Match match : matches) {
        	Time time = timeDao.selectTime(match.getTimeId());
            Program program = programDao.selectProgramByTimeId(match.getTimeId());
            Mentor mentor = mentorDao.selectMentor(match.getMentorId());
            
            match.setProgramInfo(program);
            match.setMentorInfo(mentor);
            match.setTimeInfo(time);
        }
        return matches;
	}
}
