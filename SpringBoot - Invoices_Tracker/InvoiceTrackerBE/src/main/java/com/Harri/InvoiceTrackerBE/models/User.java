package com.Harri.InvoiceTrackerBE.models;


import com.Harri.InvoiceTrackerBE.enums.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;
// lomo

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 20)
    private String firstName;
    @Column(nullable = false, length = 20)
    private String lastName;
    @Column(nullable = false,unique = true, length = 45)
    private String email;
    @Column(nullable = false, length = 100)
    private String address;
    @Column(nullable = false)
    private int age;
    @Column(nullable = false, length = 500)
    private String password;

    @Column(nullable = false, name = "deleted")
    private int deleted;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserRole role;

//    @OneToMany(mappedBy = "user", fetch=FetchType.LAZY)
//    private Set<Invoice> invoices;

}
