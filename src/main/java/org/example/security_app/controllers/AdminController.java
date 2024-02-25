package org.example.security_app.controllers;

import org.example.security_app.models.OrderStatus;
import org.example.security_app.services.OrderService;
import org.example.security_app.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final PeopleService peopleService;
    private final OrderService orderService;
    @Autowired
    public AdminController(PeopleService peopleService, OrderService orderService) {
        this.peopleService = peopleService;
        this.orderService = orderService;
    }

    @GetMapping("/users")
    public String showUsers(Model model){
        model.addAttribute("users", peopleService.showPeople());
        return "admin/users";
    }
    @PostMapping("/users/{id}/lock")
    public String lockUser(@PathVariable("id") int id, Model model){
        peopleService.lockPerson(id);
        model.addAttribute("users", peopleService.showPeople());
        return "admin/users";
    }
    @GetMapping("/users/{id}/orders")
    public String showUserOrders(@PathVariable("id") int id, Model model){
        model.addAttribute("processingOrders", orderService.getOrdersByStatusAndOwner(OrderStatus.processing, peopleService.findById(id)));
        model.addAttribute("doneOrders", orderService.getOrdersByStatusAndOwner(OrderStatus.done, peopleService.findById(id)));
        return "admin/orders";
    }
    @PostMapping("/users/{user_id}/orders/{order_id}")
    public String deleteUserOrder(@PathVariable("order_id") int orderId, Model model, @PathVariable("user_id") int userId){
        orderService.deleteOrder(orderId);
        model.addAttribute("processingOrders", orderService.getOrdersByStatusAndOwner(OrderStatus.processing, peopleService.findById(userId)));
        model.addAttribute("doneOrders", orderService.getOrdersByStatusAndOwner(OrderStatus.done, peopleService.findById(userId)));
        return "admin/orders";
    }
    @PostMapping("/users/{user_id}/orders/{order_id}/edit")
    public String changeOrderStatus(@PathVariable("order_id") int orderId, Model model, @PathVariable("user_id") int userId){
        orderService.makeDone(orderId);
        model.addAttribute("processingOrders", orderService.getOrdersByStatusAndOwner(OrderStatus.processing, peopleService.findById(userId)));
        model.addAttribute("doneOrders", orderService.getOrdersByStatusAndOwner(OrderStatus.done, peopleService.findById(userId)));
        return "admin/orders";
    }
}
