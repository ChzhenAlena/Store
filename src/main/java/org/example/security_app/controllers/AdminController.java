package org.example.security_app.controllers;

import jakarta.validation.Valid;
import org.example.security_app.models.Item;
import org.example.security_app.models.ItemCategory;
import org.example.security_app.models.OrderStatus;
import org.example.security_app.services.ItemService;
import org.example.security_app.services.OrderService;
import org.example.security_app.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final PeopleService peopleService;
    private final OrderService orderService;
    private final ItemService itemService;
    @Autowired
    public AdminController(PeopleService peopleService, OrderService orderService, ItemService itemService) {
        this.peopleService = peopleService;
        this.orderService = orderService;
        this.itemService = itemService;
    }
    @GetMapping
    public String index(){
        return "admin/index";
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
    @DeleteMapping("/users/{user_id}/orders/{order_id}")
    public String deleteUserOrder(@PathVariable("order_id") int orderId, Model model, @PathVariable("user_id") int userId){
        orderService.deleteOrder(orderId);
        return "redirect:/users/{id}/orders";
    }
    @PostMapping("/users/{user_id}/orders/{order_id}")
    public String changeOrderStatus(@PathVariable("order_id") int orderId, Model model, @PathVariable("user_id") int userId){
        orderService.makeDone(orderId);
        model.addAttribute("processingOrders", orderService.getOrdersByStatusAndOwner(OrderStatus.processing, peopleService.findById(userId)));
        model.addAttribute("doneOrders", orderService.getOrdersByStatusAndOwner(OrderStatus.done, peopleService.findById(userId)));
        return "admin/orders";
    }
    @GetMapping("/categories")
    public String showCategories(Model model){
        model.addAttribute("categories", ItemCategory.values());
        return "admin/categories";
    }
    @GetMapping("/categories/{category}")
    public String showItems(@PathVariable("category") String category, Model model){
        model.addAttribute("items", itemService.findByCategory(ItemCategory.valueOf(category)));
        model.addAttribute("category", category);
        return "admin/items";
    }
    @PostMapping("/categories/{category}/{id}")
    public String changeItemAmount(@PathVariable("category") String category, @PathVariable("id") int id, @RequestParam("amount") int amount, Model model){
        itemService.changeAmount(id, amount);
        model.addAttribute("items", itemService.findByCategory(ItemCategory.valueOf(category)));
        model.addAttribute("category", category);
        return "admin/items";
    }
    @DeleteMapping("/categories/{category}/{id}")
    public String deleteItem(@PathVariable("category") String category, @PathVariable("id") int id, Model model){
        itemService.deleteItem(id);
        return "redirect:/admin/categories/{category}";
    }
    @GetMapping("/categories/{category}/add")
    public String newItem(@PathVariable("category") String category, Model model){
        model.addAttribute("item", new Item(ItemCategory.valueOf(category)));
        return "admin/addItem";
    }
    @PostMapping("/categories/{category}")
    public String createItem(@ModelAttribute("item") @Valid Item item, BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "admin/addItem";
        itemService.save(item);
        return "redirect:/admin/categories/{category}";
    }
}
