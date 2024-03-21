package com.kartike.my_gate.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LayoutDTO {

    @NotNull
    private Integer id;

    @NotNull
    private Integer houseNumber;

    @NotNull
    private String blockId;
}
