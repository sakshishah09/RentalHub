package com.rental.sys.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rental.sys.entities.Wishlist;

import jakarta.transaction.Transactional;
@Repository
public interface WishlistRepo extends JpaRepository<Wishlist, Integer> {
   
	List<Wishlist> findByUserId(Integer userId);
    boolean existsByUserIdAndProductId(Integer userId, Integer productId);
    @Modifying
    @Transactional
    @Query("DELETE FROM Wishlist w WHERE w.user.id = :userId AND w.product.id = :productId")
    void deleteByUserIdAndProductId(@Param("userId") Integer userId,
                                    @Param("productId") Integer productId);
}
