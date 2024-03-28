package com.kartike.my_gate.rest;

import com.kartike.my_gate.model.AmenityDTO;
import com.kartike.my_gate.service.implementations.AmenityServiceImpl;
import com.kartike.my_gate.util.ReferencedException;
import com.kartike.my_gate.util.ReferencedWarning;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/amenities", produces = MediaType.APPLICATION_JSON_VALUE)
public class AmenityResource {

    private final AmenityServiceImpl amenityService;

    public AmenityResource(final AmenityServiceImpl amenityService) {
        this.amenityService = amenityService;
    }

    @GetMapping
    public ResponseEntity<List<AmenityDTO>> getAllAmenities() {
        return ResponseEntity.ok(amenityService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AmenityDTO> getAmenity(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(amenityService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createAmenity(@RequestBody @Valid final AmenityDTO amenityDTO) {
        final Integer createdId = amenityService.create(amenityDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateAmenity(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final AmenityDTO amenityDTO) {
        amenityService.update(id, amenityDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAmenity(@PathVariable(name = "id") final Integer id) {
        final ReferencedWarning referencedWarning = amenityService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        amenityService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
