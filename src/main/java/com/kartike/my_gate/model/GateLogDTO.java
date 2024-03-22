package com.kartike.my_gate.model;

import com.kartike.my_gate.enums.LogTypeEnum;
import com.kartike.my_gate.enums.UserStateEnum;
import com.kartike.my_gate.enums.UserTypeEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.usertype.UserType;


@Getter
@Setter
public class GateLogDTO {

    private Integer logId;

    @NotNull
    @Size(max = 255)
    private String houseId;

    @NotNull
    private UserTypeEnum userType;

    @NotNull
    private LogTypeEnum logType;

    @NotNull
    private OffsetDateTime logTime;

    private UUID ownerId;

    private UUID vendorId;
}
