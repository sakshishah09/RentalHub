package com.rental.sys.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rental.sys.entities.Subcategory;

public interface SubCategoryRepo extends JpaRepository<Subcategory, Integer>{
	List<Subcategory> findByCategoryId(Integer categoryId);
}