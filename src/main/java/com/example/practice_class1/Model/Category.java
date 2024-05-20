package com.example.practice_class1.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity

public class Category extends BaseModel {
    private String title;
    @OneToMany(mappedBy = "category")
    List<Product> products;
}

