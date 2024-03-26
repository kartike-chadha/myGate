package com.kartike.my_gate.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
public class BlockDTO {

    private Integer blockId;

    @NotNull
    @Size(max = 255)
    private String blockName;

}
