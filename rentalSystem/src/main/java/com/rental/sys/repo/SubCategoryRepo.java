package com.rental.sys.repo;

import java.util.List;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rental.sys.entities.Subcategory;

public interface SubCategoryRepo extends JpaRepository<Subcategory, Integer>{
	
	List<Subcategory> findByCategoryId(Integer categoryId);
	
	@Query("SELECT s FROM Subcategory s")
	List<Subcategory> findAllSubCategories(Pageable pageable);
}