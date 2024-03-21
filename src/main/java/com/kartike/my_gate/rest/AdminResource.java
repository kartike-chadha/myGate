package com.kartike.my_gate.rest;

import com.kartike.my_gate.model.AdminDTO;
import com.kartike.my_gate.service.AdminServiceImpl;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/admins", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminResource {

    private final AdminServiceImpl adminService;

    public AdminResource(final AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public ResponseEntity<List<AdminDTO>> getAllAdmins() {
        return ResponseEntity.ok(adminService.findAll());
    }

    @GetMapping("/{adminId}")
    public ResponseEntity<AdminDTO> getAdmin(@PathVariable(name = "adminId") final UUID adminId) {
        return ResponseEntity.ok(adminService.get(adminId));
    }

    @PostMapping
    public ResponseEntity<UUID> createAdmin(@RequestBody @Valid final AdminDTO adminDTO) {
        final UUID createdAdminId = adminService.create(adminDTO);
        return new ResponseEntity<>(createdAdminId, HttpStatus.CREATED);
    }

    @PutMapping("/{adminId}")
    public ResponseEntity<UUID> updateAdmin(@PathVariable(name = "adminId") final UUID adminId,
            @RequestBody @Valid final AdminDTO adminDTO) {
        adminService.update(adminId, adminDTO);
        return ResponseEntity.ok(adminId);
    }

    @DeleteMapping("/{adminId}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable(name = "adminId") final UUID adminId) {
        adminService.delete(adminId);
        return ResponseEntity.noContent().build();
    }



}
