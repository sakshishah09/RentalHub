package com.rental.sys.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name="rental_returns")
@NamedQuery(name="RentalReturn.findAll", query="SELECT r FROM RentalReturn r")
public class RentalReturn  {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name="rent_date")
	private Date rentDate;

	@Column(name="rent_status")
	private String rentStatus;

	@Temporal(TemporalType.DATE)
	@Column(name="return_due_date")
	private Date returnDueDate;

	//bi-directional many-to-one association to Booking
	@ManyToOne
	private Booking booking;

	//bi-directional many-to-one association to Product
	@ManyToOne
	private Product product;

	public RentalReturn() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getRentDate() {
		return this.rentDate;
	}

	public void setRentDate(Date rentDate) {
		this.rentDate = rentDate;
	}

	public String getRentStatus() {
		return this.rentStatus;
	}

	public void setRentStatus(String rentStatus) {
		this.rentStatus = rentStatus;
	}

	public Date getReturnDueDate() {
		return this.returnDueDate;
	}

	public void setReturnDueDate(Date returnDueDate) {
		this.returnDueDate = returnDueDate;
	}

	public Booking getBooking() {
		return this.booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}