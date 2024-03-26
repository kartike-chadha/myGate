package com.kartike.my_gate.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;


@Getter
@Setter
public class PayableDTO {

    private Integer paymentId;

    @NotNull
    private Integer amountPaid;

    @NotNull
    private Integer invoiceId;

    private OffsetDateTime dateCreated;

    @NotNull
    private UUID ownerId;

}
