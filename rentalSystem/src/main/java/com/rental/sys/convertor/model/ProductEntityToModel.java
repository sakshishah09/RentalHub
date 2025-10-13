package com.rental.sys.convertor.model;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.rental.sys.entities.Product;
import com.rental.sys.model.response.ProductResponse;

@Component
public class ProductEntityToModel {

    // Convert a list of Product entities to a list of ProductResponse DTOs
    public List<ProductResponse> getFindAllConvert(List<Product> productList) {
        return productList.stream()
                          .map(ProductResponse::new)  // use the constructor directly
                          .collect(Collectors.toList());
    }

    // Convert a single Product entity to ProductResponse DTO
    public ProductResponse getProductById(Product savedProduct) {
        return new ProductResponse(savedProduct);  // use the constructor directly
    }
}