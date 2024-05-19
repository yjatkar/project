package com.example.practice_class1.services;

import com.example.practice_class1.Dtos.FakeStoreDto;
import com.example.practice_class1.Dtos.ProductResponseDto;
import com.example.practice_class1.Model.Product;

public interface ProductService {
    public ProductResponseDto getSingleProduct(int productId);
    public ProductResponseDto addProduct(
            String title,
            String description,
            String image,
            String category,
            double price);
}



//Looks like you've created a simple interface ProductService for
//handling product-related operations.
//This interface defines a method getSingleProduct() which
//takes an integer productId as input and returns a Product object.
//This interface serves as a contract that any class implementing it must adhere to.
//It abstracts away the details of how the product is fetched,
//allowing for flexibility and separation of concerns in your codebase.