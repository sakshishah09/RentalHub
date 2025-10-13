package com.rental.sys.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "categories", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, length = 100)
	private String name;

	private String imagePath;

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Product> products = new ArrayList<>();

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Subcategory> subcategories = new ArrayList<>();

	public Category() {
	}

	// Convenience methods
	public Product addProduct(Product product) {
		products.add(product);
		product.setCategory(this);
		return product;
	}

	public Product removeProduct(Product product) {
		products.remove(product);
		product.setCategory(null);
		return product;
	}

	public Subcategory addSubcategory(Subcategory subcategory) {
		subcategories.add(subcategory);
		subcategory.setCategory(this);
		return subcategory;
	}

	public Subcategory removeSubcategory(Subcategory subcategory) {
		subcategories.remove(subcategory);
		subcategory.setCategory(null);
		return subcategory;
	}
}