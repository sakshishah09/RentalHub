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
    private int id;

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

    private String address;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
