package com.kartike.my_gate.model;

import com.kartike.my_gate.enums.LogTypeEnum;
import com.kartike.my_gate.enums.UserTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;


@Getter
@Setter
public class GateLogDTO {

    private Integer logId;

    @NotNull
//    @Size(max = 255)
    private Integer houseId;

    @NotNull
    private UserTypeEnum userType;

    @NotNull
    private LogTypeEnum logType;


    private OffsetDateTime logTime;

    @NotNull
    private UUID userId;
}
