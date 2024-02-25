package org.example.security_app.services;

import jakarta.transaction.Transactional;
import org.example.security_app.models.Order;
import org.example.security_app.models.OrderItem;
import org.example.security_app.models.OrderStatus;
import org.example.security_app.models.Person;
import org.example.security_app.repositories.OrderRepository;
import org.example.security_app.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemService itemService;
    private final OrderItemService orderItemService;
    @Autowired
    public OrderService(OrderRepository orderRepository, ItemService itemService, OrderItemService orderItemService) {
        this.orderRepository = orderRepository;
        this.itemService = itemService;
        this.orderItemService = orderItemService;
    }
    @Transactional
    public void addItem(int itemId, int amount){
        List<Order> orders = getOrdersByStatus(OrderStatus.active);
        Order order;
        if(orders.isEmpty()){
            order = new Order(getPerson(), OrderStatus.active);
            orderRepository.save(order);
        }
        else
            order = orders.get(0);
        Optional<OrderItem> existingOrderItem= orderItemService.findExistingItem(order, itemService.findItem(itemId).get());
        OrderItem item;
        if(existingOrderItem.isEmpty()) {
            item = new OrderItem(itemService.findItem(itemId).get(), amount);
            order.addItem(item);
            orderRepository.save(order);
        }
        else {
            item = existingOrderItem.get();
            item.setAmount(item.getAmount()+amount);
            orderItemService.save(item);
        }
        itemService.decreaseItemAmount(itemId, amount);
    }
    public List<Order> getOrdersByStatus(OrderStatus status){
        return orderRepository.findOrdersByStatusAndOwner(status, getPerson());
    }
    public List<Order> getOrdersByStatusAndOwner(OrderStatus status, Person person){
        return orderRepository.findOrdersByStatusAndOwner(status, person);
    }
    public Optional<Order> getOrder(int id){
        return orderRepository.findById(id);
    }
    private Person getPerson(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        return personDetails.getPerson();
    }
    public List<OrderItem> getItemsFromActiveOrder(){
        Person person = getPerson();
        List<Order> orders = orderRepository.findOrdersByStatusAndOwner(OrderStatus.active, person);
        if(orders.isEmpty())
            return null;
        else
            return orders.get(0).getItems();
    }
    public void deleteOrder(int id){
        orderRepository.delete(orderRepository.findById(id).get());
    }
    public void makeDone(int id){
        Order order = orderRepository.findById(id).get();
        order.setStatus(OrderStatus.done);
        orderRepository.save(order);
    }

}
