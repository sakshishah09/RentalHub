package com.rental.sys.model.response;

import java.util.List;

import lombok.Data;

@Data
public class CategoryDetailsResponse {
	private Integer id;
	private String name;
	private String imagePath;
	private List<SubCategoryResponse> subcategories;
}