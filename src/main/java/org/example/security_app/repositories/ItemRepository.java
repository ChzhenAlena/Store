package org.example.security_app.repositories;

import org.example.security_app.models.Item;
import org.example.security_app.models.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    public List<Item> findItemsByCategory(ItemCategory category);
}
