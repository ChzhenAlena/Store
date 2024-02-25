package org.example.security_app.controllers;

import org.example.security_app.models.ItemCategory;
import org.example.security_app.services.ItemService;
import org.example.security_app.services.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/catalog")
@Validated
public class CatalogController {
    private final ItemService itemService;
    private final OrderService orderService;

    public CatalogController(ItemService itemService, OrderService orderService) {
        this.itemService = itemService;
        this.orderService = orderService;
    }

    @GetMapping
    public String showCategories(Model model){
        model.addAttribute("categories", ItemCategory.values());
        return "/catalog/categories";
    }
    @GetMapping("/{category}")
    public String showItems(@PathVariable("category") String category, Model model){
        model.addAttribute("items", itemService.findByCategory(ItemCategory.valueOf(category)));
        model.addAttribute("category", category);
        return "/catalog/items";
    }
    @GetMapping("/{category}/{id}")
    public String showItem( @PathVariable("id") int id, Model model){
        model.addAttribute("item", itemService.findItem(id).get());
        return "/catalog/item";
    }
    @PostMapping("/{category}/{id}")
    public String addItemToCart(@PathVariable("id") int id, @RequestParam("amount") int amount){
        orderService.addItem(id, amount);
        return "/catalog/added";
    }
}
