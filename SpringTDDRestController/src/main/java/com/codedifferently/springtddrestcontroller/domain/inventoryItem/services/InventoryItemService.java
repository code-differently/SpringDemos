package com.codedifferently.springtddrestcontroller.domain.inventoryItem.services;

import com.codedifferently.springtddrestcontroller.domain.core.ResourceCreationException;
import com.codedifferently.springtddrestcontroller.domain.core.ResourceNotFoundException;
import com.codedifferently.springtddrestcontroller.domain.inventoryItem.models.InventoryItem;

import java.util.List;

public interface InventoryItemService {
    InventoryItem create (InventoryItem inventoryItem) throws ResourceCreationException;
    List<InventoryItem> getAll();
    InventoryItem getById(Long id) throws ResourceNotFoundException;
    InventoryItem getByName(String name) throws ResourceNotFoundException;
    InventoryItem update(Long id, InventoryItem inventoryItemDetail) throws ResourceNotFoundException;
    void delete(Long id) throws ResourceNotFoundException;
}
