package com.kartike.my_gate.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.dialect.pagination.OffsetFetchLimitHandler;


@Entity
@Getter
@Setter
public class Payable {

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
    private Integer paymentId;

//    Removed because adding reference with join columns which will handle the creation of fk field
//    @Column(nullable = false)
//    private UUID ownerId;

    @Column(nullable = false)
    private Integer amountPaid;

    @Column(nullable = false)
    private OffsetDateTime dateCreated;

//    Removed because adding reference with join columns which will handle the creation of fk field
//    @Column(nullable = false)
//    private Integer invoiceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

}
