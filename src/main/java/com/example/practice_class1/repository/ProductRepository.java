package com.example.practice_class1.repository;

import com.example.practice_class1.Model.Product;
import com.example.practice_class1.repository.projections.ProductProjection;
import com.example.practice_class1.repository.projections.ProductWithIdAndTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
//--------------------------DECLARED QUERIES------------------------------------------------------------
    //create add object
    Product save(Product product);

    //find all products in db
    List<Product> findAll();

    //delete product and get single product
    Product findByIdIs(Long id);

    List<Product> findAllByCategory_Title(String title);
    List<Product> findAllByDescription(String description);
//-----------------------------------------------HQL QUERIES(we can print multiple records )-------------------------------------
    // now we want single field HQL
    @Query("select p from Product p where p.category.title=:categoryName")
    List<Product> getProductWithCategoryName(String categoryName);

    @Query("select p from Product p where p.category.title=:categoryName")
    List<Product> getProductWithCategoryNameAndPriceAndtitle(String categoryName);

    @Query("select p.title as title from Product p where p.category.title=: categoryName")
    List<String> someTitleMethod(String categoryName);
//-------------------------print using Projection(it is way of printing ouput)--------------

    @Query("select p.id as id,p.title as title from Product p where p.category.title=:categoryName")
    List<ProductWithIdAndTitle> someMethod1(String categoryName);

    @Query("select p.id as id,p.title as title,p.price as price from Product p where p.category.title=:categoryName")
    List<ProductProjection> someMethod2(String categoryName);

//    --------------------------NATIVE QUERY--------------------------------------

    @Query(value="select * from Product p where p.id=:id",nativeQuery=true)
    Product someNativeSql(Long id);

    //above query using Projection
    @Query(value="select * from Product p where p.id=:id",nativeQuery = true)
    ProductProjection someNativeSql1(Long id);

    @Query(value="select * from Product p where p.id=:id",nativeQuery=true)
    ProductProjection someNativeSql2(Long id);
}

