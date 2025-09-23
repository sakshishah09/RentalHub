package com.rental.sys.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rental.sys.entities.ProductImage;

public interface ProductImageRepo extends JpaRepository<ProductImage, Integer> {

}
