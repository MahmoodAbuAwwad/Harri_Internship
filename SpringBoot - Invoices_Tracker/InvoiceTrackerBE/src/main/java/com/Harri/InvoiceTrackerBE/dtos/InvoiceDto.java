package com.Harri.InvoiceTrackerBE.dtos;

import com.Harri.InvoiceTrackerBE.enums.InvoiceTypes;
import com.Harri.InvoiceTrackerBE.models.Item;
import com.Harri.InvoiceTrackerBE.models.User;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;


//Inovice Data transfer object

public class InvoiceDto {
    private long id;
    private InvoiceTypes invoiceType;
    private LocalDateTime dateOfInvoice = LocalDateTime.now();
    private float total;
    private User user;
    private MultipartFile file;
    private String file_type;
    private Item[] items;

    public Item[] getItems() {
        return items;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public void setDateOfInvoice(LocalDateTime dateOfInvoice) {
        this.dateOfInvoice = dateOfInvoice;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }


    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public InvoiceTypes getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(InvoiceTypes invoiceType) {
        this.invoiceType = invoiceType;
    }

    public LocalDateTime getDateOfInvoice() {
        return  LocalDateTime.now();
    }

    public void setDateOfInvoice() {
        this.dateOfInvoice =  LocalDateTime.now();
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
