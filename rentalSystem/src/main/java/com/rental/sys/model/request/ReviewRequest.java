package com.rental.sys.model.request;

import lombok.Data;

@Data
public class ReviewRequest {
	private Integer userId;
	private Integer productId;
	private Integer rating;
	private String comment;
}
