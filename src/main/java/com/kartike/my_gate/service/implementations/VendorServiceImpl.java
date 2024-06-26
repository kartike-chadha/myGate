package com.kartike.my_gate.service.implementations;

import com.kartike.my_gate.domain.Amenity;
import com.kartike.my_gate.domain.Vendor;
import com.kartike.my_gate.model.VendorDTO;
import com.kartike.my_gate.repos.AmenityRepository;
import com.kartike.my_gate.repos.GateLogRepository;
import com.kartike.my_gate.repos.UserStateRepository;
import com.kartike.my_gate.repos.VendorRepository;
import com.kartike.my_gate.service.interfaces.VendorService;
import com.kartike.my_gate.util.NotFoundException;
import com.kartike.my_gate.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final AmenityRepository amenityRepository;
    private final UserStateRepository userStateRepository;
    private final GateLogRepository gateLogRepository;

    public VendorServiceImpl(final VendorRepository vendorRepository,
                             final AmenityRepository amenityRepository,
                             final UserStateRepository userStateRepository,
                             final GateLogRepository gateLogRepository) {
        this.vendorRepository = vendorRepository;
        this.amenityRepository = amenityRepository;
        this.userStateRepository = userStateRepository;
        this.gateLogRepository = gateLogRepository;
    }
    @Override
    public List<VendorDTO> findAll() {
        final List<Vendor> vendors = vendorRepository.findAll(Sort.by("vendorId"));
        return vendors.stream()
                .map(vendor -> mapToDTO(vendor, new VendorDTO()))
                .toList();
    }
    @Override
    public VendorDTO get(final UUID vendorId) {
        return vendorRepository.findById(vendorId)
                .map(vendor -> mapToDTO(vendor, new VendorDTO()))
                .orElseThrow(NotFoundException::new);
    }
    @Override
    public UUID create(final VendorDTO vendorDTO) {
        final Vendor vendor = new Vendor();
        mapToEntity(vendorDTO, vendor);
        return vendorRepository.save(vendor).getVendorId();
    }
    @Override
    public void update(final UUID vendorId, final VendorDTO vendorDTO) {
        final Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(()->new RuntimeException("Vendor Not Found"));
        mapToEntity(vendorDTO, vendor);
        vendorRepository.save(vendor);
    }
    @Override
    public void incrementRequest(final UUID vendorId){
        final Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(()-> new RuntimeException("Vendor Not found"));
        vendor.setTotalRequests(vendor.getTotalRequests()+1);
        vendorRepository.save(vendor);
    }
    @Override
    public void decrementRequest(final UUID vendorId){
        final Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(()-> new RuntimeException("Vendor Not found"));
        vendor.setTotalRequests(vendor.getTotalRequests()-1);
        vendorRepository.save(vendor);
    }

    @Override
    public void delete(final UUID vendorId) {
        vendorRepository.deleteById(vendorId);
    }

    private VendorDTO mapToDTO(final Vendor vendor, final VendorDTO vendorDTO) {
        vendorDTO.setVendorId(vendor.getVendorId());
        vendorDTO.setVendorName(vendor.getVendorName());
        Amenity amenity = amenityRepository.findById(vendor.getAmenity().getId())
                .orElseThrow(()->new RuntimeException(""));
        vendorDTO.setTotalRequests(vendor.getTotalRequests());
        vendorDTO.setAmenityId(amenity.getId());
        return vendorDTO;
    }

    private Vendor mapToEntity(final VendorDTO vendorDTO, final Vendor vendor) {
        vendor.setVendorName(vendorDTO.getVendorName());
        Amenity amenity = amenityRepository.findById(vendorDTO.getAmenityId())
                .orElseThrow(()->new RuntimeException("Amenity Not Found"));
        vendor.setTotalRequests((vendorDTO.getTotalRequests()>0)? vendor.getTotalRequests() : 0);
        vendor.setAmenity(amenity);
        return vendor;
    }

    public boolean amenityExists(final Integer id) {
        return vendorRepository.existsByAmenityId(id);
    }

    public ReferencedWarning getReferencedWarning(final UUID vendorId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(NotFoundException::new);
        return null;
    }

}
