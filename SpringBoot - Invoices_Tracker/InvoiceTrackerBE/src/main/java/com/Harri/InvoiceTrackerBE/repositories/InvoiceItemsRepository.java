package com.Harri.InvoiceTrackerBE.repositories;

import com.Harri.InvoiceTrackerBE.models.Invoice;
import com.Harri.InvoiceTrackerBE.models.InvoicesItems;
import com.Harri.InvoiceTrackerBE.models.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
@Transactional
public interface InvoiceItemsRepository extends CrudRepository<InvoicesItems,Long> {
    void deleteAllByInvoiceId(long id);
//
//    @Query(value = "FROM InvoicesItems JOIN  Item on InvoicesItems.itemId = Item.id where InvoicesItems.invoiceId= :invoiceId")
//    List<InvoicesItems> findInvoiceItems2( @Param("invoiceId") long invoiceId);


}
