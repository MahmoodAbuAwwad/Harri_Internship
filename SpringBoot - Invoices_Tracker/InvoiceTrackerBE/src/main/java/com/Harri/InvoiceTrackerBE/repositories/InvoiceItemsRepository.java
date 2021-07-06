package com.Harri.InvoiceTrackerBE.repositories;

import com.Harri.InvoiceTrackerBE.models.Invoice;
import com.Harri.InvoiceTrackerBE.models.InvoicesItems;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceItemsRepository extends CrudRepository<InvoicesItems,Long> {
}
