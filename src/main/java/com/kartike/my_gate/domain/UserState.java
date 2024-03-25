package com.kartike.my_gate.domain;

import com.kartike.my_gate.enums.LogTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Entity
@Getter
@Setter
public class UserState {

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
    private Integer stateId;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LogTypeEnum state;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "owner_user_id")
//    private Owner owner;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "vendor_user_id")
//    private Vendor vendor;


}
