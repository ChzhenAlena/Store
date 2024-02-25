package org.example.security_app.servcies;

import jakarta.transaction.Transactional;
import org.example.security_app.models.Item;
import org.example.security_app.models.ItemCategory;
import org.example.security_app.repositories.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
    public List<Item> findByCategory(ItemCategory category){
        return itemRepository.findItemsByCategory(category);
    }
    public Optional<Item> findItem(int id){
        return itemRepository.findById(id);
    }
    @Transactional
    public void decreaseItemAmount(int id, int amount){
        Item item = itemRepository.findById(id).get();
        item.setAmount(item.getAmount()-amount);
        itemRepository.save(item);
    }
    public void save(Item item){
        itemRepository.save(item);
    }
}
