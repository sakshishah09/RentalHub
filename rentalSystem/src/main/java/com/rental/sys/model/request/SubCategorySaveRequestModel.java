package com.rental.sys.model.request;

import lombok.Data;

@Data
public class SubCategorySaveRequestModel {
	// Save Request
	private String name;
	private int categoryId;

}
