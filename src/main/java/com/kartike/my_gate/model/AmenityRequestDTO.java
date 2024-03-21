package com.kartike.my_gate.model;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AmenityRequestDTO {

    private Integer requestId;

    @NotNull
    private OffsetDateTime dateCreated;

    @NotNull
    private OffsetDateTime eta;

    @NotNull
    private OffsetDateTime scheduledDate;

    @NotNull
    private RequestStatusEnum requestStatus;


}
