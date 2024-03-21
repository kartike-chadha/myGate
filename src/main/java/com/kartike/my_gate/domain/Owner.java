package com.kartike.my_gate.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Owner {

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
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column
    private String address;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Long bankAccount;

//    @OneToMany(mappedBy = "owner")
//    private Set<Invoice> ownerInvoices;
//
//    @OneToMany(mappedBy = "user")
//    private Set<UserState> userUserStates;

//    @OneToMany(mappedBy = "user")
//    private Set<GateLog> userGateLogs;

//    @OneToMany(mappedBy = "owner")
//    private Set<Layout> ownerLayouts;

//    @OneToMany(mappedBy = "owner")
//    private Set<AmenityRequest> ownerAmenityRequests;

//    @OneToMany(mappedBy = "amenity")
//    private Set<Amenity> amenityAmenities;

}
