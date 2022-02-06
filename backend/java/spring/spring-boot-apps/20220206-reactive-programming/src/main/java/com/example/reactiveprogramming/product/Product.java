package com.example.reactiveprogramming.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collation = "products")
public class Product {

    @Id
    private Integer id;
    private String name;
    private int qty;
    private double price;
}
