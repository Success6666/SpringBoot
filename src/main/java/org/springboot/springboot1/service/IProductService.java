package org.springboot.springboot1.service;

import org.springboot.springboot1.entity.Product;

import java.util.List;

public interface IProductService {
    List<Product> findAll();

    List<Product> findByCategoryId(Integer categoryId);

    Product findById(Integer id);

    void add(Product product);

    void update(Product product);

    void delete(Integer id);
}
