package com.rental.sys.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "payments")
@NamedQuery(name = "Payment.findAll", query = "SELECT p FROM Payment p")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "payment_method")
	private String paymentMethod;

	@Column(name = "payment_status")
	private String paymentStatus;

	// bi-directional many-to-one association to Booking
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "booking_id")
	private Booking booking;

	public Payment() {
	}
}