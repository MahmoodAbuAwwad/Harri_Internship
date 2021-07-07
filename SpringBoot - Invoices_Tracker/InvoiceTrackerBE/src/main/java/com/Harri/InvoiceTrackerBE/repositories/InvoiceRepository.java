package com.Harri.InvoiceTrackerBE.repositories;

import com.Harri.InvoiceTrackerBE.models.Invoice;
import com.Harri.InvoiceTrackerBE.models.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends PagingAndSortingRepository<Invoice,Long> {
    Invoice findById(long id);
    List<Invoice> findAll();
    List<Invoice> findAllByUser_Id(long id, Pageable pageable);
}
