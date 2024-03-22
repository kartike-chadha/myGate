package com.kartike.my_gate.service;

import com.kartike.my_gate.model.OwnerDTO;

import java.util.List;
import java.util.UUID;

public interface OwnerService {

    public List<OwnerDTO> findAll();
    public OwnerDTO get(UUID id);
    public UUID create(OwnerDTO ownerDTO);
    public void update(UUID id, OwnerDTO ownerDTO);
    public void delete(UUID id);
}
