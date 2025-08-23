package com.rental.sys.entities;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "admin")
@NamedQuery(name = "Admin.findAll", query = "SELECT a FROM Admin a")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String address;

    @Column(name = "created_at", updatable = false, insertable = false)
    private Timestamp createdAt;
  
    private String email;
   
    @Column(name = "full_name")
    private String fullName;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "mobile_number")
    private String mobileNumber;

    private String password;

//    @ManyToOne
//    @JoinColumn(name = "role_id", nullable = false)
//    private Role role;

    public Admin() {}

    public int getId() { 
    	return this.id; 
    }
    public void setId(int id) { 
    	this.id = id; 
    }

    public String getAddress() { 
        return this.address; 
    }
    public void setAddress(String address) { 
    	this.address = address; 
    }

    public Timestamp getCreatedAt() { 
    	return this.createdAt; 
    }
    public void setCreatedAt(Timestamp createdAt) { 
    	this.createdAt = createdAt; 
    }

    public String getEmail() { 
    	return this.email; 
    }
    public void setEmail(String email) { 
    	this.email = email; 
    }

    public String getFullName() { 
    	return this.fullName; 
    }
    public void setFullName(String fullName) { 
    	this.fullName = fullName; 
    }

    public String getImageUrl() { 
    	return this.imageUrl; 
    }
    public void setImageUrl(String imageUrl) { 
    	this.imageUrl = imageUrl; 
    }

    public String getMobileNumber() { 
    	return this.mobileNumber;
    }
    public void setMobileNumber(String mobileNumber) { 
    	this.mobileNumber = mobileNumber; 
    }

    public String getPassword() { 
    	return this.password; 
    }
    public void setPassword(String password) { 
    	this.password = password; 
    }

  
}
