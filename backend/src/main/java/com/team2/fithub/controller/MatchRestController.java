package com.team2.fithub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team2.fithub.model.dto.Match;
import com.team2.fithub.service.MatchService;

@RestController
@RequestMapping("/match")
@CrossOrigin("*")
public class MatchRestController {
	
	private final MatchService ms;
	
	@Autowired
	public MatchRestController(MatchService ms) {
		this.ms = ms;
	}
	
	@PostMapping("")
	public ResponseEntity<?> matchAdd(@RequestBody Match match) {
		try {
			int result = ms.addMatch(match);
			if(result == 1)
				return new ResponseEntity<>(result, HttpStatus.CREATED);
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> matchRemove(@PathVariable("id") int id) {
		try {
			int result = ms.removeMatch(id);
			if(result == 1)
				return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> matchByUser(@PathVariable("userId") int userId) {
		List<Match> matchList = ms.findAllMatchById(userId);
		return new ResponseEntity<>(matchList, HttpStatus.OK);
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
