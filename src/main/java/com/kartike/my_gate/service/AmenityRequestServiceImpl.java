package com.kartike.my_gate.service;

import com.kartike.my_gate.domain.AmenityRequest;
import com.kartike.my_gate.model.AmenityRequestDTO;
import com.kartike.my_gate.repos.AmenityRepository;
import com.kartike.my_gate.repos.AmenityRequestRepository;
import com.kartike.my_gate.repos.OwnerRepository;
import com.kartike.my_gate.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AmenityRequestServiceImpl implements AmenityRequestService{

    private final AmenityRequestRepository amenityRequestRepository;
    private final AmenityRepository amenityRepository;
    private final OwnerRepository ownerRepository;

    public AmenityRequestServiceImpl(final AmenityRequestRepository amenityRequestRepository,
                                     final AmenityRepository amenityRepository, final OwnerRepository ownerRepository) {
        this.amenityRequestRepository = amenityRequestRepository;
        this.amenityRepository = amenityRepository;
        this.ownerRepository = ownerRepository;
    }

    @Override
    public List<AmenityRequestDTO> findAll() {
        final List<AmenityRequest> amenityRequests = amenityRequestRepository.findAll(Sort.by("requestId"));
        return amenityRequests.stream()
                .map(amenityRequest -> mapToDTO(amenityRequest, new AmenityRequestDTO()))
                .toList();
    }
    @Override
    public AmenityRequestDTO get(final Integer requestId) {
        return amenityRequestRepository.findById(requestId)
                .map(amenityRequest -> mapToDTO(amenityRequest, new AmenityRequestDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Integer create(final AmenityRequestDTO amenityRequestDTO) {
        final AmenityRequest amenityRequest = new AmenityRequest();
        mapToEntity(amenityRequestDTO, amenityRequest);
        return amenityRequestRepository.save(amenityRequest).getRequestId();
    }
    @Override
    public void update(final Integer requestId, final AmenityRequestDTO amenityRequestDTO) {
        final AmenityRequest amenityRequest = amenityRequestRepository.findById(requestId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(amenityRequestDTO, amenityRequest);
        amenityRequestRepository.save(amenityRequest);
    }
    @Override
    public void delete(final Integer requestId) {
        amenityRequestRepository.deleteById(requestId);
    }

    private AmenityRequestDTO mapToDTO(final AmenityRequest amenityRequest,
            final AmenityRequestDTO amenityRequestDTO) {
        amenityRequestDTO.setRequestId(amenityRequest.getRequestId());
        amenityRequestDTO.setDateCreated(amenityRequest.getDateCreated());
        amenityRequestDTO.setEta(amenityRequest.getEta());
        amenityRequestDTO.setScheduledDate(amenityRequest.getScheduledDate());
        amenityRequestDTO.setRequestStatus(amenityRequest.getRequestStatus());
        return amenityRequestDTO;
    }

    private AmenityRequest mapToEntity(final AmenityRequestDTO amenityRequestDTO,
            final AmenityRequest amenityRequest) {
        amenityRequest.setDateCreated(amenityRequestDTO.getDateCreated());
        amenityRequest.setEta(amenityRequestDTO.getEta());
        amenityRequest.setScheduledDate(amenityRequestDTO.getScheduledDate());
        amenityRequest.setRequestStatus(amenityRequestDTO.getRequestStatus());
        return amenityRequest;
    }

}
