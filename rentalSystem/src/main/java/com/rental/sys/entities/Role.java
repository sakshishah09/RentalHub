package com.rental.sys.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

@Data
@Entity
@NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;

    private String name;
}