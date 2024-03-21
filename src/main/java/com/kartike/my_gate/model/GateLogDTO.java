package com.kartike.my_gate.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GateLogDTO {

    private Integer logId;

    @NotNull
    @Size(max = 255)
    private String houseId;

    @NotNull
    private UserStateEnum userType;

    @NotNull
    private LogTypeEnum logType;

    @NotNull
    private OffsetDateTime logTime;
}
