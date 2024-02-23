package org.example.security_app.servcies;

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
    @Autowired
    public OrderService(OrderRepository orderRepository, ItemService itemService) {
        this.orderRepository = orderRepository;
        this.itemService = itemService;
    }
    @Transactional
    public void addItem(int itemId, int amount){
        Person person = getPerson();
        Order order = getOrCreateActiveOrder(person);
        OrderItem newItem = new OrderItem(itemService.findItem(itemId).get(), amount);
        order.addItem(newItem);
        orderRepository.save(order);
        itemService.decreaseItemAmount(itemId, amount);
    }
    public List<Order> getOrders(){
        Person person = getPerson();
        return orderRepository.findOrdersByOwner(person);
    }
    public Optional<Order> getOrder(int id){
        return orderRepository.findById(id);
    }
    private Person getPerson(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        return personDetails.getPerson();
    }
    private Order getOrCreateActiveOrder(Person person){
        Optional<Order> orderCheck = orderRepository.findOrderByStatusAndOwner(OrderStatus.active, person);
        Order order;
        if(orderCheck.isEmpty()){
            order = new Order(person, OrderStatus.active);
            orderRepository.save(order);
        }
        else
            order = orderCheck.get();
        return order;
    }

}
