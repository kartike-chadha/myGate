package com.kartike.my_gate.service.implementations;

import com.kartike.my_gate.domain.Amenity;
import com.kartike.my_gate.domain.AmenityRequest;
import com.kartike.my_gate.enums.RequestStatusEnum;
import com.kartike.my_gate.model.AmenityRequestDTO;
import com.kartike.my_gate.repos.AmenityRepository;
import com.kartike.my_gate.repos.AmenityRequestRepository;
import com.kartike.my_gate.repos.OwnerRepository;
import com.kartike.my_gate.repos.VendorRepository;
import com.kartike.my_gate.service.interfaces.AmenityRequestService;
import com.kartike.my_gate.service.interfaces.VendorService;
import com.kartike.my_gate.util.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;


@Service
@AllArgsConstructor
public class AmenityRequestServiceImpl implements AmenityRequestService {

    private final AmenityRequestRepository amenityRequestRepository;
    private final AmenityRepository amenityRepository;
    private final OwnerRepository ownerRepository;
    private final VendorRepository vendorRepository;
    private final VendorService vendorService;

//    public AmenityRequestServiceImpl(final AmenityRequestRepository amenityRequestRepository,
//                                     final AmenityRepository amenityRepository, final OwnerRepository ownerRepository, VendorService vendorService) {
//        this.amenityRequestRepository = amenityRequestRepository;
//        this.amenityRepository = amenityRepository;
//        this.ownerRepository = ownerRepository;
//        this.vendorService = vendorService;
//    }

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
        vendorService.incrementRequest(vendorRepository
                .findFirstByAmenity(
                        amenityRepository
                                .findById(amenityRequestDTO.getRequestedAmenity())
                .orElseThrow(()-> new RuntimeException("Amenity Not Found"))).getVendorId());
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
    public void markRequestCompleted(final Integer requestId){
        final AmenityRequest amenityRequest = amenityRequestRepository.findById(requestId)
                .orElseThrow(()-> new RuntimeException("Amenity Request Not Found"));
        if(!amenityRequest.getRequestStatus().equals(RequestStatusEnum.COMPLETED)){
            amenityRequest.setRequestStatus(RequestStatusEnum.COMPLETED);
        }
        amenityRequestRepository.save(amenityRequest);
    }

    @Override
    public void delete(final Integer requestId) {
        amenityRequestRepository.deleteById(requestId);
    }

//    private OffsetDateTime getEta(OffsetDateTime createdTime){}

    private AmenityRequestDTO mapToDTO(final AmenityRequest amenityRequest,
            final AmenityRequestDTO amenityRequestDTO) {
        amenityRequestDTO.setRequestId(amenityRequest.getRequestId());
        amenityRequestDTO.setDateCreated(amenityRequest.getDateCreated());
//        amenityRequestDTO.setEta(amenityRequest.getEta());
        amenityRequestDTO.setRequestStatus(amenityRequest.getRequestStatus());
        amenityRequestDTO.setRequestedAmenity(amenityRequest.getRequestedAmenity().getId());
        amenityRequestDTO.setOwnerId(amenityRequest.getOwner().getId());
        return amenityRequestDTO;
    }

    private AmenityRequest mapToEntity(final AmenityRequestDTO amenityRequestDTO,
            final AmenityRequest amenityRequest) {
        amenityRequest.setDateCreated(OffsetDateTime.now());
//        amenityRequest.setEta(OffsetDateTime.now().plusDays(amenityRequestRepository.countByAmenityIdAndAmenityStatusNotEqualTo(
//                amenityRepository.findById(amenityRequestDTO.getRequestedAmenity())
//                        .orElseThrow(()->new RuntimeException("Amenity doesn't exist")),
//                RequestStatusEnum.COMPLETED
//        ).longValue()));
        amenityRequest.setRequestStatus(amenityRequestDTO.getRequestStatus());
        Amenity requestedAmenity = amenityRepository.findById(amenityRequestDTO.getRequestedAmenity())
                .orElseThrow(()-> new RuntimeException("Amenity not found"));
        amenityRequest.setRequestedAmenity(requestedAmenity);
        amenityRequest.setOwner(ownerRepository.findById(amenityRequestDTO.getOwnerId())
                .orElseThrow(()-> new RuntimeException("User Not found")));
        return amenityRequest;
    }

}
