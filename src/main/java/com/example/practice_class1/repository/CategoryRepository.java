package com.example.practice_class1.repository;

import com.example.practice_class1.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    //add product
    Category save(Category category);
    Category findByTitle(String name);
    List<Category> findByTitleEndingWith(String ending);
}
