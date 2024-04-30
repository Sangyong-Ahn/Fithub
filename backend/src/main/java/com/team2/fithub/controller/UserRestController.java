package com.team2.fithub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team2.fithub.model.dto.User;
import com.team2.fithub.service.UserService;

@RestController
@RequestMapping("/user")
public class UserRestController {
	
	@Autowired
	UserService us;
	
	@PostMapping("")
	public ResponseEntity<?> insert(@RequestBody User user) {
		try {
			int result = us.addUser(user);
			if(result == 1)
				return new ResponseEntity<>(user, HttpStatus.CREATED);
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
