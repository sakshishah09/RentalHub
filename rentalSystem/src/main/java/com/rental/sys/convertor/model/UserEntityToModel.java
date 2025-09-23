package com.rental.sys.convertor.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.rental.sys.entities.User;
import com.rental.sys.model.response.UserResponse;
@Component
public class UserEntityToModel {
	private String extractFileNameOnly(String fullPath) {
	    return new File(fullPath).getName();
	}
	public List<UserResponse> getFindAllConvert(List<User> userlist) {
	    List<UserResponse> responses = new ArrayList<>();
	    for (User user : userlist) {
	        UserResponse responseModel = new UserResponse();
	        responseModel.setId(user.getId());
	        responseModel.setName(user.getName());
	        responseModel.setEmail(user.getEmail());
	        responseModel.setPhoneNumber(user.getPhoneNumber());
	        responseModel.setAddress(user.getAddress());
	        if (user.getImageUrl() != null) {
	            responseModel.setImageUrl("/users/" + extractFileNameOnly(user.getImageUrl()));
	        } else {
	            responseModel.setImageUrl(null);
	        }
	        responses.add(responseModel);
	    }
	    return responses;
	}
	public UserResponse getfindbyId(User user) {
		UserResponse responseModel = new UserResponse();
		responseModel.setId(user.getId());
		responseModel.setName(user.getName());
		responseModel.setEmail(user.getEmail());
		responseModel.setPhoneNumber(user.getPhoneNumber());
		responseModel.setAddress(user.getAddress());
		if(user.getImageUrl()!=null) {
		responseModel.setImageUrl("/users/" + extractFileNameOnly(user.getImageUrl()));
		}else {
			responseModel.setImageUrl(null);
		}
		user.setCreatedAt(user.getCreatedAt());
		return responseModel;
	}
}
