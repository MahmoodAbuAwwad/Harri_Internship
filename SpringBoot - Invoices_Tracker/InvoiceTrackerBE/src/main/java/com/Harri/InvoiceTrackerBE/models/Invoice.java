package com.Harri.InvoiceTrackerBE.models;
import com.Harri.InvoiceTrackerBE.enums.InvoiceTypes;
import lombok.*;
import org.hibernate.mapping.Join;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20,name = "type_of_invoice")
    private InvoiceTypes invoiceType;

    @Column(nullable = false,name = "total")
    private float invoiceTotal;

    @Column(nullable = false, name = "date_of_invoice")
    private LocalDateTime invoiceDate;

    @Column(nullable = false, length = 20,name = "file_type")
    private String fileType;

    @Column(nullable = false, length = 500,name = "file_path")
    private String filePath;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


//    @OneToMany(mappedBy = "invoice", fetch=FetchType.LAZY)
//    private Set<InvoiceLogs> logs;

}
