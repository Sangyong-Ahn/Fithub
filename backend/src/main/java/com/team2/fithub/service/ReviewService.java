package com.team2.fithub.service;

import java.util.List;

import com.team2.fithub.model.dto.Review;

public interface ReviewService {
	
	int addReview(Review review);
	
	Review findReview(int id);
	
	List<Review> findReviewByMentor(int mentorId);
	
	double findReivewAvgScore(int mentorId);
	
	int modifyReview(Review review);
	
	int removeReview(int id);
}
