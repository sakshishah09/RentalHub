package com.rental.sys.model.request;

import java.math.BigDecimal;
import com.rental.sys.entities.ProductType;
import lombok.Data;

@Data
public class ProductSaveRequestModel {
	private String name;
	private String description;
	private String size;
	private String color;
	private BigDecimal pricePerDay;
	private BigDecimal priceForSale;
	private Integer categoryId;
	private Integer subcategoryId;
	private Integer userId;
	private ProductType productType; 
}
