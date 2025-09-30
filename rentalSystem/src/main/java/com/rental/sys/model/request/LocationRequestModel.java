package com.rental.sys.model.request;

import lombok.Data;

@Data
public class LocationRequestModel {
	private String name;
	private String pincode;
	private int cityId;
}