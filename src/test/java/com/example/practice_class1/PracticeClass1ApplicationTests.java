package com.example.practice_class1;

import com.example.practice_class1.Model.Category;
import com.example.practice_class1.Model.Product;
import com.example.practice_class1.repository.CategoryRepository;
import com.example.practice_class1.repository.ProductRepository;
import com.example.practice_class1.repository.projections.ProductProjection;
import com.example.practice_class1.repository.projections.ProductWithIdAndTitle;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class PracticeClass1ApplicationTests {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Test
    void contextLoads() {
    }

    @Test
    void testJpaDeclareJoin(){
        List<Product> products=productRepository.findAllByCategory_Title("new electronics");
        for(Product product:products) {
            System.out.println(product.getTitle());
        }
    }

    @Test
    void testJpaCheckDescription(){
        List<Product> products=productRepository.findAllByDescription("old Iphone");
        for(Product product:products)
        {
            System.out.print(product.getDescription()+" ");
            System.out.print(product.getId());
            System.out.println();
        }
    }
//----------------------------HQL QUERIES----------------------------------------------
    //return single field(HQL QUERY)
@Test
    void testHQL(){
        List<Product> products=productRepository.getProductWithCategoryName("new electronics");
        for(Product product :products)
        {
            System.out.println(product.getTitle());
        }

    }

    @Test
    void TestHQL1(){
        List<Product> products=productRepository.getProductWithCategoryNameAndPriceAndtitle("new electronics");
        for (Product product:products)
        {
            System.out.println(product.getTitle());
            System.out.println(product.getCategory().getTitle());
            System.out.println(product.getPrice());
        }
    }
    @Test
    void testSpecificFields(){
        List<String> productTitles=productRepository.someTitleMethod("new electronics");

        for(String ProductTitle : productTitles)
        {
            System.out.println(ProductTitle);
        }
    }

//    ------------------------------using Projection------------------------
    @Test
    void TestProjections(){
        List<ProductWithIdAndTitle> products=productRepository.someMethod1("new electronics");
        for(ProductWithIdAndTitle product:products)
        {
            System.out.println(product.getTitle());
            System.out.println(product.getId());
        }
}

    @Test
    void TestProjections1(){
        List<ProductProjection> productProjections=productRepository.someMethod2("new electronics");
        for(ProductProjection p:productProjections)
        {
            System.out.println(p.getTitle());
            System.out.println(p.getId());
            System.out.println(p.getPrice());
        }
    }

//    ------------------------------NATIVE QUERY--------------------------------------------
@Test
    void TestNativeSql(){
        Product product= productRepository.someNativeSql(1L);
        System.out.println(product.getTitle());//output =iphone 10
}

@Test
void TestNativeSqll(){
    ProductProjection  product =productRepository.someNativeSql1(1L);
    System.out.println(product.getTitle());
    }
    

    @Test
    void TestNativeSQL(){
        ProductProjection product2=productRepository.someNativeSql2(1L);
        System.out.println(product2.getTitle());
        System.out.println(product2.getId());
    }



//    ------------------------------------------------------------------------------
    @Test
    @Transactional

    void fetchTestMode()
    {
        Optional<Category> category=categoryRepository.findById(4L);
        if(category.isPresent())
        {
            System.out.println(category.get().getTitle());
            List<Product>products=category.get().getProducts();
            for(Product  product:products)
            {
                System.out.println(product.getTitle()+" "+product.getDescription()+" "+product.getPrice());
            }
        }
    }

//    --------------------------(N+1) problem---------------------
    @Test
    @Transactional  //as we are using lazy loading we need to set this as one transaction
    void testFetchMode()
    {
        List<Category> categories=categoryRepository.findByTitleEndingWith("electronics");
        for(Category category:categories)
        {
            System.out.println(category.getTitle());
            List<Product> products=category.getProducts();
            for(Product product :products)
            {
                System.out.println(product.getTitle());
            }
        }


    }
}
