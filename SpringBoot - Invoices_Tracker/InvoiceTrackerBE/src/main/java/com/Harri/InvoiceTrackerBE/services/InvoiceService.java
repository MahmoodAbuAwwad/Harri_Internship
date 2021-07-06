package com.Harri.InvoiceTrackerBE.services;

import com.Harri.InvoiceTrackerBE.controllers.InvoiceController;
import com.Harri.InvoiceTrackerBE.enums.InvoiceTypes;
import com.Harri.InvoiceTrackerBE.enums.UserRole;
import com.Harri.InvoiceTrackerBE.models.*;
import com.Harri.InvoiceTrackerBE.repositories.InvoiceItemsRepository;
import com.Harri.InvoiceTrackerBE.repositories.InvoiceRepository;
import com.Harri.InvoiceTrackerBE.repositories.ItemRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepo;
    @Autowired
    private ItemRepository itemRepo;
    @Autowired
    private InvoiceItemsRepository invoiceItemsRepo;

    @Autowired
    private com.Harri.InvoiceTrackerBE.repositories.InvoiceLogs invoiceLogsRepo;





    public ResponseEntity<?> addInvoice(String invoice, MultipartFile file) throws IOException {
        System.out.println(invoice.toString());
        JSONObject invoiceJson = new JSONObject(invoice.toString());
        Invoice newInvoice = new Invoice();
        newInvoice.setInvoiceType(returnInvoiceType(invoiceJson.getInt("invoiceType")));
        newInvoice.setInvoiceDate(LocalDateTime.now());
        newInvoice.setInvoiceTotal(invoiceJson.getFloat("total"));
        newInvoice.setUser(getUserJson(invoiceJson.getJSONObject("user")));
        newInvoice.setFileType(invoiceJson.getString("type"));
        if(newInvoice.getFileType().compareTo("None")!=0){
            String orgName = file.getOriginalFilename();
            String filePath = InvoiceController.uploadDir + "/"+orgName;
            File dest = new File(filePath);
            file.transferTo(dest);
            newInvoice.setFilePath(InvoiceController.uploadDir +"/"+file.getOriginalFilename());
        }
        else{
            newInvoice.setFileType("None");
        }

        Invoice createdInvoice = this.invoiceRepo.save(newInvoice);
        if(createdInvoice!=null){
            for(Item item : getItemsJson(invoiceJson.getJSONArray("items"))){
                InvoicesItems invoiceItem = new InvoicesItems();
                invoiceItem.setInvoice_id(newInvoice.getId());
                invoiceItem.setItem_id(item.getId());
                if (this.invoiceItemsRepo.save(invoiceItem)==null){
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }

            }

            InvoiceLogs newLog = new InvoiceLogs();
            newLog.setInvoice(newInvoice);
            newLog.setLogNote("Created");
            newLog.setLogDate(LocalDateTime.now());
            newLog.setLogStatus("Created");
            newLog.setLogDataBefore(null);
            newLog.setLogDataAfter(invoice.toString());
            InvoiceLogs createdLog = this.invoiceLogsRepo.save(newLog);
            if(createdLog!=null){
                return new ResponseEntity<>(HttpStatus.OK);

            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public InvoiceTypes returnInvoiceType(int i){
        switch (i){
            case 0: return InvoiceTypes.PIAD;
            case 1: return InvoiceTypes.NOT_PAID;
            case 2: return InvoiceTypes.HALF_PAID;
        }
        return InvoiceTypes.PIAD;
    }
    public UserRole returnUserRole(String i){
        switch (i){
            case "SUPERUSER": return UserRole.SUPERUSER;
            case "AUDITOR": return UserRole.AUDITOR;
            case "USER": return UserRole.USER;

        }
        return UserRole.SUPERUSER;
    }

    public User getUserJson(JSONObject user){
        User newUser = new User();
        newUser.setAge(user.getInt("age"));
        newUser.setAddress(user.getString("address"));
        newUser.setLastName(user.getString("lastName"));
        newUser.setRole(returnUserRole(user.getString("role")));
        newUser.setFirstName(user.getString("firstName"));
        newUser.setEmail(user.getString("email"));
        newUser.setPassword(user.getString("password"));
        newUser.setId(user.getLong("id"));
        return  newUser;
    }

    public Item[] getItemsJson(JSONArray itemss) {
        List<Item> items = new ArrayList<Item>();
        for (int i = 0; i < itemss.length(); i++) {
            Item newItem = new Item();
            newItem.setId(itemss.getJSONObject(i).getLong("id"));
            newItem.setName(itemss.getJSONObject(i).getString("name"));
            newItem.setPrice(itemss.getJSONObject(i).getString("price"));
            items.add(newItem);
        }
        Item [] arr  = new Item[itemss.length()];
        for (int i = 0; i < arr.length; i++) {
          arr[i] = new Item();
          arr[i].setPrice(items.get(i).getPrice());
          arr[i].setName(items.get(i).getName());
          arr[i].setId(items.get(i).getId());
        }

        return arr;
    }

        public List<Invoice> getAllInvoices(Integer pageNo,Integer pageSize, String sortBy){
            PageRequest paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
            Page<Invoice> pagedResult = invoiceRepo.findAll(paging);

            if(pagedResult.hasContent()) {
                return pagedResult.getContent();
            } else {
                return new ArrayList<Invoice>();
            }
//            return (List<Invoice>) invoiceRepo.findAll();
        }

        public List<Invoice> getAllInvoicesOfUser(long user_id,Integer pageNo,Integer pageSize, String sortBy){
             List<Invoice> allInvoices = getAllInvoices(pageNo,pageSize,sortBy);
             List<Invoice> forUser =new ArrayList<>();
             for(int i=0; i<allInvoices.size();i++){
                 if(allInvoices.get(i).getUser().getId()==user_id){
                     forUser.add((allInvoices.get(i)));
                 }
             }
             return forUser;

        }
}
