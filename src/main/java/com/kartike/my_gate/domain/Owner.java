package com.kartike.my_gate.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.util.Set;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Getter
@Setter
public class Owner {

    @Id
    @Column(nullable = false, updatable = false)
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column
    private String address;

    @Column(nullable = false)
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
