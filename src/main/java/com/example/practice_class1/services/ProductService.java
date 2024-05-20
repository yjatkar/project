package com.example.practice_class1.services;

import com.example.practice_class1.Dtos.FakeStoreDto;
import com.example.practice_class1.Dtos.ProductResponseDto;
import com.example.practice_class1.Model.Product;
import com.example.practice_class1.exception.ProductNotFoundException;

import java.util.List;

public interface ProductService {
    public Product getSingleProduct(Long productId) throws ProductNotFoundException;
    public List<Product> getAllProducts();
    public Product addProduct(
            String title,
            String description,
            String image,
            String category,
            double price);
    public Product deleteProduct(Long productId) throws ProductNotFoundException;
    public Product updateProduct(Long productId,String title,String Description,String image,String category,Double price)
            throws ProductNotFoundException;
    public Product replaceProduct(Long productId,String title,String Description,String image,String category,Double price)
            throws ProductNotFoundException;


}



//Looks like you've created a simple interface ProductService for
//handling product-related operations.
//This interface defines a method getSingleProduct() which
//takes an integer productId as input and returns a Product object.
//This interface serves as a contract that any class implementing it must adhere to.
//It abstracts away the details of how the product is fetched,
//allowing for flexibility and separation of concerns in your codebase.