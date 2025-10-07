package com.rental.sys.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="orders")
public class Order  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne @JoinColumn(name="product_id", nullable=false)
    private Product product;

    @ManyToOne @JoinColumn(name="user_id", nullable=false)
    private User user;

    private BigDecimal amount;
    private String status; // CREATED, PAID, CANCELLED
    private LocalDateTime createdAt;

    // Getters & setters
}
