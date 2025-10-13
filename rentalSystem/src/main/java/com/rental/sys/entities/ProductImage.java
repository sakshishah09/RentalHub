package com.rental.sys.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product_images")
public class ProductImage {
<<<<<<< HEAD
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
=======
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
>>>>>>> 20370f6 (Local changes: removed deleted files and added new files)

	@Column(name = "image_url", nullable = false)
	private String imageUrl;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	public ProductImage() {
	}

<<<<<<< HEAD
    // Getters and Setters
}
=======
	public ProductImage(String imageUrl, Product product) {
		this.imageUrl = imageUrl;
		this.product = product;
	}

	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
>>>>>>> 20370f6 (Local changes: removed deleted files and added new files)
