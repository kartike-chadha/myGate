package com.kartike.my_gate.domain;

import com.kartike.my_gate.model.RequestStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Column(nullable = false)
    private UUID ownerId;

    @Column(nullable = false)
    private Integer amenityRequested;

    @Column(nullable = false)
    private OffsetDateTime dateCreated;

    @Column(nullable = false)
    private OffsetDateTime eta;

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
