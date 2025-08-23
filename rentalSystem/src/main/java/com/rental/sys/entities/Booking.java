package com.rental.sys.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "booking")
@NamedQuery(name="Booking.findAll", query="SELECT b FROM Booking b")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Temporal(TemporalType.DATE)
    @Column(name = "from_date")
    private Date fromDate;

    private String status;

    @Temporal(TemporalType.DATE)
    @Column(name = "to_date")
    private Date toDate;

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

    public Timestamp getCreatedAt() { return this.createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Date getFromDate() { return this.fromDate; }
    public void setFromDate(Date fromDate) { this.fromDate = fromDate; }

    public String getStatus() { return this.status; }
    public void setStatus(String status) { this.status = status; }

    public Date getToDate() { return this.toDate; }
    public void setToDate(Date toDate) { this.toDate = toDate; }

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
