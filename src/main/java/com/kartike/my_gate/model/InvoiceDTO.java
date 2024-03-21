package com.kartike.my_gate.model;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class InvoiceDTO {

    private Integer invoiceId;

    @NotNull
    private Integer amount;

    @NotNull
    private OffsetDateTime dateCreated;

    @NotNull
    private OffsetDateTime datePayable;

}
