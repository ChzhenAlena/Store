package org.example.security_app.services;

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
    private final ItemService itemService;
    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository, ItemService itemService) {
        this.orderItemRepository = orderItemRepository;
        this.itemService = itemService;
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
    public void changeItemAmount(int id, int amount){
        OrderItem orderItem = orderItemRepository.findById(id).get();
        Item item = orderItem.getItem();
        int currentAmountInOrder = orderItem.getAmount();
        int amountAtStorage = item.getAmount();
        int averageAmount = amountAtStorage + currentAmountInOrder;
        orderItem.setAmount(amount);
        item.setAmount(averageAmount-amount);
        itemService.save(item);
        orderItemRepository.save(orderItem);
    }
}
