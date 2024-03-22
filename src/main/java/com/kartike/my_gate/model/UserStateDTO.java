package com.kartike.my_gate.model;

import com.kartike.my_gate.enums.UserStateEnum;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserStateDTO {

    private Integer stateId;

    @NotNull
    private UserStateEnum state;

    private UUID ownerId;

    private UUID vendorId;


}
