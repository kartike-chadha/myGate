package com.kartike.my_gate.domain;

import com.kartike.my_gate.enums.LogTypeEnum;
import com.kartike.my_gate.enums.UserTypeEnum;
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
public class GateLog {

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
    private Integer logId;

//    Removed because adding reference with join columns which will handle the creation of fk field
//    @Column(nullable = false)
//    private String houseId;
//
//    @Column(nullable = false)
//    private UUID userId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserTypeEnum userType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LogTypeEnum logType;

    @Column(nullable = false)
    private OffsetDateTime logTime;

    @Column(nullable = false)
    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id")
    private Layout house;

}
