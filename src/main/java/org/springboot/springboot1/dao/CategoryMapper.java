package org.springboot.springboot1.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springboot.springboot1.entity.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> getCategories();
}

