package com.kartike.my_gate.rest;

import com.kartike.my_gate.model.AmenityRequestDTO;
import com.kartike.my_gate.service.implementations.AmenityRequestServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(value = "/api/amenityRequests", produces = MediaType.APPLICATION_JSON_VALUE)
public class AmenityRequestResource {

    private final AmenityRequestServiceImpl amenityRequestService;

    public AmenityRequestResource(final AmenityRequestServiceImpl amenityRequestService) {
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
