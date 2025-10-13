package com.rental.sys.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rental.sys.entities.Product;
import com.rental.sys.entities.User;
import com.rental.sys.entities.Wishlist;
import com.rental.sys.model.request.WishlistRequest;
import com.rental.sys.model.response.WishlistResponse;
import com.rental.sys.repo.ProductRepo;
import com.rental.sys.repo.UserRepo;
import com.rental.sys.repo.WishlistRepo;
@Service
public class WishlistService {
	
	@Autowired
    private WishlistRepo wishlistRepo;
    
	@Autowired
    private ProductRepo productRepo;
    
	@Autowired
    private UserRepo userRepo ;

    public WishlistResponse addToWishlist(WishlistRequest request) {
        if (wishlistRepo.existsByUserIdAndProductId(request.getUserId(), request.getProductId())) {
            throw new RuntimeException("Product already in wishlist.");
        }
      Optional<Product> productOpt = productRepo.findById(request.getProductId());
      if(productOpt.isEmpty()){
    	  throw new RuntimeException("Product not found");
      }
      Optional<User> userOpt = userRepo.findById(request.getUserId());
      if(userOpt.isEmpty()){
    	  throw new RuntimeException("User not found");
      }
      Wishlist wishlist = new Wishlist();
      wishlist.setUser(userOpt.get());
      wishlist.setProduct(productOpt.get());
      Wishlist saved = wishlistRepo.save(wishlist);

        WishlistResponse response = new WishlistResponse();
        response.setId(saved.getId());
        response.setUserId(saved.getUser().getId());
        response.setProductId(saved.getProduct().getId());
        response.setProductName(productOpt.get().getName());
        response.setProductDescription(productOpt.get().getDescription());
//      response.setProductAvailable(productOpt.get().get);
//      response.setMessage("Product added to wishlist successfully!");
        return response;
    }

    public List<WishlistResponse> getUserWishlist(Integer userId) {
        List<Wishlist> wishlistItems = wishlistRepo.findByUserId(userId);

        return wishlistItems.stream().map(item -> {
            Product product = productRepo.findById(item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            WishlistResponse response = new WishlistResponse();
            response.setId(item.getId());
            response.setUserId(item.getUser().getId());
            response.setProductId(item.getProduct().getId());
            response.setProductName(product.getName());
//          response.setProductAvailable(product.getA);
            response.setProductDescription(product.getDescription());
            return response;
        }).collect(Collectors.toList());
    }

    public void removeFromWishlist(Integer userId, Integer productId) {
        if (!wishlistRepo.existsByUserIdAndProductId(userId, productId)) {
            throw new RuntimeException("Product not found in wishlist.");
        }
        wishlistRepo.deleteByUserIdAndProductId(userId, productId);
    }
}