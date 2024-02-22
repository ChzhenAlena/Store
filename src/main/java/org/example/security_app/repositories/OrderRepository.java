package org.example.security_app.repositories;

import org.example.security_app.models.Order;
import org.example.security_app.models.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    public Optional<Order> findOrderByStatus(OrderStatus status);
}
