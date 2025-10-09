package com.rental.sys.model.request;

import lombok.Data;

@Data
public class SubCategoryUpdateRequestModel {

	private Integer id;
	private String name;
	private Integer categoryId;
	// getters & setters

}
