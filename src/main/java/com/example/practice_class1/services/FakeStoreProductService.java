package com.example.practice_class1.services;

import com.example.practice_class1.Dtos.FakeStoreDto;
import com.example.practice_class1.Dtos.ProductResponseDto;
import com.example.practice_class1.Model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements ProductService {
    private RestTemplate restTemplate;

    //WE WANT RESTTEMPLATE TO BE INJECTED HERE
    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getSingleProduct(int productId) {
        FakeStoreDto fakeStoreDto = restTemplate.getForObject(
                "https://fakestoreapi.com/products/" + productId, FakeStoreDto.class

        );
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
        FakeStoreDto fakeStoreDto=new FakeStoreDto();
        fakeStoreDto.setTitle(title);
        fakeStoreDto.setDescription(description);
        fakeStoreDto.setImage(image);
        fakeStoreDto.setCategory(category);
        fakeStoreDto.setPrice(price);
        FakeStoreDto response=restTemplate.postForObject(
                "https://fakestoreapi.com/products/",
                fakeStoreDto,
                FakeStoreDto.class
        );
        return response.toProduct();

    }

}
