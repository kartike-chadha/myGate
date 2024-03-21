package com.kartike.my_gate.service;

import com.kartike.my_gate.model.AmenityDTO;

import java.util.List;

public interface AmenityService {
    public List<AmenityDTO> findAll();
    public AmenityDTO get(Long id);
    public Long create(AmenityDTO amenityDTO);
    public void update(Long id, AmenityDTO amenityDTO);
    public void delete(Long id);
}
