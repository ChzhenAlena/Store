package org.example.security_app.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private ItemCategory category;
    //@Min(value = 2, message = "Item name should be at least 2 characters long")
    private String name;
    private int amount;

}
