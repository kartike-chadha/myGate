package com.kartike.my_gate.service;

import com.kartike.my_gate.domain.Amenity;
import com.kartike.my_gate.domain.AmenityRequest;
import com.kartike.my_gate.domain.Owner;
import com.kartike.my_gate.domain.Vendor;
import com.kartike.my_gate.model.AmenityDTO;
import com.kartike.my_gate.repos.AmenityRepository;
import com.kartike.my_gate.repos.AmenityRequestRepository;
import com.kartike.my_gate.repos.OwnerRepository;
import com.kartike.my_gate.repos.VendorRepository;
import com.kartike.my_gate.util.NotFoundException;
import com.kartike.my_gate.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AmenityService {

    private final AmenityRepository amenityRepository;
    private final OwnerRepository ownerRepository;
    private final VendorRepository vendorRepository;
    private final AmenityRequestRepository amenityRequestRepository;

    public AmenityService(final AmenityRepository amenityRepository,
            final OwnerRepository ownerRepository, final VendorRepository vendorRepository,
            final AmenityRequestRepository amenityRequestRepository) {
        this.amenityRepository = amenityRepository;
        this.ownerRepository = ownerRepository;
        this.vendorRepository = vendorRepository;
        this.amenityRequestRepository = amenityRequestRepository;
    }

    public List<AmenityDTO> findAll() {
        final List<Amenity> amenities = amenityRepository.findAll(Sort.by("id"));
        return amenities.stream()
                .map(amenity -> mapToDTO(amenity, new AmenityDTO()))
                .toList();
    }

    public AmenityDTO get(final Long id) {
        return amenityRepository.findById(id)
                .map(amenity -> mapToDTO(amenity, new AmenityDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final AmenityDTO amenityDTO) {
        final Amenity amenity = new Amenity();
        mapToEntity(amenityDTO, amenity);
        return amenityRepository.save(amenity).getId();
    }

    public void update(final Long id, final AmenityDTO amenityDTO) {
        final Amenity amenity = amenityRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(amenityDTO, amenity);
        amenityRepository.save(amenity);
    }

    public void delete(final Long id) {
        amenityRepository.deleteById(id);
    }

    private AmenityDTO mapToDTO(final Amenity amenity, final AmenityDTO amenityDTO) {
        amenityDTO.setId(amenity.getId());
        amenityDTO.setAmenityName(amenity.getAmenityName());
        amenityDTO.setAmenity(amenity.getAmenity() == null ? null : amenity.getAmenity().getId());
        return amenityDTO;
    }

    private Amenity mapToEntity(final AmenityDTO amenityDTO, final Amenity amenity) {
        amenity.setAmenityName(amenityDTO.getAmenityName());
        final Owner amenity = amenityDTO.getAmenity() == null ? null : ownerRepository.findById(amenityDTO.getAmenity())
                .orElseThrow(() -> new NotFoundException("amenity not found"));
        amenity.setAmenity(amenity);
        return amenity;
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Amenity amenity = amenityRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Vendor amenityVendor = vendorRepository.findFirstByAmenity(amenity);
        if (amenityVendor != null) {
            referencedWarning.setKey("amenity.vendor.amenity.referenced");
            referencedWarning.addParam(amenityVendor.getVendorId());
            return referencedWarning;
        }
        final AmenityRequest requestedAmenityAmenityRequest = amenityRequestRepository.findFirstByRequestedAmenity(amenity);
        if (requestedAmenityAmenityRequest != null) {
            referencedWarning.setKey("amenity.amenityRequest.requestedAmenity.referenced");
            referencedWarning.addParam(requestedAmenityAmenityRequest.getRequestId());
            return referencedWarning;
        }
        return null;
    }

}
