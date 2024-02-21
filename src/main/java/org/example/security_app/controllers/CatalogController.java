package org.example.security_app.controllers;

import org.example.security_app.models.ItemCategory;
import org.example.security_app.servcies.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/catalog")
public class CatalogController {
    private final ItemService itemService;

    public CatalogController(ItemService itemService) {
        this.itemService = itemService;
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
        System.out.println(itemService.findByCategory(ItemCategory.valueOf(category)));
        return "/catalog/items";
    }
}
