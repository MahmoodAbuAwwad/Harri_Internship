package com.Harri.InvoiceTrackerBE.controllers;

import com.Harri.InvoiceTrackerBE.dtos.ItemDto;
import com.Harri.InvoiceTrackerBE.dtos.UserDTO;
import com.Harri.InvoiceTrackerBE.models.Item;
import com.Harri.InvoiceTrackerBE.models.User;
import com.Harri.InvoiceTrackerBE.repositories.ItemRepository;
import com.Harri.InvoiceTrackerBE.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@PreAuthorize("hasAuthority('SUPERUSER')")
public class ItemController {

    @Autowired
    ItemRepository itemRepo;

    @Autowired
    private ItemService itemService;
    @PreAuthorize("hasAuthority('SUPERUSER')"+"||hasAuthority('USER') ")
    @GetMapping("/items")
    public List<Item> getItems(){
        return (List<Item>) itemRepo.findAll();
    }

    @GetMapping("/items/{id}")
    public Item getItem(@PathVariable long id){
        return  itemRepo.findById(id);
    }

    @PostMapping("/items")
    public ResponseEntity<?> addItem(@RequestBody ItemDto item) throws Exception {
        return  itemService.addItem(item);
    }

    @DeleteMapping("/items/delete/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable long id) throws Exception {
       return this.itemService.deleteItem(id);
    }

    @PutMapping("/items/edit/{id}")
    public ResponseEntity<?> editItem(@PathVariable long id, @RequestBody ItemDto item ) throws Exception {
        return  itemService.editItem(id,item);
    }

}
