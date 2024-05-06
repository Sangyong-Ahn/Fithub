package com.team2.fithub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team2.fithub.model.dao.ReviewDao;
import com.team2.fithub.model.dto.Review;

@Service
public class ReviewServiceImpl implements ReviewService {

	private ReviewDao reviewDao;
	
	@Autowired
	public ReviewServiceImpl(ReviewDao reviewDao) {
		this.reviewDao = reviewDao;
	}
	
	@Override
	@Transactional
	public int addReview(Review review) {
		return reviewDao.insertReview(review);
	}
	
	@Override
	public Review findReview(int id) {
		return reviewDao.selectReview(id);
	}

	@Override
	public List<Review> findReviewByMentor(int mentorId) {
		return reviewDao.selectReviewByMentor(mentorId);
	}

	@Override
	@Transactional
	public int modifyReview(Review review) {
		return reviewDao.updateReview(review);
	}

	@Override
	@Transactional
	public int removeReview(int id) {
		return reviewDao.deleteReview(id);
	}

}
