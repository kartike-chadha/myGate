package com.kartike.my_gate.service;

import com.kartike.my_gate.model.AdminDTO;
import com.kartike.my_gate.model.AmenityRequestDTO;

import java.util.List;
import java.util.UUID;

public interface AmenityRequestService {
    public List<AmenityRequestDTO> findAll();
    public AmenityRequestDTO get(Integer requestId);
    public Integer create(AmenityRequestDTO amenityRequestDTO);
    public void update(Integer requestId, AmenityRequestDTO amenityRequestDTO);
    public void delete(Integer requestId);
}
