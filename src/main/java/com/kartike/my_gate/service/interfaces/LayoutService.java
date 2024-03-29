package com.kartike.my_gate.service.interfaces;

import com.kartike.my_gate.model.LayoutDTO;

import java.util.List;

public interface LayoutService {

    public List<LayoutDTO> findAll();
    public LayoutDTO get(Integer id);
    public Integer createAndAssignLayout(LayoutDTO layoutDTO);

    public void update(Integer id, LayoutDTO layoutDTO);
    public void delete(Integer id);
}
