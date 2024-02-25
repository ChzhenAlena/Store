package org.example.security_app.repositories;

import org.example.security_app.models.Order;
import org.example.security_app.models.OrderStatus;
import org.example.security_app.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    public List<Order> findOrdersByStatusAndOwner(OrderStatus status, Person owner);
}
