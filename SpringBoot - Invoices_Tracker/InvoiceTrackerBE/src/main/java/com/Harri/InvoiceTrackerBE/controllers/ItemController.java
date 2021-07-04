package com.Harri.InvoiceTrackerBE.controllers;

import com.Harri.InvoiceTrackerBE.dtos.ItemDto;
import com.Harri.InvoiceTrackerBE.dtos.UserDTO;
import com.Harri.InvoiceTrackerBE.models.Item;
import com.Harri.InvoiceTrackerBE.models.User;
import com.Harri.InvoiceTrackerBE.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
public class ItemController {

    @Autowired
    ItemRepository itemRepo;

    @GetMapping("items")
    public List<Item> getItems(){
        return (List<Item>) itemRepo.findAll();
    }

    @GetMapping("items/{id}")
    public Item getItem(@PathVariable long id){
        return  itemRepo.findById(id);
    }

    @PostMapping("/items")
    public ResponseEntity<?> Register(@RequestBody ItemDto item) throws Exception {
        Item newItem = new Item();
        newItem.setName(item.getName());
        newItem.setPrice(item.getPrice());
        itemRepo.save(newItem);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/items/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) throws Exception {
        if(itemRepo.existsById(id)){
            itemRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return  new ResponseEntity<>("Item not found !",HttpStatus.FORBIDDEN);
    }

//    @PutMapping("/users/edit/{id}")
//    public ResponseEntity<?> editUser(@PathVariable long id, @RequestBody UserDTO user ) throws Exception {
//        if(userRepo.existsById(id)){
//            User newUser = new User();
//            newUser.setAge(user.getAge());
//            newUser.setEmail(user.getEmail());
//            newUser.setAddress(user.getAddress());
//            newUser.setFirstName(user.getFirstName());
//            newUser.setLastName(user.getLastName());
//            newUser.setRole(user.getRole());
//            newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
//            this.userService.editUser(newUser);
//            return new ResponseEntity<>(HttpStatus.OK);
//        }
//        return  new ResponseEntity<>("User not found !",HttpStatus.FORBIDDEN);
//    }

}
