package com.Harri.InvoiceTrackerBE.controllers;

import com.Harri.InvoiceTrackerBE.models.Invoice;

import com.Harri.InvoiceTrackerBE.repositories.InvoiceItemsRepository;
import com.Harri.InvoiceTrackerBE.repositories.InvoiceRepository;
import com.Harri.InvoiceTrackerBE.services.InvoiceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;


@RestController
public class InvoiceController  {
    @Autowired
    private InvoiceRepository invoiceRepo;
    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoiceItemsRepository invoicesItemsRepo;

    @Autowired
    private com.Harri.InvoiceTrackerBE.repositories.InvoiceLogs invoiceLogRepo;
    @Autowired
    private com.Harri.InvoiceTrackerBE.repositories.InvoiceLogs logsRepo;

    public static  String uploadDir = System.getProperty("user.dir")+"/uploads";

    //add Invoice
    @PostMapping("/invoices")
    public ResponseEntity<?>addInvoice(@RequestParam(value = "json", required = false) String invoice,@RequestParam(value = "file", required = false) MultipartFile file ) throws IOException {
        return invoiceService.addInvoice(invoice,file);
    }

    //get all invoices
    @GetMapping("/invoices")
    public List<Invoice> getAllInvoices(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "invoiceDate") String sortBy)  {
        return invoiceService.getAllInvoices(pageNo,pageSize,sortBy);
    }

    @GetMapping("/invoices/{user_id}")
    public List<Invoice> getAllInvoicesOfUser(@PathVariable long user_id, @RequestParam(defaultValue = "0") Integer pageNo,
                                              @RequestParam(defaultValue = "10") Integer pageSize,
                                              @RequestParam(defaultValue = "invoiceDate") String sortBy )  {
        return invoiceService.getAllInvoicesOfUser(user_id,pageNo,pageSize,sortBy);
    }
}
