package com.example.practice_class1.controller;

import com.example.practice_class1.Dtos.ProductRequestDto;
import com.example.practice_class1.Dtos.ProductResponseDto;
import com.example.practice_class1.Model.Product;
import com.example.practice_class1.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {
    private ProductService productService;
    private ModelMapper modelMapper;
    //to inject this use constructor
    public ProductController(ProductService productService,ModelMapper modelMapper)
    {
        this.productService=productService;
        this.modelMapper=modelMapper;
    }
    @GetMapping("/products/{id}")//to tell this is special class
    public ProductResponseDto getProductDetails(@PathVariable ("id") int productId)
    {
         Product product= productService.getSingleProduct(productId);
//         return modelMapper.map(product,ProductResponseDto.class);
        return convertToProductResponseDto(product);
    }
    //get all products
    @GetMapping("/products")
    public List<ProductResponseDto> getAllProducts(){
        List<Product> products=productService.getAllProducts();
        List<ProductResponseDto> productResponseDtos=new ArrayList<>();
        for(Product product:products)
        {
            productResponseDtos.add(convertToProductResponseDto(product));
        }
        return productResponseDtos;
    }

    @PostMapping("/products")
    //we need to send Dto back convert return type to ProductResponseDto
    public ProductResponseDto createnewProduct(@RequestBody ProductRequestDto productRequestDto)
    {
        Product product= productService.addProduct(
                productRequestDto.getTitle(),
                productRequestDto.getDescription(),
                productRequestDto.getImage(),
                productRequestDto.getCategory(),
                productRequestDto.getPrice()
        );
        return convertToProductResponseDto(product);


    }

    private ProductResponseDto convertToProductResponseDto(Product product)
    {
        String categoryTitle=product.getCategory().getTitle();
        ProductResponseDto productResponseDto=modelMapper.map(product,ProductResponseDto.class);
        productResponseDto.setCategory(categoryTitle);
        return productResponseDto;

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




