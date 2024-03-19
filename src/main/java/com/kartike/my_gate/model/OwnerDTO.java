package com.kartike.my_gate.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OwnerDTO {

    private Integer id;

    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String address;

    @NotNull
    @Size(max = 255)
    @OwnerEmailUnique
    private String email;

    @NotNull
    private Long bankAccount;

}
