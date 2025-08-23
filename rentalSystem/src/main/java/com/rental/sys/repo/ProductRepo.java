package com.rental.sys.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rental.sys.entities.Product;

public interface ProductRepo extends JpaRepository<Product, Integer>{

}
