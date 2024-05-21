package com.team2.fithub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team2.fithub.model.dao.MentorDao;
import com.team2.fithub.model.dao.ProgramDao;
import com.team2.fithub.model.dao.ReviewDao;
import com.team2.fithub.model.dao.TimeDao;
import com.team2.fithub.model.dao.UserDao;
import com.team2.fithub.model.dto.Mentor;
import com.team2.fithub.model.dto.Program;
import com.team2.fithub.model.dto.Review;
import com.team2.fithub.model.dto.SearchCondition;
import com.team2.fithub.model.dto.Time;
import com.team2.fithub.model.dto.User;

@Service
public class ProgramServiceImpl implements ProgramService{
	
	private ProgramDao programDao;
	private TimeDao timeDao;
	private MentorDao mentorDao;
	private ReviewDao reviewDao;
	private UserDao userDao;
	
	@Autowired
    public ProgramServiceImpl(ProgramDao programDao, TimeDao timeDao, MentorDao mentorDao, ReviewDao reviewDao, UserDao userDao) {
        this.programDao = programDao;
        this.timeDao = timeDao;
        this.mentorDao = mentorDao;
        this.reviewDao = reviewDao;
        this.userDao = userDao;
    }

	@Override
	@Transactional
	public int addProgram(Program program) {
		 try {
	        int p_result = programDao.insertProgram(program);
	        if (p_result != 1) {
	            throw new RuntimeException("Failed to add program.");
	        }
	        
	        int programId = program.getId();
	        for (Time time : program.getTimes()) {
	            time.setProgramId(programId);
	            int t_result = timeDao.insertTime(time);
	            if (t_result != 1) {
	                throw new RuntimeException("Failed to add time for program.");
	            }
	        }
	        return 1;
	    } catch (Exception e) {
	        throw e;
	    }
	}
	
	@Override
    public int updateProgramThumbnailSavePath(int programId, String thumbnailSavePath) {
        return programDao.updateProgramThumbnailSavePath(programId, thumbnailSavePath);
    }
	
	@Override
    public int updateProgramThumbnail(int programId, String thumbnail) {
        return programDao.updateProgramThumbnail(programId, thumbnail);
    }
	
	@Override
    public String getProgramThumbnailPath(int programId) {
        return programDao.selectProgramThumbnailPath(programId);
    }
	
	@Override
	public Program findProgram(int id) {
		Program program = programDao.selectProgram(id);
		List<Time> times = timeDao.selectTimeByProgram(id);
		program.setTimes(times);
		
		List<Review> reviews = reviewDao.selectReviewByMentor(program.getMentorId());
		for(Review review : reviews) {
			User user = userDao.selectUser(review.getUserId());
			review.setUserInfo(user);
		}
		Double reviewAvgScore = reviewDao.reviewAvgScore(program.getMentorId());
		Mentor mentorInfo = mentorDao.selectMentor(program.getMentorId());
		
		mentorInfo.setReviews(reviews);
		mentorInfo.setReviewAvgScore(reviewAvgScore);
		
		program.setMentorInfo(mentorInfo);
		return program;
	}

	@Override
	public List<Program> findAllProgram() {
		List<Program> programList = programDao.selectAllProgram();
		for(Program program : programList) {
			List<Time> times = timeDao.selectTimeByProgram(program.getId());
			program.setTimes(times);
			
			List<Review> reviews = reviewDao.selectReviewByMentor(program.getMentorId());
			Double reviewAvgScore = reviewDao.reviewAvgScore(program.getMentorId());
			Mentor mentorInfo = mentorDao.selectMentor(program.getMentorId());
			
			mentorInfo.setReviews(reviews);
			mentorInfo.setReviewAvgScore(reviewAvgScore);
			program.setMentorInfo(mentorInfo);
		}
		return programList;
	}

	@Override
	public List<Program> searchProgram(SearchCondition condition) {
		List<Program> programList = programDao.searchProgram(condition);
		for(Program program : programList) {
			List<Time> times = timeDao.selectTimeByProgram(program.getId());
			program.setTimes(times);
			
			List<Review> reviews = reviewDao.selectReviewByMentor(program.getMentorId());			
			Double reviewAvgScore = reviewDao.reviewAvgScore(program.getMentorId());
			Mentor mentorInfo = mentorDao.selectMentor(program.getMentorId());
			
			mentorInfo.setReviews(reviews);
			mentorInfo.setReviewAvgScore(reviewAvgScore);
			program.setMentorInfo(mentorInfo);
		}
		return programList;
	}
	
	@Override
    public int removeProgram(int id) {
        return programDao.deleteProgram(id);
    }

	@Override
	public List<Program> getMentorProgram(int mentorId) {
		return programDao.selectProgramsByMentorId(mentorId);
	}

}
