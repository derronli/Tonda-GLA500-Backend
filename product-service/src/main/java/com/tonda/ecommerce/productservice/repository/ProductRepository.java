package com.tonda.ecommerce.productservice.repository;

import com.tonda.ecommerce.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

// Extension of JpaRepository. Specify Entity + PK type
public interface ProductRepository extends MongoRepository<Product, String> {
    // automatically get the CRUD functions
}
