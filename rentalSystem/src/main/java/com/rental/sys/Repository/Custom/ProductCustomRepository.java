package com.rental.sys.Repository.Custom;

import java.math.BigDecimal;
import java.util.List;

import com.rental.sys.entities.Product;

public interface ProductCustomRepository {
	// List<Product> filterByCustom(String name, BigDecimal minPrice, BigDecimal
	// maxPrice);
	// long countCustom(String name);
//	public List<Product> filterBy(Integer page, Integer size, String type, String categoryName, String productName,
//			BigDecimal minPrice, BigDecimal maxPrice, String keyword);
//
//	public long countBy(String type, String categoryName, String productName, BigDecimal minPrice, BigDecimal maxPrice,
//			String keyword);

		List<Product> filterBy(Integer page, Integer size, String type, String categoryName, String productName,
			String description, String color, BigDecimal minPrice, BigDecimal maxPrice, BigDecimal minSalePrice,
			BigDecimal maxSalePrice, String keyword);

	long countBy(String type, String categoryName, String productName, String description, String color,
			BigDecimal minPrice, BigDecimal maxPrice, BigDecimal minSalePrice, BigDecimal maxSalePrice, String keyword);

}
