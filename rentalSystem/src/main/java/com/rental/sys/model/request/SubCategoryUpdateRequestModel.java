package com.rental.sys.model.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SubCategoryUpdateRequestModel {
	private Integer id; 
	private String name; 
	private Integer categoryId; 
	private MultipartFile image; 
}