package com.example.practice_class1.controller;

import com.example.practice_class1.Dtos.ProductRequestDto;
import com.example.practice_class1.Dtos.ProductResponseDto;
import com.example.practice_class1.Model.Product;
import com.example.practice_class1.services.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    private ProductService productService;
    //to inject this use constructor
    public ProductController(ProductService productService)
    {
        this.productService=productService;
    }
    @GetMapping("/products/{id}")//to tell this is special class
    public ProductResponseDto getProductDetails(@PathVariable ("id") int productId)
    {
         return productService.getSingleProduct(productId);
    }

    @PostMapping("/products")
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto productRequestDto)
    {
        return productService.addProduct(
                productRequestDto.getTitle(),
                productRequestDto.getDescription(),
                productRequestDto.getImage(),
                productRequestDto.getCategory(),
                productRequestDto.getPrice()
        );
    }
}



//1.@RestController annotation is used to indicate that this class
//is a controller where every method returns a domain object instead of a view.
//2.ProductController class has a field productService of type ProductService,
// which will be used to perform business logic related to products.
//3.The constructor ProductController(ProductService productService) is
// used for constructor injection of the ProductService dependency.
//4.@GetMapping("/products/{id}") annotation indicates that
//the getProductDetails method will handle GET requests at the
//specified endpoint /products/{id} where {id} is a path variable.
//5.@PathVariable("id") annotation binds the value of the path variable id
// to the productId parameter of the getProductDetails method.
//6.Inside getProductDetails method, you'll typically have
// logic to fetch product details by ID using the productId,
// here represented as productService.getProductById(productId).




