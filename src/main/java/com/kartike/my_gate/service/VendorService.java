package com.kartike.my_gate.service;

import com.kartike.my_gate.domain.Amenity;
import com.kartike.my_gate.domain.GateLog;
import com.kartike.my_gate.domain.UserState;
import com.kartike.my_gate.domain.Vendor;
import com.kartike.my_gate.model.VendorDTO;
import com.kartike.my_gate.repos.AmenityRepository;
import com.kartike.my_gate.repos.GateLogRepository;
import com.kartike.my_gate.repos.UserStateRepository;
import com.kartike.my_gate.repos.VendorRepository;
import com.kartike.my_gate.util.NotFoundException;
import com.kartike.my_gate.util.ReferencedWarning;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class VendorService {

    private final VendorRepository vendorRepository;
    private final AmenityRepository amenityRepository;
    private final UserStateRepository userStateRepository;
    private final GateLogRepository gateLogRepository;

    public VendorService(final VendorRepository vendorRepository,
            final AmenityRepository amenityRepository,
            final UserStateRepository userStateRepository,
            final GateLogRepository gateLogRepository) {
        this.vendorRepository = vendorRepository;
        this.amenityRepository = amenityRepository;
        this.userStateRepository = userStateRepository;
        this.gateLogRepository = gateLogRepository;
    }

    public List<VendorDTO> findAll() {
        final List<Vendor> vendors = vendorRepository.findAll(Sort.by("vendorId"));
        return vendors.stream()
                .map(vendor -> mapToDTO(vendor, new VendorDTO()))
                .toList();
    }

    public VendorDTO get(final UUID vendorId) {
        return vendorRepository.findById(vendorId)
                .map(vendor -> mapToDTO(vendor, new VendorDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final VendorDTO vendorDTO) {
        final Vendor vendor = new Vendor();
        mapToEntity(vendorDTO, vendor);
        return vendorRepository.save(vendor).getVendorId();
    }

    public void update(final UUID vendorId, final VendorDTO vendorDTO) {
        final Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(vendorDTO, vendor);
        vendorRepository.save(vendor);
    }

    public void delete(final UUID vendorId) {
        vendorRepository.deleteById(vendorId);
    }

    private VendorDTO mapToDTO(final Vendor vendor, final VendorDTO vendorDTO) {
        vendorDTO.setVendorId(vendor.getVendorId());
        vendorDTO.setVendorName(vendor.getVendorName());
        vendorDTO.setAmenityId(vendor.getAmenityId());
        vendorDTO.setAmenity(vendor.getAmenity() == null ? null : vendor.getAmenity().getId());
        return vendorDTO;
    }

    private Vendor mapToEntity(final VendorDTO vendorDTO, final Vendor vendor) {
        vendor.setVendorName(vendorDTO.getVendorName());
        vendor.setAmenityId(vendorDTO.getAmenityId());
        final Amenity amenity = vendorDTO.getAmenity() == null ? null : amenityRepository.findById(vendorDTO.getAmenity())
                .orElseThrow(() -> new NotFoundException("amenity not found"));
        vendor.setAmenity(amenity);
        return vendor;
    }

    public boolean amenityExists(final Long id) {
        return vendorRepository.existsByAmenityId(id);
    }

    public ReferencedWarning getReferencedWarning(final UUID vendorId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(NotFoundException::new);
        final UserState userUserState = userStateRepository.findFirstByUser(vendor);
        if (userUserState != null) {
            referencedWarning.setKey("vendor.userState.user.referenced");
            referencedWarning.addParam(userUserState.getStateId());
            return referencedWarning;
        }
        final GateLog userGateLog = gateLogRepository.findFirstByUser(vendor);
        if (userGateLog != null) {
            referencedWarning.setKey("vendor.gateLog.user.referenced");
            referencedWarning.addParam(userGateLog.getLogId());
            return referencedWarning;
        }
        return null;
    }

}
