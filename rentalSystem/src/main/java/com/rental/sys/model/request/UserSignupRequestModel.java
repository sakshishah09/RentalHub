package com.rental.sys.model.request;

import lombok.Data;

@Data
public class UserSignupRequestModel {
	private String name;
	private String email;
	private String phoneNumber;
	private String password;
	private String status;
	private String address;
	private int locationId;
	private int roleId;
	
}
