package com.kartike.my_gate.service.interfaces;

import com.kartike.my_gate.domain.Admin;
import com.kartike.my_gate.model.AdminDTO;

import java.util.List;
import java.util.UUID;

public interface AdminService {

    public List<AdminDTO> findAll();
    public AdminDTO get(UUID adminId);
    public UUID create(AdminDTO adminDTO);
    public void update(UUID adminId, AdminDTO adminDTO);
    public void delete(UUID adminId);



}
