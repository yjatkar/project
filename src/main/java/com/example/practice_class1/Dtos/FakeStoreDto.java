package com.example.practice_class1.Dtos;

import com.example.practice_class1.Model.Category;
import com.example.practice_class1.Model.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreDto {
    private Long id;
    private String title;
    private String description;
    private double price;
    private String image;
    private String category;

    public Product toProduct() {
        Product product = new Product();
        product.setId(id);
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setImage(image);
//        product.setCategory(category);
        Category categoryObj=new Category();
        categoryObj.setTitle(category);
        product.setCategory(categoryObj);
        return product;

    }


}
