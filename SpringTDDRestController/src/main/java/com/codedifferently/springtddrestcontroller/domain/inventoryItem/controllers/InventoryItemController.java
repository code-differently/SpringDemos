package com.codedifferently.springtddrestcontroller.domain.inventoryItem.controllers;

import com.codedifferently.springtddrestcontroller.domain.inventoryItem.models.InventoryItem;
import com.codedifferently.springtddrestcontroller.domain.inventoryItem.services.InventoryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryItemController {

    private InventoryItemService inventoryItemService;

    @Autowired
    public InventoryItemController(InventoryItemService inventoryItemService) {
        this.inventoryItemService = inventoryItemService;
    }

    @PostMapping
    public ResponseEntity<InventoryItem> create(@RequestBody InventoryItem inventoryItem){
        inventoryItem = inventoryItemService.create(inventoryItem);
        return new ResponseEntity<>(inventoryItem, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<InventoryItem>> getAll(){
        List<InventoryItem> inventory = inventoryItemService.getAll();
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<InventoryItem> getById(@PathVariable("id") Long id){
        InventoryItem inventoryItem = inventoryItemService.getById(id);
        return new ResponseEntity<>(inventoryItem, HttpStatus.OK);
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<InventoryItem> getByName(@PathVariable("name") String  name){
        InventoryItem inventoryItem = inventoryItemService.getByName(name);
        return new ResponseEntity<>(inventoryItem, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<InventoryItem> update(@PathVariable("id")Long id, @RequestBody InventoryItem inventoryItem){
        inventoryItem = inventoryItemService.update(id, inventoryItem);
        return new ResponseEntity<>(inventoryItem, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        inventoryItemService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
