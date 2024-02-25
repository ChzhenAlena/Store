package org.example.security_app.controllers;

import org.example.security_app.servcies.OrderItemService;
import org.example.security_app.servcies.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;
    @Autowired
    public UserController(OrderService orderService, OrderItemService orderItemService) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }

    @GetMapping("/cart")
    public String showCart(Model model){
        model.addAttribute("items", orderService.getItemsFromActiveOrder());
        return "user/cart";
    }
    @PostMapping("/cart/{id}")
    public String deleteItemFromCart(@PathVariable("id") int id, Model model){
        orderItemService.deleteItem(id);
        model.addAttribute("items", orderService.getItemsFromActiveOrder());
        return "user/cart";
    }
    @PostMapping("/cart/{id}/edit")
    public String changeItemAmountInCart(@PathVariable("id") int id, @RequestParam("amount") int amount, Model model){
        orderItemService.changeItemAmount(id, amount);
        model.addAttribute("items", orderService.getItemsFromActiveOrder());
        return "user/cart";
    }
    @GetMapping("/orders")
    public String showOrders(Model model){
        System.out.println("orders");
        model.addAttribute("orders", orderService.getOrders());
        return "user/orders";
    }
    @GetMapping("/orders/{id}")
    public String showOrder(@PathVariable("id") int id, Model model){
        model.addAttribute("order", orderService.getOrder(id).get());
        return "user/order";
    }
}
