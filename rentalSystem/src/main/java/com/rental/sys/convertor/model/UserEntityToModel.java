package com.rental.sys.convertor.model;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rental.sys.entities.User;
import com.rental.sys.model.response.UserResponse;
import com.rental.sys.repo.UserRepo;
@Component
public class UserEntityToModel {
	
	@Autowired
	private UserRepo userRepo;
	
	private String extractFileNameOnly(String fullPath) {
	    return new File(fullPath).getName();
	}
	public UserResponse getfindbyId(User user) {
		UserResponse responseModel = new UserResponse();
		responseModel.setId(user.getId());
		responseModel.setName(user.getName());
		responseModel.setEmail(user.getEmail());
		responseModel.setPhoneNumber(user.getPhoneNumber());
		responseModel.setAddress(user.getAddress());
		responseModel.setImageUrl("/users/" + extractFileNameOnly(user.getImageUrl()));
		user.setCreatedAt(new Timestamp(new Date().getTime()));
		return responseModel;
	}
}
