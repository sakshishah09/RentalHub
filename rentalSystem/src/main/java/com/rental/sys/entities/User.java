package com.rental.sys.entities;

import jakarta.persistence.*;
import java.sql.Timestamp;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreationTimestamp
    @Column(name="created_at", updatable = false)
    private Timestamp createdAt;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name="image_url")
    private String imageUrl;

    private String phoneNumber;

    private String name;

    private String password;

    private String status;
    @Column(name = "is_seller", nullable = false)
    private Boolean isSeller = false; // 👈 default value set at Java level
    
    private String address;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @PrePersist
    public void setDefaultRole() {
        if (this.role == null) {
            Role defaultRole = new Role();
            defaultRole.setId(2); // assuming 1 = MEMBER in your Role table
            this.role = defaultRole;
        }
        if (this.isSeller == null) {
            this.isSeller = false; // safety: ensure default is false
        }
    }
}
