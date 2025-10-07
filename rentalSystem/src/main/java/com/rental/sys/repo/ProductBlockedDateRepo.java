package com.rental.sys.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rental.sys.entities.ProductBlockedDate;

public interface ProductBlockedDateRepo extends JpaRepository<ProductBlockedDate,Integer> {
    List<ProductBlockedDate> findByProductIdAndBlockedDateBetween(Integer productId, LocalDate start, LocalDate end);
}