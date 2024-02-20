package org.example.security_app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class ItemCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Min(value = 2, message = "Category name should be at least 2 characters long")
    private String name;
    @OneToMany(mappedBy = "category")
    private List<Item> items;

}
