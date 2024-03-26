package com.kartike.my_gate.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;


@Getter
@Setter
public class AdminDTO {

    private UUID adminId;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    private Long bankAccount;

}
