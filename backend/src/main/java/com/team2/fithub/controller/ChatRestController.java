package com.team2.fithub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team2.fithub.service.ChatService;

@RestController
@RequestMapping("/chat")
public class ChatRestController {
	
private final ChatService cs;
	
	@Autowired
	public ChatRestController(ChatService cs) {
		this.cs = cs;
	}
	
	
}
