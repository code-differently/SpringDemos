package com.codedifferently.springtddrestcontroller.domain.inventoryItem.repos;

import com.codedifferently.springtddrestcontroller.domain.inventoryItem.models.InventoryItem;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InventoryItemRepo extends CrudRepository<InventoryItem, Long> {
    Optional<InventoryItem> findByName(String name);
}
