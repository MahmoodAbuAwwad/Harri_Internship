package com.Harri.InvoiceTrackerBE.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoices_logs")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class InvoiceLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(nullable = false, length = 20,name = "date_of_log")
    private LocalDateTime logDate;

    @Column(nullable = false, length = 600,name = "note")
    private String logNote;
    @Column(nullable = false, length = 600,name = "status_of_log")
    private String logStatus;

    @Column(nullable = false,name = "data_before")
    private String logDataBefore;

    @Column(nullable = false,name = "data_after")
    private String logDataAfter;

    @ManyToOne
    @JoinColumn(name = "invoice_id",referencedColumnName = "id")
    private Invoice invoice;

}