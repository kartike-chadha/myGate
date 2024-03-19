package com.kartike.my_gate.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BlockDTO {

    private Integer blockId;

    @NotNull
    @Size(max = 255)
    private String blockName;

}
