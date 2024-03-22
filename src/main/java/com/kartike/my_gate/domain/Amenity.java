package com.kartike.my_gate.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Amenity {

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
    private String amenityName;

    @OneToOne(mappedBy = "amenity", fetch = FetchType.LAZY)
    private Vendor amenityVendors;

//    @OneToMany(mappedBy = "requestedAmenity")
//    private Set<AmenityRequest> requestedAmenityAmenityRequests;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "amenity_owner_id")
//    private Owner amenityOwner;

}
