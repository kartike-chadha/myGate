package com.kartike.my_gate.service.implementations;

import com.kartike.my_gate.domain.Admin;
import com.kartike.my_gate.model.AdminDTO;
import com.kartike.my_gate.repos.AdminRepository;
import com.kartike.my_gate.service.interfaces.AdminService;
import com.kartike.my_gate.util.NotFoundException;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    public AdminServiceImpl(final AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public List<AdminDTO> findAll() {
        final List<Admin> admins = adminRepository.findAll(Sort.by("adminId"));
        return admins.stream()
                .map(admin -> mapToDTO(admin, new AdminDTO()))
                .toList();
    }
    @Override
    public AdminDTO get(final UUID adminId) {
        return adminRepository.findById(adminId)
                .map(admin -> mapToDTO(admin, new AdminDTO()))
                .orElseThrow(NotFoundException::new);
    }
    @Override
    public UUID create(final AdminDTO adminDTO) {
        final Admin admin = new Admin();
        mapToEntity(adminDTO, admin);
        return adminRepository.save(admin).getAdminId();
    }
    @Override
    public void update(final UUID adminId, final AdminDTO adminDTO) {
        final Admin admin = adminRepository.findById(adminId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(adminDTO, admin);
        adminRepository.save(admin);
    }
    @Override
    public void delete(final UUID adminId) {
        adminRepository.deleteById(adminId);
    }


    private AdminDTO mapToDTO(final Admin admin, final AdminDTO adminDTO) {
        adminDTO.setAdminId(admin.getAdminId());
        adminDTO.setName(admin.getName());
        adminDTO.setBankAccount(admin.getBankAccount());
        return adminDTO;
    }

    private Admin mapToEntity(final AdminDTO adminDTO, final Admin admin) {
        admin.setName(adminDTO.getName());
        admin.setBankAccount(adminDTO.getBankAccount());
        return admin;
    }

}
