package com.kartike.my_gate.model;

import com.kartike.my_gate.enums.RequestStatusEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;


@Getter
@Setter
public class AmenityRequestDTO {

    private Integer requestId;

    @NotNull
    private Integer requestedAmenity;

    @NotNull
    private UUID ownerId;

    @NotNull
    private RequestStatusEnum requestStatus;

    private OffsetDateTime dateCreated;

    private OffsetDateTime eta;

    private OffsetDateTime scheduledDate;
}
