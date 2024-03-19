package com.kartike.my_gate.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LayoutDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String blockId;

    @NotNull
    private Integer houseNumber;

    @NotNull
    private UUID ownerId;

    private Integer block;

    private Integer owner;

}
