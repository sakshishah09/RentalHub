package com.rental.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rental.sys.model.request.WishlistRequest;
import com.rental.sys.model.response.WishlistResponse;
import com.rental.sys.response.RestResponse;
import com.rental.sys.service.WishlistService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;
    
    @Operation(summary = "Add product to wishlist", description = "Adds a product to the user's wishlist.")
    @PostMapping(value = "/add",  consumes = "application/json", produces = "application/json")
    public RestResponse addToWishlist(@RequestBody WishlistRequest wishlistRequest) {
        try {
            WishlistResponse response = wishlistService.addToWishlist(wishlistRequest);
            return RestResponse.build().withSuccess("Product added to wishlist successfully", response);
        } catch (Exception e) {
            return RestResponse.build().withError(e.getMessage());
        }
    }

    @Operation(summary = "Get all wishlist items for a user", description = "Fetch all products in user's wishlist.")
    @GetMapping(value = "/user/{userId}", produces = "application/json")
    public RestResponse getUserWishlist(@PathVariable("userId") Integer userId) {
        try {
            List<WishlistResponse> responses = wishlistService.getUserWishlist(userId);
            return RestResponse.build().withSuccess("Wishlist fetched successfully", responses);
        } catch (Exception e) {
            return RestResponse.build().withError(e.getMessage());
        }
    }

    @Operation(summary = "Remove product from wishlist", description = "Removes a product from the user's wishlist.")
    @DeleteMapping(value = "/remove/{userId}/{productId}")
    public RestResponse removeFromWishlist(@PathVariable("userId") Integer userId,
                                           @PathVariable("productId") Integer productId) {
        try {
            wishlistService.removeFromWishlist(userId, productId);
            return RestResponse.build().withSuccess("Product removed from wishlist successfully");
        } catch (Exception e) {
            return RestResponse.build().withError(e.getMessage());
        }
    }
}
