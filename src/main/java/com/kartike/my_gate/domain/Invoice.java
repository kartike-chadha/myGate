package com.kartike.my_gate.domain;


import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.OffsetDateTime;


@Entity
@Getter
@Setter
public class Invoice {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Integer invoiceId;

//    Removed because adding reference with join columns which will handle the creation of fk field
//    @Column(nullable = false)
//    private UUID ownerId;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private OffsetDateTime dateCreated;

    @Column(nullable = false)
    private OffsetDateTime datePayable;

    private Timestamp timestamp;

//    @OneToMany(mappedBy = "invoice")
//    private Set<Payable> invoicePayables;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

}
