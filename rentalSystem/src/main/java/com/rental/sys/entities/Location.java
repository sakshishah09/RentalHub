package com.rental.sys.entities;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "location")
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String pincode;

	@ManyToOne
	@JoinColumn(name = "city_id", nullable = false)
	private City city;
	
}