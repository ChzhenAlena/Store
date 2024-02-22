package org.example.security_app.servcies;

import org.example.security_app.models.Order;
import org.example.security_app.models.OrderItems;
import org.example.security_app.models.OrderStatus;
import org.example.security_app.models.Person;
import org.example.security_app.repositories.ItemRepository;
import org.example.security_app.repositories.OrderItemsRepository;
import org.example.security_app.repositories.OrderRepository;
import org.example.security_app.repositories.PeopleRepository;
import org.example.security_app.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final PeopleRepository peopleRepository;
    private final ItemRepository itemRepository;
    @Autowired
    public OrderService(OrderRepository orderRepository, OrderItemsRepository orderItemsRepository, PeopleRepository peopleRepository, ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemsRepository = orderItemsRepository;
        this.peopleRepository = peopleRepository;
        this.itemRepository = itemRepository;
    }
    public void addItem(int id, int amount){
        Optional<Order> orderCheck = orderRepository.findOrderByStatus(OrderStatus.active);
        Order order;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        Person person = personDetails.getPerson();
        if(orderCheck.isEmpty()){
            order = new Order(person, OrderStatus.active);
            orderRepository.save(order);
        }
        else
            order = orderCheck.get();
        OrderItems orderItems = new OrderItems(order, itemRepository.findById(id).get(), amount);
        order.getItems().add(orderItems);
        orderRepository.save(order);
        System.out.println("Order has been saved");
    }

}
