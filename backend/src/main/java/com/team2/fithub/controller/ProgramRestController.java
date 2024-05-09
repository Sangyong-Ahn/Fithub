package com.team2.fithub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team2.fithub.model.dto.Program;
import com.team2.fithub.model.dto.SearchCondition;
import com.team2.fithub.model.dto.Time;
import com.team2.fithub.service.ProgramService;

@RestController
@RequestMapping("/program")
@CrossOrigin("*")
public class ProgramRestController {
	
	private final ProgramService ps;
	
	@Autowired
	public ProgramRestController(ProgramService ps) {
		this.ps = ps;
	}
	
	@GetMapping("")
	public ResponseEntity<?> programList() {
		List<Program> programList = ps.findAllProgram();
		if (programList.isEmpty())
			return new ResponseEntity<>(programList, HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(programList, HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<?> programAdd(@RequestBody Program program) {
		try {
			int result = ps.addProgram(program);
			if(result == 1)
				return new ResponseEntity<>(result, HttpStatus.CREATED);
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> programDetails(@PathVariable("id") int id) {
		Program program = ps.findProgram(id);
		if (program == null)
			return new ResponseEntity<>(program, HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(program, HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<?> programDetails(@ModelAttribute SearchCondition condition) {
		System.out.println(condition);
		List<Program> programs = ps.searchProgram(condition);
		if (programs.isEmpty())
			return new ResponseEntity<>(programs, HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(programs, HttpStatus.OK);
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
