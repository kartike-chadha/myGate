package com.kartike.my_gate.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class OwnerDTO {

    private UUID id;

    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String address;

    @NotNull
    @Size(max = 255)
    private String email;

    @NotNull
    private Long bankAccount;

}
