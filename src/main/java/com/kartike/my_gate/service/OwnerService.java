package com.kartike.my_gate.service;

import com.kartike.my_gate.model.OwnerDTO;

import java.util.List;

public interface OwnerService {

    public List<OwnerDTO> findAll();
    public OwnerDTO get(Integer id);
    public Integer create(OwnerDTO ownerDTO);
    public void update(Integer id, OwnerDTO ownerDTO);
    public void delete(Integer id);
}
