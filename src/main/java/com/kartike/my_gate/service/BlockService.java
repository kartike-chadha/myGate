package com.kartike.my_gate.service;

import com.kartike.my_gate.model.BlockDTO;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public interface BlockService {

    public List<BlockDTO> findAll();
    public BlockDTO get(Integer blockId);
    public Integer create(BlockDTO blockDTO);
    public void update(Integer blockId, BlockDTO blockDTO);
    public void delete(Integer blockId);
}
