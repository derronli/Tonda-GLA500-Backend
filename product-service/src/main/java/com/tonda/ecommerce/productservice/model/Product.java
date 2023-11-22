package com.tonda.ecommerce.productservice.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "product") // required to connect with MongoDB
@AllArgsConstructor
@NoArgsConstructor
@Builder // allows us to do the step by step building of object
@Data // gives all the boiler plate -> toString, getterandsetter, etc.
public class Product {
    @Id // pk field
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
}
