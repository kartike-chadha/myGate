package com.kartike.my_gate.rest;

import com.kartike.my_gate.model.OwnerDTO;
import com.kartike.my_gate.service.implementations.OwnerServiceImpl;
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
@RequestMapping(value = "/api/owners", produces = MediaType.APPLICATION_JSON_VALUE)
public class OwnerResource {

    private final OwnerServiceImpl ownerService;

    public OwnerResource(final OwnerServiceImpl ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping
    public ResponseEntity<List<OwnerDTO>> getAllOwners() {
        return ResponseEntity.ok(ownerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerDTO> getOwner(@PathVariable(name = "id") final UUID id) {
        return ResponseEntity.ok(ownerService.get(id));
    }

    @PostMapping
    public ResponseEntity<UUID> createOwner(@RequestBody @Valid final OwnerDTO ownerDTO) {
        final UUID createdId = ownerService.create(ownerDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UUID> updateOwner(@PathVariable(name = "id") final UUID id,
            @RequestBody @Valid final OwnerDTO ownerDTO) {
        ownerService.update(id, ownerDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable(name = "id") final UUID id) {
        final ReferencedWarning referencedWarning = ownerService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        ownerService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
