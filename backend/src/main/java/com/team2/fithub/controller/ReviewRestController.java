package com.team2.fithub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team2.fithub.model.dto.Review;
import com.team2.fithub.model.dto.User;
import com.team2.fithub.service.ReviewService;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/review")
@CrossOrigin("*")
public class ReviewRestController {
	
	private final ReviewService rs;
	
	@Autowired
    private HttpSession httpSession;
	
	@Autowired
	public ReviewRestController(ReviewService rs) {
		this.rs = rs;
	}
	
	@PostMapping("")
	public ResponseEntity<?> userReview(@RequestBody Review review) {
		try {
			int result = rs.addReview(review);
			if(result == 1)
				return new ResponseEntity<>(result, HttpStatus.CREATED);
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> reviewUpdate(@PathVariable("id") int id, @RequestBody Review newReview) {
		try {
			newReview.setId(id);	        
			int result = rs.modifyReview(newReview);
			if(result == 1)
				return new ResponseEntity<>(result, HttpStatus.OK);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> reviewRemove(@PathVariable("id") int id) {
		try {
			Review review = rs.findReview(id);
	        if (review == null) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	        
			int result = rs.removeReview(id);
			if(result == 1)
				return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
