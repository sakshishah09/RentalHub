package com.rental.sys.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rental.sys.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
	boolean existsByName(String name);

	@Query("SELECT c FROM Category c")
	List<Category> findAllCategories(Pageable pageable);
}
