package com.rental.sys.model.request;

import lombok.Data;

@Data
public class SubCategoryUpdateRequestModel {

	private int id;
	private String name;
	private int categoryId;
	// getters & setters

}
