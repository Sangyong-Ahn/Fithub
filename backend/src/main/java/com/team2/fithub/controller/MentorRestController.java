package com.team2.fithub.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team2.fithub.model.dto.Chat;
import com.team2.fithub.model.dto.Mentor;
import com.team2.fithub.model.dto.Review;
import com.team2.fithub.model.dto.User;
import com.team2.fithub.service.ChatService;
import com.team2.fithub.service.MentorService;
import com.team2.fithub.service.ReviewService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/mentor")
public class MentorRestController {
	
	private final MentorService ms;
	private final ChatService cs;
	private final ReviewService rs;
	
	@Autowired
    private HttpSession httpSession;
	
	@Autowired
	public MentorRestController(MentorService ms, ChatService cs, ReviewService rs) {
		this.ms = ms;
		this.cs = cs;
		this.rs = rs;
	}
	
	@GetMapping("")
	public ResponseEntity<?> mentorList() {
		List<Mentor> mentorList = ms.findAllMentor();
		if (mentorList.isEmpty())
			return new ResponseEntity<>(mentorList, HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(mentorList, HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<?> mentorAdd(@RequestBody Mentor mentor) {
		try {
			int result = ms.addMentor(mentor);
			if(result == 1)
				return new ResponseEntity<>(result, HttpStatus.CREATED);
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> mentorDetails(@PathVariable("id") int id) {
		Mentor mentor = ms.findMentor(id);
		if (mentor == null)
			return new ResponseEntity<>(mentor, HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(mentor, HttpStatus.OK);
	}
	
	@GetMapping("/{id}/chats")
	public ResponseEntity<?> mentorChats(@PathVariable("id") int id) {
		Map<Integer, List<Chat>> chat = cs.findChatByMentor(id);
		if (chat.isEmpty())
			return new ResponseEntity<>(chat, HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(chat, HttpStatus.OK);
	}
	
	@GetMapping("/{id}/reviews")
	public ResponseEntity<?> mentorReviews(@PathVariable("id") int id) {
		List<Review> review = rs.findReviewByMentor(id);
		if (review.isEmpty())
			return new ResponseEntity<>(review, HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(review, HttpStatus.OK);
	}
	
	// 지저분해서 수정 필요할듯
	@PutMapping("/{id}")
	public ResponseEntity<?> mentorModify(@PathVariable("id") int id, @RequestBody Mentor newMentor) {
		try {
			Mentor mentor = ms.findMentor(id);
	        if (mentor == null) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	        
	        if (newMentor.getPassword() != null) {
	        	mentor.setPassword(newMentor.getPassword());
	        }
	        if (newMentor.getGender() != null) {
	        	mentor.setGender(newMentor.getGender());
	        }
	        if (newMentor.getPhoneNumber() != null) {
	        	mentor.setPhoneNumber(newMentor.getPhoneNumber());
	        }
	        if (newMentor.getContent() != null) {
	        	mentor.setContent(newMentor.getContent());
	        }
	        if (newMentor.getLatitude() != null) {
	        	mentor.setLatitude(newMentor.getLatitude());
	        }
	        if (newMentor.getLongitude() != null) {
	        	mentor.setLongitude(newMentor.getLongitude());
	        }
	        
			int result = ms.modifyMentor(mentor);
			if(result == 1)
				return new ResponseEntity<>(result, HttpStatus.OK);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> mentorRemove(@PathVariable("id") int id) {
		try {
			Mentor mentor = ms.findMentor(id);
	        if (mentor == null) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	        
			int result = ms.removeMentor(id);
			if(result == 1)
				return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> mentorLogin(@RequestParam String email, @RequestParam String password) {
	    try {
	    	Mentor mentor = ms.findMentorByEmail(email);
	        
	        if (mentor == null) {
	            return new ResponseEntity<>("Mentor not found for email: " + email, HttpStatus.NOT_FOUND);
	        }
	        
	        if (!mentor.getPassword().equals(password)) {
	            return new ResponseEntity<>("Wrong password", HttpStatus.UNAUTHORIZED);
	        }

	        // 로그인 성공 시 세션에 사용자 정보 저장
	        httpSession.setAttribute("loginMentor", mentor);
	        return new ResponseEntity<>(mentor, HttpStatus.OK);
	    } catch (Exception e) {
	        return exceptionHandling(e);
	    }
	}
	
	@GetMapping("/logout")
    public ResponseEntity<String> mentorLogout() {
        try {
            // 세션에서 사용자 정보 삭제
            httpSession.removeAttribute("loginMentor");
            return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
