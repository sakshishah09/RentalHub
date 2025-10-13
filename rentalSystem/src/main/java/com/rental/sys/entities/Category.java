package com.rental.sys.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "categories", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c")
public class Category {

<<<<<<< HEAD
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
=======
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
>>>>>>> 20370f6 (Local changes: removed deleted files and added new files)

	@Column(nullable = false, length = 100)
	private String name;

	private String imagePath;

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	// (One-to-Many)
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Product> products;

<<<<<<< HEAD
    //(One-to-Many)
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Subcategory> subcategories;
    
}
=======
	// (One-to-Many)
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Subcategory> subcategories;

	public Category() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Subcategory> getSubcategories() {
		return this.subcategories;
	}

	public void setSubcategories(List<Subcategory> subcategories) {
		this.subcategories = subcategories;
	}

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
>>>>>>> 20370f6 (Local changes: removed deleted files and added new files)
