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
import com.team2.fithub.model.dto.User;
import com.team2.fithub.service.ChatService;
import com.team2.fithub.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserRestController {
	
	private final UserService us;
	private final ChatService cs;
	
	@Autowired
    private HttpSession httpSession;
	
	@Autowired
	public UserRestController(UserService us, ChatService cs) {
		this.us = us;
		this.cs = cs;
	}
	
	@GetMapping("")
	public ResponseEntity<?> userList() {
		List<User> userList = us.findAllUser();
		if (userList.isEmpty())
			return new ResponseEntity<>(userList, HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(userList, HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<?> userAdd(@RequestBody User user) {
		try {
			int result = us.addUser(user);
			if(result == 1)
				return new ResponseEntity<>(result, HttpStatus.CREATED);
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> userDetails(@PathVariable("id") int id) {
		User user = us.findUser(id);
		if (user == null)
			return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@GetMapping("/{id}/chats")
	public ResponseEntity<?> userChats(@PathVariable("id") int id) {
		Map<Integer, List<Chat>> chat = cs.findChatByUser(id);
		if (chat.isEmpty())
			return new ResponseEntity<>(chat, HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(chat, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> userUpdate(@PathVariable("id") int id, @RequestBody User newUser) {
		try {
			User user = us.findUser(id);
	        if (user == null) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	        
	        if (newUser.getPassword() != null) {
	        	user.setPassword(newUser.getPassword());
	        }
	        if (newUser.getGender() != null) {
	        	user.setGender(newUser.getGender());
	        }
	        if (newUser.getPhoneNumber() != null) {
	        	user.setPhoneNumber(newUser.getPhoneNumber());
	        }
	        if (newUser.getLatitude() != null) {
	        	user.setLatitude(newUser.getLatitude());
	        }
	        if (newUser.getLongitude() != null) {
	        	user.setLongitude(newUser.getLongitude());
	        }
	        
			int result = us.modifyUser(user);
			if(result == 1)
				return new ResponseEntity<>(result, HttpStatus.OK);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> userRemove(@PathVariable("id") int id) {
		try {
			User user = us.findUser(id);
	        if (user == null) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	        
			int result = us.removeUser(id);
			if(result == 1)
				return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> userLogin(@RequestParam String email, @RequestParam String password) {
	    try {
	    	User user = us.findUserByEmail(email);
	        
	        if (user == null) {
	            return new ResponseEntity<>("User not found for email: " + email, HttpStatus.NOT_FOUND);
	        }
	        
	        if (!user.getPassword().equals(password)) {
	            return new ResponseEntity<>("Wrong password", HttpStatus.UNAUTHORIZED);
	        }

	        // 로그인 성공 시 세션에 사용자 정보 저장
	        httpSession.setAttribute("loginUser", user);
	        return new ResponseEntity<>(user, HttpStatus.OK);
	    } catch (Exception e) {
	        return exceptionHandling(e);
	    }
	}
	
	@GetMapping("/logout")
    public ResponseEntity<String> userlogout() {
        try {
            // 세션에서 사용자 정보 삭제
            httpSession.removeAttribute("loginUser");
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
