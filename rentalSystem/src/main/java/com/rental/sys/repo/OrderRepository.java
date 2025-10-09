package com.rental.sys.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rental.sys.entities.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
	
	@Query("SELECT o FROM Order o WHERE o.buyer.id = :buyerId")
    List<Order> findByBuyerId(@Param("buyerId") Integer buyerId, Pageable pageable);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.buyer.id = :buyerId")
    long countByBuyerId(@Param("buyerId") Integer buyerId);

    @Query("SELECT o FROM Order o WHERE o.seller.id = :sellerId")
    List<Order> findBySellerId(@Param("sellerId") Integer sellerId, Pageable pageable);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.seller.id = :sellerId")
    long countBySellerId(@Param("sellerId") Integer sellerId);
}


