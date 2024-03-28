package com.kartike.my_gate.service.interfaces;

import com.kartike.my_gate.model.AmenityDTO;

import java.util.List;

public interface AmenityService {
    public List<AmenityDTO> findAll();
    public AmenityDTO get(Integer id);
    public Integer create(AmenityDTO amenityDTO);
    public void update(Integer id, AmenityDTO amenityDTO);
    public void delete(Integer id);
}
