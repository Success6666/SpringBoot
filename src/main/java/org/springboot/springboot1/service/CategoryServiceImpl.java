package org.springboot.springboot1.service;

import org.springboot.springboot1.dao.CategoryMapper;
import org.springboot.springboot1.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Category> getCategories() {
        return categoryMapper.getCategories();
    }
}

