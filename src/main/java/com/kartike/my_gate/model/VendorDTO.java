package com.kartike.my_gate.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class VendorDTO {

    private UUID vendorId;

    @NotNull
    @Size(max = 255)
    private String vendorName;

}
