package org.example.security_app.controllers;

import org.example.security_app.servcies.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
public class OrdersController {
    private final OrderService orderService;
    @Autowired
    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping()
    public String showOrders(Model model){
        model.addAttribute("orders", orderService.getOrders());
        return "orders/index";
    }
    @GetMapping("/{id}")
    public String showOrder(@PathVariable("id") int id, Model model){
        model.addAttribute("order", orderService.getOrder(id).get());
        return "orders/show";
    }
}
