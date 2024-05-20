package com.example.practice_class1.repository;

import com.example.practice_class1.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    //create add object
    Product save(Product product);

    //find all products in db
    List<Product> findAll();

    //delete product and get single product
    Product findByIdIs(Long id);
}

