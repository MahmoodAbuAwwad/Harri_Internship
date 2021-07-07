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
//delete items-invoice from cross table by invoice id
public interface InvoiceItemsRepository extends CrudRepository<InvoicesItems,Long> {
    void deleteAllByInvoiceId(long id);


}
