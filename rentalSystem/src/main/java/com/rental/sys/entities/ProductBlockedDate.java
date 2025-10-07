package com.rental.sys.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name="product_blocked_date",
       uniqueConstraints = @UniqueConstraint(columnNames={"product_id","blocked_date"}))
public class ProductBlockedDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    private Product product;

    private LocalDate blockedDate;

    public ProductBlockedDate() {}
    public ProductBlockedDate(Product product, LocalDate blockedDate) {
        this.product = product; this.blockedDate = blockedDate;
    }
}

