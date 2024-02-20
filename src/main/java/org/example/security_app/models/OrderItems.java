package org.example.security_app.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class OrderItems {
    @Id
    private int id;
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;
    private int amount;

}
