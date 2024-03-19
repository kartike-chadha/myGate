package com.kartike.my_gate.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


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
