package com.kartike.my_gate.model;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PayableDTO {

    private Integer paymentId;

    @NotNull
    private UUID ownerId;

    @NotNull
    private Integer amountPaid;

    @NotNull
    private Integer invoiceId;

    private Integer invoice;

}
