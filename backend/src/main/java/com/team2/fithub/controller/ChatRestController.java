package com.team2.fithub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team2.fithub.model.dto.Chat;
import com.team2.fithub.model.dto.Match;
import com.team2.fithub.service.ChatService;

@RestController
@RequestMapping("/chat")
@CrossOrigin("*")
public class ChatRestController {
	
private final ChatService cs;
	
	@Autowired
	public ChatRestController(ChatService cs) {
		this.cs = cs;
	}
	
	@GetMapping("")
	public ResponseEntity<?> chatList(@RequestParam("mentorId") int mentorId, @RequestParam("userId") int userId) {
		List<Chat> chatList = cs.findChat(mentorId, userId);
		return new ResponseEntity<>(chatList, HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<?> chatAdd(@RequestBody Chat chat) {
		try {
			System.out.println(chat);
			int result = cs.addChat(chat);
			if(result == 1)
				return new ResponseEntity<>(result, HttpStatus.CREATED);
			return new ResponseEntity<>("전송에 실패했습니다.", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Sorry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
