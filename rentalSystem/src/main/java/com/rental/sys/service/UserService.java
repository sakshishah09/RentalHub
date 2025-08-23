package com.rental.sys.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rental.sys.convertor.entities.UserModelToEntity;
import com.rental.sys.convertor.model.UserEntityToModel;
import com.rental.sys.entities.User;
import com.rental.sys.model.request.UserSignupRequestModel;
import com.rental.sys.model.response.UserResponse;
import com.rental.sys.repo.UserRepo;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserModelToEntity userModelToEntity;

    @Autowired
    private UserEntityToModel userEntityToModel;

    private final String PROFILE_PIC_DIR = "storage/profilePics/";

    public UserResponse signup(UserSignupRequestModel userSignupRequestModel, MultipartFile image) throws Exception {

        // Duplicate email check
        User byEmail = userRepo.findByEmail(userSignupRequestModel.getEmail());
        if (byEmail != null) {
            throw new Exception("An account with this email already exists. Please use another email.");
        }

        // Duplicate phone check
        User byPhoneNumber = userRepo.findByPhoneNumber(userSignupRequestModel.getPhoneNumber());
        if (byPhoneNumber != null) {
            throw new Exception("An account with this phone number already exists. Please use another number.");
        }

        // Save image if provided
        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            File dir = new File(PROFILE_PIC_DIR);
            if (!dir.exists()) dir.mkdirs();

            String fileName = java.util.UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path filePath = Paths.get(PROFILE_PIC_DIR + fileName);
            Files.write(filePath, image.getBytes());
            imageUrl = filePath.toString();
        }

        // Convert model to entity
        User userEntity = userModelToEntity.getSaveConvert(userSignupRequestModel, imageUrl);

        // Save user
        User savedUser = userRepo.save(userEntity);

        // Convert entity to response
        return userEntityToModel.getfindbyId(savedUser);
    }
}