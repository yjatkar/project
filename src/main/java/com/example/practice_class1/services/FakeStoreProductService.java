package com.example.practice_class1.services;

import com.example.practice_class1.Dtos.FakeStoreDto;
import com.example.practice_class1.Dtos.ProductResponseDto;
import com.example.practice_class1.Model.Product;
import com.example.practice_class1.exception.ProductNotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {
    private RestTemplate restTemplate;

    //WE WANT RESTTEMPLATE TO BE INJECTED HERE
    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException{
        FakeStoreDto fakeStoreDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + productId, FakeStoreDto.class

        );
        if(fakeStoreDto==null)
        {
            throw new ProductNotFoundException(
                    "Product with id"+productId+"not found"
                    +"try a product with id less than 21"
            );
        }
        return fakeStoreDto.toProduct();
    }
    @Override
    public List<Product> getAllProducts(){
//        List<FakeStoreDto> fakeStoreDto=restTemplate.getForObject(
        FakeStoreDto[] fakeStoreDtos=restTemplate.getForObject(
                "https://fakestoreapi.com/products",
                FakeStoreDto[].class
        );
        //convert all fakeStoreDto to productObject
        List<Product> products=new ArrayList<>();
        for(FakeStoreDto fakeStoreDto:fakeStoreDtos)
        {
            products.add(fakeStoreDto.toProduct());
        }
        return products;

    }

    @Override
    public Product addProduct(
        String title,
        String description,
        String image,
        String category,
        double price){
        FakeStoreDto requestDto=new FakeStoreDto();
        requestDto.setTitle(title);
        requestDto.setDescription(description);
        requestDto.setImage(image);
        requestDto.setCategory(category);
        requestDto.setPrice(price);
        FakeStoreDto response=restTemplate.postForObject(
                "https://fakestoreapi.com/products/",
                requestDto,
                FakeStoreDto.class
        );
        return response.toProduct();

    }
    public Product deleteProduct(Long productId) throws ProductNotFoundException{
        FakeStoreDto fakeStoreDto=restTemplate.exchange(
                "https://fakestoreapi.com/products/" + productId,
                HttpMethod.DELETE,
                null,
                FakeStoreDto.class
        ).getBody();

        if(fakeStoreDto==null)
        {
            throw new ProductNotFoundException(
                    "Product with id "+ productId +" not found "
                +"try to delete a product with id less than 21"
        );
        }
        return fakeStoreDto.toProduct();
    }
    @Override
    public Product updateProduct(Long productId,String title,String Description,String image,String category,Double price)
            throws ProductNotFoundException{
        FakeStoreDto requestDto=new FakeStoreDto();
        requestDto.setTitle(title);
        requestDto.setDescription(Description);
        requestDto.setImage(image);
        requestDto.setCategory(category);
        requestDto.setPrice(price);

        // Known Issue: HTTP PATCH is not supported by RestTemplate
        // So below code will NOT work
        // Will throw an exception:
        // Invalid HTTP method: PATCH\n\tat org.springframework.web.client.
        // create request entity to send in patch request body to fakestore
//         HttpEntity<FakeStoreDto> requestEntity = new HttpEntity<>(requestDto);
//
//        ResponseEntity<FakeStoreDto> responseEntity = restTemplate.exchange(
//                "https://fakestoreapi.com/products/" + productId,
//                HttpMethod.PATCH,
//                requestEntity,
//                FakeStoreDto.class
//        );
//
//        FakeStoreDto response = responseEntity.getBody();
//        if (response == null) {
//            throw new ProductNotFoundException(
//                    "Product with id " + productId + " not found");
//        }

        FakeStoreDto response=requestDto;
        response.setId(productId);
        return response.toProduct();
    }

    @Override
    public Product replaceProduct(Long productId,String title,String Description,String image,String category,Double price)
            throws ProductNotFoundException{
        FakeStoreDto requestDto=new FakeStoreDto();
        requestDto.setTitle(title);
        requestDto.setDescription(Description);
        requestDto.setImage(image);
        requestDto.setCategory(category);
        requestDto.setPrice(price);

        //create request entity to send in put request body to fakestore
        HttpEntity<FakeStoreDto> requestEntity=new HttpEntity<>(requestDto);

        FakeStoreDto response=restTemplate.exchange(
                "https://fakestoreapi.com/products/"+productId,
                HttpMethod.PUT,
                requestEntity,
                FakeStoreDto.class
        ).getBody();

        if(response==null)
        {
            throw new ProductNotFoundException(
                    "Product with id"+productId+" not found"
            );
        }
        return response.toProduct();


    }

}
