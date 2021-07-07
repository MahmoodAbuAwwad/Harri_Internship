package com.Harri.InvoiceTrackerBE.repositories;

import com.Harri.InvoiceTrackerBE.models.Item;
import com.Harri.InvoiceTrackerBE.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<Item,Long> {
    Item findById(long id);

    //native query with SQL,  return items of an invoice -- used in preview
    @Query(value = "SELECT i.* FROM invoices_items as ii JOIN  items as i on ii.item_id=i.id where invoice_id= :invoiceId", nativeQuery = true)
    List<Item> findInvoiceItems(@Param("invoiceId") long invoiceId);


}
