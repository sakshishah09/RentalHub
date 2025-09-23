package com.rental.sys.model.response;

import lombok.Data;

@Data
public class ReviewResponse {
	 private int id;
	 private int userId;
	 private int productId;
	 private int rating;
	 private String comment;
}
