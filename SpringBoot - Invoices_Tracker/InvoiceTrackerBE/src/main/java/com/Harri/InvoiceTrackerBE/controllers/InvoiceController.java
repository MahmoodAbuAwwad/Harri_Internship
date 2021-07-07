package com.Harri.InvoiceTrackerBE.controllers;
import com.Harri.InvoiceTrackerBE.models.Invoice;
import com.Harri.InvoiceTrackerBE.models.InvoiceLogs;
import com.Harri.InvoiceTrackerBE.models.Item;
import com.Harri.InvoiceTrackerBE.repositories.InvoiceItemsRepository;
import com.Harri.InvoiceTrackerBE.repositories.InvoiceRepository;
import com.Harri.InvoiceTrackerBE.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private com.Harri.InvoiceTrackerBE.repositories.InvoiceLogsRepository invoiceLogRepo;
    @Autowired
    private com.Harri.InvoiceTrackerBE.repositories.InvoiceLogsRepository logsRepo;

    //specify the directory to save the attachments.
    public static  String uploadDir = System.getProperty("user.dir")+"/uploads";

    //add Invoice- auditor is not allowed to add edit and delete
    @PreAuthorize("hasAuthority('SUPERUSER')"+"||hasAuthority('USER') ")
    @PostMapping("/invoices")
    public ResponseEntity<?>addInvoice(@RequestParam(value = "json", required = false) String invoice,@RequestParam(value = "file", required = false) MultipartFile file ) throws IOException {
        return invoiceService.addInvoice(invoice,file);
    }

    @PreAuthorize("hasAuthority('SUPERUSER')"+"||hasAuthority('USER') ")
    @PutMapping("/invoices/edit/{id}")
    public ResponseEntity<?>editInvoice(@RequestParam(value = "json", required = false) String invoice,@RequestParam(value = "file", required = false) MultipartFile file,@PathVariable long id ) throws IOException {
        return invoiceService.editInvoice(invoice,file,id);
    }

    //get all invoices
    @GetMapping("/invoices")
    public List<Invoice> getAllInvoices(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "invoiceDate") String sortBy)  {
        return invoiceService.getAllInvoices(pageNo,pageSize,sortBy);
    }

    @GetMapping("/invoices/preview/{id}")
    public Invoice getInvoice(@PathVariable long id)  {
        try{
            return invoiceService.previewInvoice(id);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }

    @GetMapping("/invoices/items/{id}")
    public List<Item> getItemsOfInvoice(@PathVariable long id)  {
        try{
            return invoiceService.getItemsOfInvoice(id);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }

    @GetMapping("/invoices/preview/logs/{invoiceId}")
    public List<InvoiceLogs> getInvoiceLogs(@PathVariable long invoiceId)  {
        return invoiceService.getLogsofInvoices(Long.valueOf(invoiceId));
    }


    @GetMapping("/invoices/{user_id}")
    public List<Invoice> getAllInvoicesOfUser(@PathVariable long user_id, @RequestParam(defaultValue = "0") Integer pageNo,
                                              @RequestParam(defaultValue = "10") Integer pageSize,
                                              @RequestParam(defaultValue = "invoiceDate") String sortBy)  {
        return invoiceService.getUserAllInvoices(pageNo,pageSize,sortBy,user_id);
    }

    @PreAuthorize("hasAuthority('SUPERUSER')"+"||hasAuthority('USER') ")
    @DeleteMapping("/invoices/delete/{id}")
    public ResponseEntity<?> deleteInvoice(@PathVariable long id) throws Exception {

        return this.invoiceService.deleteInvoice(id);
    }
}
