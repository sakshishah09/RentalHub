package com.rental.sys.model.request;

import java.math.BigDecimal;
import java.util.List;

import com.rental.sys.entities.ProductImage;

import lombok.Data;

@Data
public class ProductSaveRequestModel {
	private String name;
	private String description;
	private String size;
	private String color;
	private BigDecimal pricePerDay;
	private BigDecimal priceForSale;
	private boolean available;
	private List<ProductImage> imageUrl;
	private int categoryId;
	private int subcategoryId;
	private int ownerId;
}
