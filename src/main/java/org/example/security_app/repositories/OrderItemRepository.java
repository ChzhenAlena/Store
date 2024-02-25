package org.example.security_app.repositories;

import org.example.security_app.models.Item;
import org.example.security_app.models.Order;
import org.example.security_app.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    public Optional<OrderItem> findOrderItemByOrderAndItem(Order order, Item item);
}
