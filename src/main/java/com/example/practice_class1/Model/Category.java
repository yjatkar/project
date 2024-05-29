package com.example.practice_class1.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Getter
@Setter
@Entity

public class Category extends BaseModel {
    private String title;
    private String description;
//    @OneToMany(mappedBy = "category", fetch= FetchType.LAZY)
@OneToMany(mappedBy = "category")//lazy is by default
        @Fetch(value= FetchMode.SUBSELECT)
    List<Product> products;
}



