package com.rental.sys.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "booking")
@NamedQuery(name="Booking.findAll", query="SELECT b FROM Booking b")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "created_at")
    private LocalDateTime  createdAt;

    @Temporal(TemporalType.DATE)
    @Column(name = "from_date")
    private LocalDate  fromDate;

    private String status;

    @Temporal(TemporalType.DATE)
    @Column(name = "to_date")
    private LocalDate  toDate;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RentalReturn> rentalReturns;

    public Booking() {}

    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }

   
    public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public BigDecimal getTotalAmount() { return this.totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public User getUser() { return this.user; }
    public void setUser(User user) { this.user = user; }

    public Product getProduct() { return this.product; }
    public void setProduct(Product product) { this.product = product; }

    public List<Payment> getPayments() { return this.payments; }
    public void setPayments(List<Payment> payments) { this.payments = payments; }

    public List<RentalReturn> getRentalReturns() { return this.rentalReturns; }
    public void setRentalReturns(List<RentalReturn> rentalReturns) { this.rentalReturns = rentalReturns; }

    public void addPayment(Payment payment) {
        payments.add(payment);
        payment.setBooking(this);
    }

    public void removePayment(Payment payment) {
        payments.remove(payment);
        payment.setBooking(null);
    }

    public void addRentalReturn(RentalReturn rentalReturn) {
        rentalReturns.add(rentalReturn);
        rentalReturn.setBooking(this);
    }

    public void removeRentalReturn(RentalReturn rentalReturn) {
        rentalReturns.remove(rentalReturn);
        rentalReturn.setBooking(null);
    }
}
