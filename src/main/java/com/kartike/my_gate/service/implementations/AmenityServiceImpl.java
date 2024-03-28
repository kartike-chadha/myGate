package com.kartike.my_gate.service.implementations;

import com.kartike.my_gate.domain.Amenity;
import com.kartike.my_gate.domain.AmenityRequest;
import com.kartike.my_gate.domain.Vendor;
import com.kartike.my_gate.model.AmenityDTO;
import com.kartike.my_gate.repos.AmenityRepository;
import com.kartike.my_gate.repos.AmenityRequestRepository;
import com.kartike.my_gate.repos.OwnerRepository;
import com.kartike.my_gate.repos.VendorRepository;
import com.kartike.my_gate.service.interfaces.AmenityService;
import com.kartike.my_gate.util.NotFoundException;
import com.kartike.my_gate.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class AmenityServiceImpl implements AmenityService {

    private final AmenityRepository amenityRepository;
    private final OwnerRepository ownerRepository;
    private final VendorRepository vendorRepository;
    private final AmenityRequestRepository amenityRequestRepository;

    public AmenityServiceImpl(final AmenityRepository amenityRepository,
                              final OwnerRepository ownerRepository, final VendorRepository vendorRepository,
                              final AmenityRequestRepository amenityRequestRepository) {
        this.amenityRepository = amenityRepository;
        this.ownerRepository = ownerRepository;
        this.vendorRepository = vendorRepository;
        this.amenityRequestRepository = amenityRequestRepository;
    }
    @Override
    public List<AmenityDTO> findAll() {
        final List<Amenity> amenities = amenityRepository.findAll(Sort.by("id"));
        return amenities.stream()
                .map(amenity -> mapToDTO(amenity, new AmenityDTO()))
                .toList();
    }
    @Override
    public AmenityDTO get(final Integer id) {
        return amenityRepository.findById(id)
                .map(amenity -> mapToDTO(amenity, new AmenityDTO()))
                .orElseThrow(NotFoundException::new);
    }
    @Override
    public Integer create(final AmenityDTO amenityDTO) {
        final Amenity amenity = new Amenity();
        mapToEntity(amenityDTO, amenity);
        return amenityRepository.save(amenity).getId();
    }
    @Override
    public void update(final Integer id, final AmenityDTO amenityDTO) {
        final Amenity amenity = amenityRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(amenityDTO, amenity);
        amenityRepository.save(amenity);
    }
    @Override
    public void delete(final Integer id) {
        amenityRepository.deleteById(id);
    }

    private AmenityDTO mapToDTO(final Amenity amenity, final AmenityDTO amenityDTO) {
        amenityDTO.setId(amenity.getId());
        amenityDTO.setAmenityName(amenity.getAmenityName());
        return amenityDTO;
    }

    private Amenity mapToEntity(final AmenityDTO amenityDTO, final Amenity amenity) {
        amenity.setAmenityName(amenityDTO.getAmenityName());
        return amenity;
    }

    public ReferencedWarning getReferencedWarning(final Integer id) {
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
