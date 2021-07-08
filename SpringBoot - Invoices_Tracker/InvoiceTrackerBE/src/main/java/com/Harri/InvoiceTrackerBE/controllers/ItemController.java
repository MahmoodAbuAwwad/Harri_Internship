package com.Harri.InvoiceTrackerBE.controllers;

import com.Harri.InvoiceTrackerBE.dtos.ItemDto;
import com.Harri.InvoiceTrackerBE.models.Item;
import com.Harri.InvoiceTrackerBE.repositories.ItemRepository;
import com.Harri.InvoiceTrackerBE.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@PreAuthorize("hasAuthority('SUPERUSER')")
/*
* this controller to add handle the items
* handle operation is only allowed to super user
* */
public class ItemController {

    //inject all repos and services needed.
    @Autowired
    ItemRepository itemRepo;
    @Autowired
    private ItemService itemService;

    //get all items, also used in creating invoice page so its allowed to user also
    @PreAuthorize("hasAuthority('SUPERUSER')"+"||hasAuthority('USER') ")
    @GetMapping("/items")
    public List<Item> getItems(){
        return (List<Item>) itemRepo.findAll();
    }

    //get item by specific id
    @GetMapping("/items/{id}")
    public Item getItem(@PathVariable long id){
        return  itemRepo.findById(id);
    }

    //add new Item
    @PostMapping("/items")
    public ResponseEntity<?> addItem(@RequestBody ItemDto item) throws Exception {
        return  itemService.addItem(item);
    }

    //delete specific item
    /*
    * INFO: need to be handled with database, this will give error, break constraint in db
    * */
    @DeleteMapping("/items/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable long id) throws Exception {
       return this.itemService.deleteItem(id);
    }

    //edit specific item
    @PutMapping("/items/{id}")
    public ResponseEntity<?> editItem(@PathVariable long id, @RequestBody ItemDto item ) throws Exception {
        return  itemService.editItem(id,item);
    }

}
