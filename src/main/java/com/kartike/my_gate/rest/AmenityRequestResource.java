package com.kartike.my_gate.rest;

import com.kartike.my_gate.model.AmenityRequestDTO;
import com.kartike.my_gate.service.AmenityRequestService;
import jakarta.validation.Valid;
import java.util.List;
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
@RequestMapping(value = "/api/amenityRequests", produces = MediaType.APPLICATION_JSON_VALUE)
public class AmenityRequestResource {

    private final AmenityRequestService amenityRequestService;

    public AmenityRequestResource(final AmenityRequestService amenityRequestService) {
        this.amenityRequestService = amenityRequestService;
    }

    @GetMapping
    public ResponseEntity<List<AmenityRequestDTO>> getAllAmenityRequests() {
        return ResponseEntity.ok(amenityRequestService.findAll());
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<AmenityRequestDTO> getAmenityRequest(
            @PathVariable(name = "requestId") final Integer requestId) {
        return ResponseEntity.ok(amenityRequestService.get(requestId));
    }

    @PostMapping
    public ResponseEntity<Integer> createAmenityRequest(
            @RequestBody @Valid final AmenityRequestDTO amenityRequestDTO) {
        final Integer createdRequestId = amenityRequestService.create(amenityRequestDTO);
        return new ResponseEntity<>(createdRequestId, HttpStatus.CREATED);
    }

    @PutMapping("/{requestId}")
    public ResponseEntity<Integer> updateAmenityRequest(
            @PathVariable(name = "requestId") final Integer requestId,
            @RequestBody @Valid final AmenityRequestDTO amenityRequestDTO) {
        amenityRequestService.update(requestId, amenityRequestDTO);
        return ResponseEntity.ok(requestId);
    }

    @DeleteMapping("/{requestId}")
    public ResponseEntity<Void> deleteAmenityRequest(
            @PathVariable(name = "requestId") final Integer requestId) {
        amenityRequestService.delete(requestId);
        return ResponseEntity.noContent().build();
    }

}
