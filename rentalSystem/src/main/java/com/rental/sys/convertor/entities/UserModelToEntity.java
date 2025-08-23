package com.rental.sys.convertor.entities;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rental.sys.entities.Location;
import com.rental.sys.entities.Role;
import com.rental.sys.entities.User;
import com.rental.sys.model.request.UserSignupRequestModel;
import com.rental.sys.repo.LocationRepo;
import com.rental.sys.repo.RoleRepo;

@Component
public class UserModelToEntity {

	@Autowired
	private LocationRepo locationRepo;

	@Autowired
	private RoleRepo roleRepo;

	public User getSaveConvert(UserSignupRequestModel signupRequestModel, String imageUrl) throws Exception {
		Optional<Location> optionalLocation = locationRepo.findById(signupRequestModel.getLocationId());
		if (optionalLocation.isEmpty()) {
			throw new Exception("The location does not exist.");
		}

		User user = new User();
		// Set all fields from signupRequestModel
		user.setName(signupRequestModel.getName());
		user.setEmail(signupRequestModel.getEmail());
		user.setPhoneNumber(signupRequestModel.getPhoneNumber());
		user.setPassword(signupRequestModel.getPassword());
		user.setStatus(signupRequestModel.getStatus());
		user.setAddress(signupRequestModel.getAddress());
		user.setLocation(optionalLocation.get());

		// Set role if provided
		if (signupRequestModel.getRoleId() > 0) {
			Role role = roleRepo.findById(signupRequestModel.getRoleId())
					.orElseThrow(() -> new Exception("Role does not exist."));
			user.setRole(role);
		}

		// Set image if provided
		if (imageUrl != null) {
			user.setImageUrl(imageUrl);
		}

		return user;
	}
}