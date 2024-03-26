package com.kartike.my_gate.rest;

import com.kartike.my_gate.model.VendorDTO;
import com.kartike.my_gate.service.VendorServiceImpl;
import com.kartike.my_gate.util.ReferencedException;
import com.kartike.my_gate.util.ReferencedWarning;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping(value = "/api/vendors", produces = MediaType.APPLICATION_JSON_VALUE)
public class VendorResource {

    private final VendorServiceImpl vendorService;

    public VendorResource(final VendorServiceImpl vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping
    public ResponseEntity<List<VendorDTO>> getAllVendors() {
        return ResponseEntity.ok(vendorService.findAll());
    }

    @GetMapping("/{vendorId}")
    public ResponseEntity<VendorDTO> getVendor(
            @PathVariable(name = "vendorId") final UUID vendorId) {
        return ResponseEntity.ok(vendorService.get(vendorId));
    }

    @PostMapping
    public ResponseEntity<UUID> createVendor(@RequestBody @Valid final VendorDTO vendorDTO) {
        final UUID createdVendorId = vendorService.create(vendorDTO);
        return new ResponseEntity<>(createdVendorId, HttpStatus.CREATED);
    }

    @PutMapping("/{vendorId}")
    public ResponseEntity<UUID> updateVendor(@PathVariable(name = "vendorId") final UUID vendorId,
            @RequestBody @Valid final VendorDTO vendorDTO) {
        vendorService.update(vendorId, vendorDTO);
        return ResponseEntity.ok(vendorId);
    }

    @DeleteMapping("/{vendorId}")
    public ResponseEntity<Void> deleteVendor(@PathVariable(name = "vendorId") final UUID vendorId) {
        final ReferencedWarning referencedWarning = vendorService.getReferencedWarning(vendorId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        vendorService.delete(vendorId);
        return ResponseEntity.noContent().build();
    }

}
