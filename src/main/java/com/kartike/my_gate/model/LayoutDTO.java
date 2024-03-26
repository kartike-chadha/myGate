package com.kartike.my_gate.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;


@Getter
@Setter
public class LayoutDTO {

    private Integer id;

    @NotNull
    private Integer houseNumber;

    @NotNull
    private Integer blockId;

    @NotNull
    private UUID ownerId;
}
