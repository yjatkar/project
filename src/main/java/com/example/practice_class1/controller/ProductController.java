package com.example.practice_class1.controller;

import com.example.practice_class1.Dtos.ErrorDto;
import com.example.practice_class1.Dtos.ProductRequestDto;
import com.example.practice_class1.Dtos.ProductResponseDto;
import com.example.practice_class1.Model.Product;
import com.example.practice_class1.exception.ProductNotFoundException;
import com.example.practice_class1.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {
    private ProductService productService;
    private ModelMapper modelMapper;
    //to inject this use constructor
    public ProductController(@Qualifier("selfProductService")ProductService productService, ModelMapper modelMapper)
    {
        this.productService=productService;
        this.modelMapper=modelMapper;
    }
    @GetMapping("/products/{id}")//to tell this is special class
    public ProductResponseDto getProductDetails(@PathVariable ("id") Long productId)
            throws ProductNotFoundException
    {
         Product product= productService.getSingleProduct(productId);
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

    //if we create object we want response code =201
    @PostMapping("/products")
    //we need to send Dto back convert return type to ProductResponseDto
    public ResponseEntity<ProductResponseDto> createnewProduct(@RequestBody ProductRequestDto productRequestDto)
    {
        Product product= productService.addProduct(
                productRequestDto.getTitle(),
                productRequestDto.getDescription(),
                productRequestDto.getImage(),
                productRequestDto.getCategory(),
                productRequestDto.getPrice()
        );
//        return convertToProductResponseDto(product);
        ProductResponseDto productResponseDto= convertToProductResponseDto(product);
        return new ResponseEntity<>(productResponseDto,HttpStatus.CREATED);


    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> deleteProduct(@PathVariable("id") Long productId)
    throws ProductNotFoundException{
        Product product=productService.deleteProduct(productId);
        ProductResponseDto productResponseDto=convertToProductResponseDto(product);
        return new ResponseEntity<>(productResponseDto,HttpStatus.OK);
    }

    @PatchMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("id") Long productId,
                                                            @RequestBody ProductRequestDto productRequestDto)
    throws ProductNotFoundException{
        Product product=productService.updateProduct(
                productId,
                productRequestDto.getTitle(),
                productRequestDto.getDescription(),
                productRequestDto.getImage(),
                productRequestDto.getCategory(),
                productRequestDto.getPrice()
        );
        ProductResponseDto productResponseDto=convertToProductResponseDto(product);
        return new ResponseEntity<>(productResponseDto,HttpStatus.OK);

    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> replaceProduct(@PathVariable("id") Long productId,
                                                            @RequestBody ProductRequestDto productRequestDto )
    throws ProductNotFoundException{
        Product product=productService.replaceProduct(
                productId,
                productRequestDto.getTitle(),
                productRequestDto.getDescription(),
                productRequestDto.getImage(),
                productRequestDto.getCategory(),
                productRequestDto.getPrice());
        ProductResponseDto productResponseDto=convertToProductResponseDto(product);
        return new ResponseEntity<>(productResponseDto,HttpStatus.OK);

    }


    private ProductResponseDto convertToProductResponseDto(Product product) {
        String categoryTitle = product.getCategory().getTitle();
        ProductResponseDto productResponseDto = modelMapper.map(product, ProductResponseDto.class);
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




