package com.rental.sys.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private String name;

	public Role() {
	}
}