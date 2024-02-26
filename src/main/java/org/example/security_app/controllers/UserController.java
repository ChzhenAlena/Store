package org.example.security_app.controllers;

import org.example.security_app.models.OrderStatus;
import org.example.security_app.services.OrderItemService;
import org.example.security_app.services.OrderService;
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
    @GetMapping
    public String index(){
        return "user/index";
    }

    @GetMapping("/cart")
    public String showCart(Model model){
        model.addAttribute("items", orderService.getItemsFromActiveOrder());
        return "user/cart";
    }
    @DeleteMapping("/cart/{id}")
    public String deleteItemFromCart(@PathVariable("id") int id, Model model){
        orderItemService.deleteItem(id);
        model.addAttribute("items", orderService.getItemsFromActiveOrder());
        return "redirect:/user/cart";
    }
    @PostMapping("/cart/{id}/edit")
    public String changeItemAmountInCart(@PathVariable("id") int id, @RequestParam("amount") int amount, Model model){
        orderItemService.changeItemAmount(id, amount);
        model.addAttribute("items", orderService.getItemsFromActiveOrder());
        return "user/cart";
    }
    @GetMapping("/orders")
    public String showOrders(Model model){
        model.addAttribute("processingOrders", orderService.getOrdersByStatus(OrderStatus.processing));
        model.addAttribute("doneOrders", orderService.getOrdersByStatus(OrderStatus.done));
        return "user/orders";
    }
    @DeleteMapping("/orders/{id}")
    public String deleteOrder(@PathVariable("id") int id, Model model){
        orderService.deleteOrder(id);
        return "redirect:/user/orders";
    }
}
