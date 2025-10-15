package com.rental.sys.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rental.sys.entities.Orders;

public interface OrderRepository extends JpaRepository<Orders,Integer> {
	
	@Query("SELECT o FROM Orders o WHERE o.buyer.id = :buyerId")
    List<Orders> findByBuyerId(@Param("buyerId") Integer buyerId, Pageable pageable);

    @Query("SELECT COUNT(o) FROM Orders o WHERE o.buyer.id = :buyerId")
    long countByBuyerId(@Param("buyerId") Integer buyerId);

    @Query("SELECT o FROM Orders o WHERE o.seller.id = :sellerId")
    List<Orders> findBySellerId(@Param("sellerId") Integer sellerId, Pageable pageable);

    @Query("SELECT COUNT(o) FROM Orders o WHERE o.seller.id = :sellerId")
    long countBySellerId(@Param("sellerId") Integer sellerId);
    
    
}


