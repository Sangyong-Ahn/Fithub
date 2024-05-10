package com.team2.fithub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team2.fithub.model.dto.Category;
import com.team2.fithub.model.dto.User;
import com.team2.fithub.service.CategoryService;

@RestController
@RequestMapping("/category")
@CrossOrigin("*")
public class CategoryRestController {
	
	private final CategoryService cs;
	
	@Autowired
	public CategoryRestController(CategoryService cs) {
		this.cs = cs;
	}
	
	@GetMapping("")
	public ResponseEntity<?> categoryList() {
		List<Category> categoryList = cs.findAllCategory();
		if (categoryList.isEmpty())
			return new ResponseEntity<>(categoryList, HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(categoryList, HttpStatus.OK);
	}
}
