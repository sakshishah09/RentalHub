package com.rental.sys.model.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.rental.sys.entities.Product;

import lombok.Data;

@Data
public class ProductResponse {
	private int id;
	private String name;
	private String description;
	private String size;
	private String color;
	private BigDecimal pricePerDay;
	private BigDecimal priceForSale;
	private boolean available;
	private int categoryId;
	private int subcategoryId;
	private int userId;
	private List<ProductImageResponse> images;

	public ProductResponse(Product prod) {
		this.id = prod.getId();
		this.name = prod.getName();
		this.description = prod.getDescription();
		this.size = prod.getSize();
		this.color = prod.getColor();
		this.pricePerDay = prod.getPricePerDay();
		this.priceForSale = prod.getPriceForSale();
		this.available = prod.isAvailable();
		this.categoryId = prod.getCategory().getId();
		this.subcategoryId = prod.getSubcategory().getId();
		this.userId = prod.getUser().getId();
		this.images = prod.getImages().stream().map(img -> {
			ProductImageResponse pir = new ProductImageResponse();
			pir.setId(img.getId());
			pir.setImageUrl(img.getImageUrl());
			return pir;
		}).collect(Collectors.toList());
	}
}