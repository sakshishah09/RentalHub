package com.rental.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rental.sys.model.request.ReviewRequest;
import com.rental.sys.model.response.ReviewResponse;
import com.rental.sys.response.RestResponse;
import com.rental.sys.service.ReviewService;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    
	@Autowired
    private ReviewService reviewService;

    @PostMapping("/add")
    public RestResponse addReview(@RequestBody ReviewRequest request) {
        try {
            ReviewResponse response = reviewService.addReview(request);
            return RestResponse.build().withSuccess("Review added successfully", response);
        } catch (Exception e) {
            return RestResponse.build().withError(e.getMessage());
        }
    }
    @GetMapping("/product/{productId}")
    public RestResponse getReviewsByProduct(@PathVariable Integer productId) {
        try {
            List<ReviewResponse> responses = reviewService.getReviewsByProduct(productId);
            return RestResponse.build().withSuccess("Reviews fetched successfully", responses);
        } catch (Exception e) {
            return RestResponse.build().withError(e.getMessage());
        }
    }
    @GetMapping("/user/{userId}")
    public RestResponse getReviewsByUser(@PathVariable Integer userId) {
        try {
            List<ReviewResponse> responses = reviewService.getReviewsByUser(userId);
            return RestResponse.build().withSuccess("User reviews fetched successfully", responses);
        } catch (Exception e) {
            return RestResponse.build().withError(e.getMessage());
        }
    }
}
