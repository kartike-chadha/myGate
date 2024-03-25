package com.kartike.my_gate.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class PaymentReconcileDTO {
    @NotNull
    private Integer invoiceAmount;

    private Integer penalty;
    @NotNull
    private OffsetDateTime payableAfter;
}
