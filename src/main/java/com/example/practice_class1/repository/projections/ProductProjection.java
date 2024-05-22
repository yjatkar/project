package com.example.practice_class1.repository.projections;

import com.example.practice_class1.Model.Category;

import java.math.BigDecimal;

public interface ProductProjection {
    Long getId();
    String getTitle();
    String getDescription();
    BigDecimal getPrice();
    Category getCategory();
    String getImage();
}
