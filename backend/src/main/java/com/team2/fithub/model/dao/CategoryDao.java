package com.team2.fithub.model.dao;

import java.util.List;

import com.team2.fithub.model.dto.Category;

public interface CategoryDao {
	
	List<Category> selectAllCategory();
}
