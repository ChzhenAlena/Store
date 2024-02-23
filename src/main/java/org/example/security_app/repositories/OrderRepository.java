package org.example.security_app.repositories;

import org.example.security_app.models.Order;
import org.example.security_app.models.OrderStatus;
import org.example.security_app.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    public Optional<Order> findOrderByStatusAndOwner(OrderStatus status, Person owner);
    public List<Order> findOrdersByOwner(Person person);
}
