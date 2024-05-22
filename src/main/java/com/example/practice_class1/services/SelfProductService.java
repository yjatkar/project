package com.example.practice_class1.services;

import com.example.practice_class1.Model.Category;
import com.example.practice_class1.Model.Product;
import com.example.practice_class1.exception.ProductNotFoundException;
import com.example.practice_class1.repository.CategoryRepository;
import com.example.practice_class1.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("selfProductService")
public class SelfProductService implements ProductService{

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public SelfProductService(CategoryRepository categoryRepository,ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository=productRepository;
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        Product product=productRepository.findByIdIs(productId);
        if(product==null)
        {
            throw new ProductNotFoundException(
                    "Product with id"+productId+"not found"
            );
        }
        return product;


    }

    @Override
    public List<Product> getAllProducts() {
//        return List.of();
        return productRepository.findAll();
    }


    @Override
    public Product addProduct(String title, String description, String image, String category, double price) {
        Product newProduct=new Product();
        newProduct.setTitle(title);
        newProduct.setDescription(description);
        newProduct.setImage(image);
        newProduct.setPrice(price);
        // for category if category dont exist create new category for this create category repository
//        newProduct.setCategory(category)
//        check if category is present in db
        Category categoryfromDb=categoryRepository.findByTitle(category);
        if(categoryfromDb==null)
        {
            Category newCategory=new Category();
            newCategory.setTitle(category);
//            categoryRepository.save(newCategory); will be done by cascadeType.Persist no need to save
            categoryfromDb=newCategory;
        }
        newProduct.setCategory(categoryfromDb);
        Product savedProduct=productRepository.save(newProduct);
        return savedProduct;

    }

    @Override
    public Product deleteProduct(Long productId) throws ProductNotFoundException {
        Product product=productRepository.findByIdIs(productId);
        if(product==null)
        {
            throw new ProductNotFoundException(
                    "product with id "+productId+" not found"
            );
        }
        productRepository.delete(product);
        return product;
    }

    @Override//patch
    public Product updateProduct(Long productId, String title, String Description, String image, String category, Double price)
            throws ProductNotFoundException {
        Product productInDb=productRepository.findByIdIs(productId);
        if(productInDb==null)
        {
            throw new ProductNotFoundException(
                    "product with id "+productId+" not found"
            );
        }
        if(title!=null)
        {
            productInDb.setTitle(title);
        }
        if(Description!=null)
        {
            productInDb.setDescription(Description);
        }
        if(image!=null)
        {
            productInDb.setImage(image);
        }
        if(price!=0)
        {
            productInDb.setPrice(price);
        }
        if(category!=null)
        {
            Category categoryfromDb=categoryRepository.findByTitle(category);
            if(categoryfromDb==null)
            {
                Category newCategory=new Category();
                newCategory.setTitle(category);
                categoryfromDb=newCategory;
            }
            productInDb.setCategory(categoryfromDb);
        }
        return productRepository.save(productInDb);


    }

    @Override//put
    public Product replaceProduct(Long productId, String title, String Description, String image, String category, Double price) throws ProductNotFoundException {
        return null;
//        Product productInDb=productRepository.findByIdIs(productId);
//        if(productInDb==null)
//        {
//            throw new ProductNotFoundException(
//                    "product with id "+productId+" not found"
//            );
//        }
//        productInDb.setTitle(title);
//        productInDb.setDescription(Description);
//        productInDb.setImage(image);
//        productInDb.setPrice(price);
//        if(category!=null)
//        {
//            Category categoryfromDb=categoryRepository.findByTitle(category);
//            if(categoryfromDb==null)
//            {
//                Category newCategory=new Category();
//                newCategory.setTitle(category);
//                categoryfromDb=newCategory;
//            }
//            productInDb.setCategory(categoryfromDb);
//
//        }
//
//        return productRepository.save(productInDb);

    }
}
