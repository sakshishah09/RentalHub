package com.rental.sys.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rental.sys.entities.Review;

public interface ReviewRepo extends JpaRepository<Review, Integer> {
	List<Review> findByProductId(Integer productId);
    List<Review> findByUserId(Integer userId);
    boolean existsByUserIdAndProductId(Integer userId, Integer productId);
}
