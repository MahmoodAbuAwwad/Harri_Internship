package com.Harri.InvoiceTrackerBE.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceLogsRepository extends CrudRepository<com.Harri.InvoiceTrackerBE.models.InvoiceLogs,Long> {
    List<com.Harri.InvoiceTrackerBE.models.InvoiceLogs> findAllByInvoice_Id(long invoiceId);
}
