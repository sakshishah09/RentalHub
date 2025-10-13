package com.rental.sys.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "subcategories")
@NamedQuery(name = "Subcategory.findAll", query = "SELECT s FROM Subcategory s")
public class Subcategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "image_path")
    private String imagePath;

    @OneToMany(mappedBy = "subcategory")
    private List<Product> products;
    

    @ManyToOne
    private Category category;
}