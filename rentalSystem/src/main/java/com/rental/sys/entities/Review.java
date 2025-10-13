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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String comment;

    @Column(name = "created_at")
    private Timestamp createdAt;

    private Integer rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}