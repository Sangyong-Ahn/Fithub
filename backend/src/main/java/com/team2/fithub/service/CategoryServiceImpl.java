package com.team2.fithub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.team2.fithub.model.dao.CategoryDao;
import com.team2.fithub.model.dto.Category;

@Service
public class CategoryServiceImpl implements CategoryService{
	
private CategoryDao categoryDao;
	
	@Autowired
    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

	@Override
	public List<Category> findAllCategory() {
		return categoryDao.selectAllCategory();
	}
}
