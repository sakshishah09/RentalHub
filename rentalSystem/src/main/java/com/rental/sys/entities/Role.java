package com.rental.sys.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private String name;

	public Role() {
	}
<<<<<<< HEAD
=======

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
>>>>>>> 20370f6 (Local changes: removed deleted files and added new files)
}