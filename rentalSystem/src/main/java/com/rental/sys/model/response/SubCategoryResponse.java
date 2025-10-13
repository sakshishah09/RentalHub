package com.rental.sys.model.response;

import java.util.List;
import lombok.Data;

@Data
public class SubCategoryResponse {
	private int id;
	private String name;
	private int categoryId;
	private String categoryName;
	private List<ProductResponse> products;
	private String imagePath;
}