package com.rental.sys.model.response;

import lombok.Data;

@Data
public class UserResponse {
	 private int id;
	 private String name;
	 private String email;
//	 private String role;
//	 private LocalDateTime createdAt;
//	 private LocalDateTime updatedAt;
	 private String imageUrl;
	 private String phoneNumber;
//	 private String status;
	 private String address;
}
