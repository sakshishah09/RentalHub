package com.rental.sys.model.response;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
@Data
public class ProductResponse {
	private int id ;
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
}
