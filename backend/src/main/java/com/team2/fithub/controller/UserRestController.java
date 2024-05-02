package com.team2.fithub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team2.fithub.model.dto.User;
import com.team2.fithub.service.UserService;

@RestController
@RequestMapping("/user")
public class UserRestController {
	
	private final UserService userService;
	
	@Autowired
	public UserRestController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("")
	public ResponseEntity<?> insert(@RequestBody User user) {
		try {
			int result = userService.addUser(user);
			if(result == 1)
				return new ResponseEntity<>(result, HttpStatus.CREATED);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/login")
	public ResponseEntity<User> select(String email) {
		User user = userService.findUser(email);
		if (user == null)
			return new ResponseEntity<User>(user, HttpStatus.NOT_FOUND);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@PutMapping("")
	public ResponseEntity<?> update(@ModelAttribute User user) {
		try {
			int result = userService.modifyUser(user);
			if(result == 1)
				return new ResponseEntity<>(result, HttpStatus.OK);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@DeleteMapping("")
	public ResponseEntity<?> userRemove(String email) {
		try {
			int result = userService.removeUser(email);
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
