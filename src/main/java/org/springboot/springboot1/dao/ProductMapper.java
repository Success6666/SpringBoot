package org.springboot.springboot1.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springboot.springboot1.entity.Product;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<Product> findAll();

    List<Product> findByCategoryId(Integer categoryId);

    Product findById(Integer id);

    void insert(Product product);

    void update(Product product);

    void delete(Integer id);
}
