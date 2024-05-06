package com.team2.fithub.model.dao;

import java.util.List;

import com.team2.fithub.model.dto.Review;

public interface ReviewDao {
	
	int insertReview(Review review);
	
	Review selectReview(int id);
	// 멘토에게 작성된 리뷰들
	List<Review> selectReviewByMentor(int mentorId);
	
	int updateReview(Review review);
	
	int deleteReview(int id);
}
