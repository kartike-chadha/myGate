package com.kartike.my_gate.service;

import com.kartike.my_gate.model.VendorDTO;

import java.util.List;
import java.util.UUID;

public interface VendorService {

    public List<VendorDTO> findAll();
    public VendorDTO get(UUID vendorId);
    public UUID create(VendorDTO vendorDTO);
    public void update(UUID venddrId, VendorDTO vendorDTO);
    public void delete(UUID vendorId);

}
