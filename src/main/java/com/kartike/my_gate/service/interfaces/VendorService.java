package com.kartike.my_gate.service.interfaces;

import com.kartike.my_gate.model.VendorDTO;

import java.util.List;
import java.util.UUID;

public interface VendorService {

    public List<VendorDTO> findAll();
    public VendorDTO get(UUID vendorId);
    public UUID create(VendorDTO vendorDTO);
    public void update(UUID venddrId, VendorDTO vendorDTO);

    public void incrementRequest(UUID vendorId);

    public void decrementRequest(UUID vendorId);

    public void delete(UUID vendorId);

}
