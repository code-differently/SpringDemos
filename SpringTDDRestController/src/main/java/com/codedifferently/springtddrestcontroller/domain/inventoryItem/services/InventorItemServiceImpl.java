package com.codedifferently.springtddrestcontroller.domain.inventoryItem.services;

import com.codedifferently.springtddrestcontroller.domain.core.ResourceCreationException;
import com.codedifferently.springtddrestcontroller.domain.core.ResourceNotFoundException;
import com.codedifferently.springtddrestcontroller.domain.inventoryItem.models.InventoryItem;
import com.codedifferently.springtddrestcontroller.domain.inventoryItem.repos.InventoryItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventorItemServiceImpl implements InventoryItemService {

    private InventoryItemRepo inventoryItemRepo;

    @Autowired
    public InventorItemServiceImpl(InventoryItemRepo inventoryItemRepo) {
        this.inventoryItemRepo = inventoryItemRepo;
    }

    @Override
    public InventoryItem create(InventoryItem inventoryItem) throws ResourceCreationException {
        Optional<InventoryItem> optional = inventoryItemRepo.findByName(inventoryItem.getName());
        if(optional.isPresent())
            throw new ResourceCreationException("Inventory with name exists: " + inventoryItem.getName());
        return inventoryItemRepo.save(inventoryItem);
    }

    @Override
    public List<InventoryItem> getAll() {
        return (List<InventoryItem>) inventoryItemRepo.findAll();
    }

    @Override
    public InventoryItem getById(Long id) throws ResourceNotFoundException {
        InventoryItem inventoryItem = inventoryItemRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No inventory with id " + id));
        return inventoryItem;
    }

    @Override
    public InventoryItem getByName(String name) throws ResourceNotFoundException {
        InventoryItem inventoryItem = inventoryItemRepo.findByName(name)
                .orElseThrow(()-> new ResourceNotFoundException("No inventory with name " + name));
        return inventoryItem;
    }

    @Override
    public InventoryItem update(Long id, InventoryItem inventoryItemDetail) throws ResourceNotFoundException {
        InventoryItem inventoryItem = getById(id);
        inventoryItem.setName(inventoryItemDetail.getName());
        inventoryItem.setDescription(inventoryItemDetail.getDescription());
        return inventoryItemRepo.save(inventoryItem);
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        InventoryItem inventoryItem = getById(id);
        inventoryItemRepo.delete(inventoryItem);
    }
}
