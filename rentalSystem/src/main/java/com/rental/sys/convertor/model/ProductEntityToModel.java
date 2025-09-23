package com.rental.sys.convertor.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.rental.sys.entities.Product;
import com.rental.sys.model.response.ProductImageResponse;
import com.rental.sys.model.response.ProductResponse;

@Component
public class ProductEntityToModel {
	public List<ProductResponse> getFindAllConvert(List<Product> productList) {
	    List<ProductResponse> productResponses = new ArrayList<>();

	    for (Product product : productList) {
	        ProductResponse response = new ProductResponse();

	        response.setId(product.getId());
	        response.setName(product.getName());
	        response.setColor(product.getColor());
	        response.setSize(product.getSize());
	        response.setDescription(product.getDescription());
	        response.setPriceForSale(product.getPriceForSale());
	        response.setPricePerDay(product.getPricePerDay());
	        response.setAvailable(product.isAvailable());
	        response.setCategoryId(product.getCategory().getId());
	        response.setSubcategoryId(product.getSubcategory().getId());
	        response.setUserId(product.getUser().getId());
	        // Convert images
	        List<ProductImageResponse> images = product.getImages().stream()
	                .map(img -> {
	                    ProductImageResponse imgResponse = new ProductImageResponse();
	                    imgResponse.setId(img.getId());
	                    imgResponse.setImageUrl(img.getImageUrl());
	                    return imgResponse;
	                })
	                .collect(Collectors.toList());
	        response.setImages(images);
	        productResponses.add(response);
	    }
	    return productResponses;
	}
	public ProductResponse getProductById(Product savedProduct) {
		ProductResponse productResponse = new ProductResponse();
		productResponse.setId(savedProduct.getId());
		productResponse.setName(savedProduct.getName());
		productResponse.setColor(savedProduct.getColor());
		productResponse.setSize(savedProduct.getSize());
		productResponse.setDescription(savedProduct.getDescription());
		productResponse.setPriceForSale(savedProduct.getPriceForSale());
		productResponse.setPricePerDay(savedProduct.getPricePerDay());
		productResponse.setAvailable(savedProduct.isAvailable());
		productResponse.setCategoryId(savedProduct.getCategory().getId());
		productResponse.setSubcategoryId(savedProduct.getSubcategory().getId());
		productResponse.setUserId(savedProduct.getUser().getId());
		List<ProductImageResponse> images = savedProduct.getImages().stream()
		        .map(img -> {
		            ProductImageResponse imgResponse = new ProductImageResponse();
		            imgResponse.setId(img.getId());
		            imgResponse.setImageUrl(img.getImageUrl());
		            return imgResponse;
		        })
		        .collect(Collectors.toList());
		    productResponse.setImages(images);
		    return productResponse;
	}
}
