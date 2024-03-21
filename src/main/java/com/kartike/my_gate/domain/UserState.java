package com.kartike.my_gate.domain;

import com.kartike.my_gate.model.UserStateEnum;
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
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


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

//    Removed because adding reference with join columns which will handle the creation of fk field
//    @Column(nullable = false)
//    private UUID ownerId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStateEnum state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_user_id")
    private Owner owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_user_id")
    private Vendor vendor;

}
