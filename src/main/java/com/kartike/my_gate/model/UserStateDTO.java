package com.kartike.my_gate.model;

import com.kartike.my_gate.enums.LogTypeEnum;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserStateDTO {

    private Integer stateId;

    @NotNull
    private LogTypeEnum state;

    @NotNull
    private UUID userId;


}
