package com.rental.sys.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rental.sys.entities.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

	@Query("SELECT p FROM Product p")
	List<Product> findAllProduct(Pageable pageable);

	// Fetch products by sub-category ID
	List<Product> findBySubcategoryId(Integer subcategoryId);
	
	@Query("SELECT p FROM Product p WHERE p.productType = 'SALE' OR p.productType = 'BOTH'")
    List<Product> findSellableProducts();

    @Query("SELECT p FROM Product p WHERE p.productType = 'RENT' OR p.productType = 'BOTH'")
    List<Product> findRentableProducts();
}