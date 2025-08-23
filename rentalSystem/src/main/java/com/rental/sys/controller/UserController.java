package com.rental.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rental.sys.model.request.UserSignupRequestModel;
import com.rental.sys.model.response.UserResponse;
import com.rental.sys.response.RestResponse;
import com.rental.sys.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Register a new user", description = "Creates a new user account with optional profile image upload.")
    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RestResponse signup(@RequestParam("user") String userJson, @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            System.out.println("Received userJson: " + userJson);

            ObjectMapper mapper = new ObjectMapper();
            UserSignupRequestModel signupRequestModel = mapper.readValue(userJson, UserSignupRequestModel.class);

            // Call service to save user
            UserResponse responseModel = userService.signup(signupRequestModel, image);
            return RestResponse.build().withSuccess("User created successfully", responseModel);

        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.build().withError(e.getMessage());
        }
    }
}