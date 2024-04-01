package com.kartike.my_gate.domain;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Getter
@Setter
public class Vendor {

    @Id
    @Column(nullable = false, updatable = false)
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private UUID vendorId;

    @Column(nullable = false)
    private String vendorName;

//    Removed because adding reference with join columns which will handle the creation of fk field
//    @Column(nullable = false)
//    private Integer amenityId;
    @Column(nullable = false)
    private Integer totalRequests;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "amenity_id", nullable = false, unique = true)
    private Amenity amenity;

//    @OneToMany(mappedBy = "user")
//    private Set<UserState> userUserStates;

//    @OneToMany(mappedBy = "user")
//    private Set<GateLog> userGateLogs;

}
