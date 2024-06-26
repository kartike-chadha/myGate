package com.kartike.my_gate.domain;

import com.kartike.my_gate.enums.RequestStatusEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.OffsetDateTime;


@Entity
@Getter
@Setter
public class AmenityRequest {

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
    private Integer requestId;

//    Removed because adding reference with join columns which will handle the creation of fk field
//    @Column(nullable = false)
//    private UUID ownerId;
//
//    @Column(nullable = false)
//    private Integer amenityRequested;

    @Column(nullable = false)
    private OffsetDateTime dateCreated;


    @Column(nullable = false)
    private OffsetDateTime scheduledDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestStatusEnum requestStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requested_amenity_id")
    private Amenity requestedAmenity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

}
