package com.kartike.my_gate.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
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
