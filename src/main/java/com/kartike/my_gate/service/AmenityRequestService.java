package com.kartike.my_gate.service;

import com.kartike.my_gate.domain.Amenity;
import com.kartike.my_gate.domain.AmenityRequest;
import com.kartike.my_gate.domain.Owner;
import com.kartike.my_gate.model.AmenityRequestDTO;
import com.kartike.my_gate.repos.AmenityRepository;
import com.kartike.my_gate.repos.AmenityRequestRepository;
import com.kartike.my_gate.repos.OwnerRepository;
import com.kartike.my_gate.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AmenityRequestService {

    private final AmenityRequestRepository amenityRequestRepository;
    private final AmenityRepository amenityRepository;
    private final OwnerRepository ownerRepository;

    public AmenityRequestService(final AmenityRequestRepository amenityRequestRepository,
            final AmenityRepository amenityRepository, final OwnerRepository ownerRepository) {
        this.amenityRequestRepository = amenityRequestRepository;
        this.amenityRepository = amenityRepository;
        this.ownerRepository = ownerRepository;
    }

    public List<AmenityRequestDTO> findAll() {
        final List<AmenityRequest> amenityRequests = amenityRequestRepository.findAll(Sort.by("requestId"));
        return amenityRequests.stream()
                .map(amenityRequest -> mapToDTO(amenityRequest, new AmenityRequestDTO()))
                .toList();
    }

    public AmenityRequestDTO get(final Integer requestId) {
        return amenityRequestRepository.findById(requestId)
                .map(amenityRequest -> mapToDTO(amenityRequest, new AmenityRequestDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final AmenityRequestDTO amenityRequestDTO) {
        final AmenityRequest amenityRequest = new AmenityRequest();
        mapToEntity(amenityRequestDTO, amenityRequest);
        return amenityRequestRepository.save(amenityRequest).getRequestId();
    }

    public void update(final Integer requestId, final AmenityRequestDTO amenityRequestDTO) {
        final AmenityRequest amenityRequest = amenityRequestRepository.findById(requestId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(amenityRequestDTO, amenityRequest);
        amenityRequestRepository.save(amenityRequest);
    }

    public void delete(final Integer requestId) {
        amenityRequestRepository.deleteById(requestId);
    }

    private AmenityRequestDTO mapToDTO(final AmenityRequest amenityRequest,
            final AmenityRequestDTO amenityRequestDTO) {
        amenityRequestDTO.setRequestId(amenityRequest.getRequestId());
        amenityRequestDTO.setOwnerId(amenityRequest.getOwnerId());
        amenityRequestDTO.setAmenityRequested(amenityRequest.getAmenityRequested());
        amenityRequestDTO.setDateCreated(amenityRequest.getDateCreated());
        amenityRequestDTO.setEta(amenityRequest.getEta());
        amenityRequestDTO.setScheduledDate(amenityRequest.getScheduledDate());
        amenityRequestDTO.setRequestStatus(amenityRequest.getRequestStatus());
        amenityRequestDTO.setRequestedAmenity(amenityRequest.getRequestedAmenity() == null ? null : amenityRequest.getRequestedAmenity().getId());
        amenityRequestDTO.setOwner(amenityRequest.getOwner() == null ? null : amenityRequest.getOwner().getId());
        return amenityRequestDTO;
    }

    private AmenityRequest mapToEntity(final AmenityRequestDTO amenityRequestDTO,
            final AmenityRequest amenityRequest) {
        amenityRequest.setOwnerId(amenityRequestDTO.getOwnerId());
        amenityRequest.setAmenityRequested(amenityRequestDTO.getAmenityRequested());
        amenityRequest.setDateCreated(amenityRequestDTO.getDateCreated());
        amenityRequest.setEta(amenityRequestDTO.getEta());
        amenityRequest.setScheduledDate(amenityRequestDTO.getScheduledDate());
        amenityRequest.setRequestStatus(amenityRequestDTO.getRequestStatus());
        final Amenity requestedAmenity = amenityRequestDTO.getRequestedAmenity() == null ? null : amenityRepository.findById(amenityRequestDTO.getRequestedAmenity())
                .orElseThrow(() -> new NotFoundException("requestedAmenity not found"));
        amenityRequest.setRequestedAmenity(requestedAmenity);
        final Owner owner = amenityRequestDTO.getOwner() == null ? null : ownerRepository.findById(amenityRequestDTO.getOwner())
                .orElseThrow(() -> new NotFoundException("owner not found"));
        amenityRequest.setOwner(owner);
        return amenityRequest;
    }

}
