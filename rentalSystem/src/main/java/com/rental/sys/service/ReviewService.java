package com.rental.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rental.sys.entities.Product;
import com.rental.sys.entities.Review;
import com.rental.sys.entities.User;
import com.rental.sys.model.request.ReviewRequest;
import com.rental.sys.model.response.ReviewResponse;
import com.rental.sys.repo.ProductRepo;
import com.rental.sys.repo.ReviewRepo;
import com.rental.sys.repo.UserRepo;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepo reviewRepository;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ProductRepo productRepo;

	public ReviewResponse addReview(ReviewRequest request) {
		if (reviewRepository.existsByUserIdAndProductId(request.getUserId(), request.getProductId())) {
			throw new RuntimeException("User has already reviewed this product");
		}

		User user = userRepo.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
		Product product = productRepo.findById(request.getProductId())
				.orElseThrow(() -> new RuntimeException("Product not found"));

		Review review = new Review();
		review.setUser(user);
		review.setProduct(product);
		review.setRating(request.getRating());
		review.setComment(request.getComment());

		Review saved = reviewRepository.save(review);

		return mapToResponse(saved);
	}

	public List<ReviewResponse> getReviewsByProduct(Integer productId) {
		return reviewRepository.findByProductId(productId).stream().map(this::mapToResponse).toList();
	}

	public List<ReviewResponse> getReviewsByUser(Integer userId) {
		return reviewRepository.findByUserId(userId).stream().map(this::mapToResponse).toList();
	}

	private ReviewResponse mapToResponse(Review review) {
		ReviewResponse res = new ReviewResponse();
		res.setId(review.getId());
		res.setUserId(review.getUser().getId());
		res.setProductId(review.getProduct().getId());
		res.setRating(review.getRating());
		res.setComment(review.getComment());
		return res;
	}
}