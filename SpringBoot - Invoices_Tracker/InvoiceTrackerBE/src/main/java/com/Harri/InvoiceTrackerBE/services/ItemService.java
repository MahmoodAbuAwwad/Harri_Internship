package com.Harri.InvoiceTrackerBE.services;

import com.Harri.InvoiceTrackerBE.dtos.ItemDto;
import com.Harri.InvoiceTrackerBE.models.Item;
import com.Harri.InvoiceTrackerBE.models.User;
import com.Harri.InvoiceTrackerBE.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepo;
    public ResponseEntity<?> addItem(ItemDto item){
        Item newItem = new Item();
        newItem.setName(item.getName());
        newItem.setPrice(item.getPrice());
        this.itemRepo.save(newItem);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> deleteItem(long id){
        if(itemRepo.existsById(id)){
            itemRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return  new ResponseEntity<>("Item not found !",HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<?> editItem(long id, ItemDto item){
        if(itemRepo.existsById(id)){
            Item newItem = itemRepo.findById(id);
            newItem.setName(item.getName());
            newItem.setPrice(item.getPrice());
            Item nnewitem = itemRepo.save(newItem);
            if(nnewitem!=null){
                return  new ResponseEntity<>(HttpStatus.OK);

            }
        }
        return  new ResponseEntity<>("User not found !",HttpStatus.FORBIDDEN);
    }

}
