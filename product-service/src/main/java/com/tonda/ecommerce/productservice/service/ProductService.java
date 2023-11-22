package com.tonda.ecommerce.productservice.service;

import java.util.List;

import com.tonda.ecommerce.productservice.dto.ProductRequest;
import com.tonda.ecommerce.productservice.dto.ProductResponse;
import com.tonda.ecommerce.productservice.model.Product;
import com.tonda.ecommerce.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductService {
    // Service layer needs to access DB - inject repository
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(ProductRequest productRequest) {
    Product product = Product.builder() // id is generated automatically by MongoDB due to @ID annotation
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);

        log.info("Product" + product.getId() + "is saved");
    }

    public List<ProductResponse> getAllProducts() {
        // accessing the repo directly returns normal Product objects
        List<Product> products = productRepository.findAll();
        // map to ProductResponse so it can be sent to Controller

        // similar to mapping in JS -> ie iterating over everything in products and performing a function.
        return products.stream().map(product -> mapToProductResponse(product)).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

}
