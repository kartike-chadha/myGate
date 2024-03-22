package com.kartike.my_gate.model;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Constraint;
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
    private Integer houseNumber;

    @NotNull
    private Integer blockId;

    @NotNull
    private UUID ownerId;
}
