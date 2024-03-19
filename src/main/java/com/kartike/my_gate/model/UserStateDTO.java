package com.kartike.my_gate.model;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserStateDTO {

    private Integer stateId;

    @NotNull
    private UUID ownerId;

    @NotNull
    private UserStateEnum state;

    private Integer user;

    private UUID user;

}
