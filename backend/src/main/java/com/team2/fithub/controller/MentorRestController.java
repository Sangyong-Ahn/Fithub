package com.team2.fithub.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.team2.fithub.model.dto.Chat;
import com.team2.fithub.model.dto.Mentor;
import com.team2.fithub.model.dto.Review;
import com.team2.fithub.model.dto.User;
import com.team2.fithub.service.ChatService;
import com.team2.fithub.service.MentorService;
import com.team2.fithub.service.ReviewService;
import com.team2.fithub.service.UserService;
import com.team2.fithub.util.JwtUtil;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/mentor")
@CrossOrigin("*")
public class MentorRestController {

	private final MentorService ms;
	private final ChatService cs;
	private final ReviewService rs;
	private final UserService us;

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	public MentorRestController(MentorService ms, ChatService cs, ReviewService rs, UserService us) {
		this.ms = ms;
		this.cs = cs;
		this.rs = rs;
		this.us = us;
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
			Mentor existingMentor = ms.findMentorByEmail(mentor.getEmail());
			if(existingMentor != null)
				return new ResponseEntity<>("이미 사용중인 이메일입니다.", HttpStatus.BAD_REQUEST);
			if (mentor.getEmail().isEmpty())
	            return new ResponseEntity<>("이메일을 입력하세요.", HttpStatus.BAD_REQUEST);
			
			String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	        if (!mentor.getEmail().matches(emailPattern))
	            return new ResponseEntity<>("올바른 이메일 형식이 아닙니다.", HttpStatus.BAD_REQUEST);
			if (mentor.getPassword().isEmpty())
	            return new ResponseEntity<>("비밀번호를 입력하세요.", HttpStatus.BAD_REQUEST);
			if (mentor.getName().isEmpty())
	            return new ResponseEntity<>("이름을 입력하세요.", HttpStatus.BAD_REQUEST);
			if (mentor.getDateOfBirth() == null)
	            return new ResponseEntity<>("생년월일을 입력하세요.", HttpStatus.BAD_REQUEST);
			if (mentor.getDateOfBirth().length() != 8)
	            return new ResponseEntity<>("생년월일을 8자리로 입력하세요.", HttpStatus.BAD_REQUEST);
			if (mentor.getGender().isEmpty())
				return new ResponseEntity<>("성별을 선택해주세요.", HttpStatus.BAD_REQUEST);
			if (mentor.getPhoneNumber().isEmpty())
				return new ResponseEntity<>("전화 번호를 선택해주세요.", HttpStatus.BAD_REQUEST);
			if (!mentor.getPhoneNumber().matches("[0-9]+")) {
	            return new ResponseEntity<>("전화번호는 숫자로만 입력해야 합니다.", HttpStatus.BAD_REQUEST);
	        }
			
			int result = ms.addMentor(mentor);
			if (result == 1)
				return new ResponseEntity<>(result, HttpStatus.CREATED);
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("올바른 생년월일이 아닙니다.", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> mentorDetails(@PathVariable("id") int id) {
		try {
			Mentor mentor = ms.findMentor(id);
			if (mentor == null)
				return new ResponseEntity<>(mentor, HttpStatus.NOT_FOUND);

			String fileName = id + "_profile.png";
			String uploadDir = "C:/img/mentor";

			Path filePath = Paths.get(uploadDir).resolve(fileName);
			Resource resource = new UrlResource(filePath.toUri());

			mentor.setProfileImage(resource.exists() ? uploadDir + "/" + fileName : "C:/img/mentor/default.png");

			return new ResponseEntity<Mentor>(mentor, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}/chat")
	public ResponseEntity<?> mentorChats(@PathVariable("id") int id) {
		
		List<User> users = us.findUserChatWithMentor(id);
		for(User user : users) {
			List<Chat> chats = cs.findChat(id, user.getId());
			user.setChats(chats);
		}
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/{id}/review")
	public ResponseEntity<?> mentorReviews(@PathVariable("id") int id) {
		List<Review> review = rs.findReviewByMentor(id);
		if (review.isEmpty())
			return new ResponseEntity<>(review, HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(review, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	// 지저분해서 수정 필요할듯
//	public ResponseEntity<?> mentorModify(@PathVariable("id") int id, @RequestPart("mentor") Mentor newMentor,
//			@RequestPart(value = "file", required = false) MultipartFile file) {
	public ResponseEntity<?> mentorModify(@PathVariable("id") int id, @RequestBody Mentor newMentor) {
		try {
			Mentor mentor = ms.findMentor(id);
			if (mentor == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			if (newMentor.getName() != null) {
				mentor.setName(mentor.getName());
			}
			if (newMentor.getPassword() != null) {
				mentor.setPassword(newMentor.getPassword());
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
//			if (file != null && !file.isEmpty()) {
//				String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//				String newFileName = id + "_profile" + getFileExtension(fileName);
//				String uploadDir = "C:/img/mentor";
//
//				Path uploadPath = Paths.get(uploadDir);
//				if (!Files.exists(uploadPath)) {
//					Files.createDirectories(uploadPath);
//				}
//				try {
//					Path existingFilePath = uploadPath.resolve(newFileName);
//					if (Files.exists(existingFilePath)) {
//						Files.delete(existingFilePath);
//					}
//					Path filePath = uploadPath.resolve(newFileName);
//					Files.copy(file.getInputStream(), filePath);
//				} catch (IOException e) {
//					return new ResponseEntity<>("Failed to upload file.", HttpStatus.INTERNAL_SERVER_ERROR);
//				}
//			}
			int result = ms.modifyMentor(mentor);
			if (result == 1)
				return new ResponseEntity<>(mentor, HttpStatus.OK);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> mentorRemove(@PathVariable("id") int id) {
		try {
			Mentor mentor = ms.findMentor(id);
			if (mentor == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			int result = ms.removeMentor(id);
			if (result == 1)
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
				return new ResponseEntity<>("해당 유저가 존재하지 않습니다." + email, HttpStatus.NOT_FOUND);
			}

			if (!mentor.getPassword().equals(password)) {
				return new ResponseEntity<>("비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED);
			}
			
			List<Review> reviews = rs.findReviewByMentor(mentor.getId());
			for(Review review : reviews) {
				User user = us.findUser(review.getUserId());
				review.setUserInfo(user);
			}
			Double reviewAvgScore = rs.findReivewAvgScore(mentor.getId());
			
			mentor.setReviews(reviews);
			mentor.setReviewAvgScore(reviewAvgScore);
			
			// 로그인 성공 시 세션에 사용자 정보 저장
			mentor.setAccessToken(jwtUtil.createToken(mentor.getEmail()));
			
			return new ResponseEntity<>(mentor, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@GetMapping("/logout")
	public ResponseEntity<String> mentorLogout() {
		try {
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
