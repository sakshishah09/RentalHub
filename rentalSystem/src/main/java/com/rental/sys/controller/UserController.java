package com.rental.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rental.sys.model.request.UserLoginRequestModel;
import com.rental.sys.model.request.UserSignupRequestModel;
import com.rental.sys.model.response.UserResponse;
import com.rental.sys.response.RestResponse;
import com.rental.sys.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
	private ObjectMapper objectMapper;

    @Operation(summary = "Register a new user", description = "Creates a new user account with optional profile image upload.")
    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RestResponse signup(@RequestParam("user") String userJson, @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            System.out.println("Received userJson: " + userJson);
            UserSignupRequestModel signupRequestModel = objectMapper.readValue(userJson, UserSignupRequestModel.class);
            UserResponse responseModel = userService.signup(signupRequestModel, image);
            return RestResponse.build().withSuccess("User created successfully", responseModel);
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.build().withError(e.getMessage());
        }
    }
    @RequestMapping(method = RequestMethod.POST, value = "/login", consumes = "application/json", produces = "application/json")
	public RestResponse login(@RequestBody UserLoginRequestModel userLoginRequestModel) throws Exception {
		try {
			System.out.println("Login with username: {}"+userLoginRequestModel.getUsername());
			UserResponse userResponseModel = userService.login(userLoginRequestModel);
			return RestResponse.build().withSuccess("User login successfully", userResponseModel);
		} catch (Exception e) {
			System.out.println("Failed to login user due to: {}"+e.getMessage());
			return RestResponse.build().withError(e.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/findById", produces = "application/json")
	public RestResponse findById(@RequestParam("id") Integer id) {
		try {
			UserResponse userResponseModel = userService.findById(id);
			return RestResponse.build().withSuccess("User found successfully", userResponseModel);
		} catch (Exception e) {
			return RestResponse.build().withError(e.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/findAll", produces = "application/json")
	public RestResponse findAll(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
//		LOGGER.info("Fetching all user");
		try {
			List<UserResponse> userResponse = userService.findAllUser(page, size);
			long totalRecord = userService.countAllUser();
			return RestResponse.build().withSuccess("User list found successfully").withTotalRecords(totalRecord)
					.withPageNumber(page).withPageSize(size).withData(userResponse);
		} catch (Exception e) {
//			LOGGER.error("Failed to find user list due to: {}", e.getMessage(), e);
			return RestResponse.build().withError(e.getMessage());
		}
	}
}