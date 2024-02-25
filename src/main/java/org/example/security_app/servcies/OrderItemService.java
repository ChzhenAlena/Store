package org.example.security_app.servcies;

import org.example.security_app.models.Item;
import org.example.security_app.models.Order;
import org.example.security_app.models.OrderItem;
import org.example.security_app.repositories.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }
    public Optional<OrderItem> findExistingItem(Order order, Item item){
        return orderItemRepository.findOrderItemByOrderAndItem(order, item);
    }
    public void save(OrderItem orderItem){
        orderItemRepository.save(orderItem);
    }
    public void deleteItem(int id){
        orderItemRepository.delete(orderItemRepository.findById(id).get());
    }
}
