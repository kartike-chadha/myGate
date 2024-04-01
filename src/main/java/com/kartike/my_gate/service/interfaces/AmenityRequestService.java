package com.kartike.my_gate.service.interfaces;

import com.kartike.my_gate.model.AmenityRequestDTO;

import java.util.List;

public interface AmenityRequestService {
    public List<AmenityRequestDTO> findAll();
    public AmenityRequestDTO get(Integer requestId);
    public Integer create(AmenityRequestDTO amenityRequestDTO);
    public void update(Integer requestId, AmenityRequestDTO amenityRequestDTO);

    void markRequestCompleted(Integer amenityRequestId);

    public void delete(Integer requestId);
}
