package com.rental.sys.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "reviews", uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "product_id" }))
@NamedQuery(name = "Review.findAll", query = "SELECT r FROM Review r")
public class Review {
	@Id
<<<<<<< HEAD
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
=======
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
>>>>>>> 20370f6 (Local changes: removed deleted files and added new files)

	private String comment;

	@Column(name = "created_at")
	private Timestamp createdAt;

<<<<<<< HEAD
	private Integer rating;
	
=======
	private int rating;

>>>>>>> 20370f6 (Local changes: removed deleted files and added new files)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
<<<<<<< HEAD
=======

	public Review() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Timestamp getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public int getRating() {
		return this.rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
>>>>>>> 20370f6 (Local changes: removed deleted files and added new files)
}