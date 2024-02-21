package org.example.security_app.servcies;

import org.example.security_app.models.Item;
import org.example.security_app.models.ItemCategory;
import org.example.security_app.repositories.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
    public List<Item> findByCategory(ItemCategory category){
        return itemRepository.findItemsByCategory(category);
    }
}
