package com.kartike.my_gate.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {

    private Integer invoiceId;

    @NotNull
    private Integer amount;
    @NotNull
    private UUID ownerId;


    private OffsetDateTime dateCreated;

    private OffsetDateTime datePayable;

}
