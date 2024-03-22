package com.kartike.my_gate.service;

import com.kartike.my_gate.domain.Amenity;
import com.kartike.my_gate.domain.AmenityRequest;
import com.kartike.my_gate.domain.GateLog;
import com.kartike.my_gate.domain.Invoice;
import com.kartike.my_gate.domain.Layout;
import com.kartike.my_gate.domain.Owner;
import com.kartike.my_gate.domain.UserState;
import com.kartike.my_gate.model.OwnerDTO;
import com.kartike.my_gate.repos.AmenityRepository;
import com.kartike.my_gate.repos.AmenityRequestRepository;
import com.kartike.my_gate.repos.GateLogRepository;
import com.kartike.my_gate.repos.InvoiceRepository;
import com.kartike.my_gate.repos.LayoutRepository;
import com.kartike.my_gate.repos.OwnerRepository;
import com.kartike.my_gate.repos.UserStateRepository;
import com.kartike.my_gate.util.NotFoundException;
import com.kartike.my_gate.util.ReferencedWarning;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final InvoiceRepository invoiceRepository;
    private final UserStateRepository userStateRepository;
    private final GateLogRepository gateLogRepository;
    private final LayoutRepository layoutRepository;
    private final AmenityRequestRepository amenityRequestRepository;
    private final AmenityRepository amenityRepository;

    public OwnerServiceImpl(final OwnerRepository ownerRepository,
                            final InvoiceRepository invoiceRepository,
                            final UserStateRepository userStateRepository,
                            final GateLogRepository gateLogRepository, final LayoutRepository layoutRepository,
                            final AmenityRequestRepository amenityRequestRepository,
                            final AmenityRepository amenityRepository) {
        this.ownerRepository = ownerRepository;
        this.invoiceRepository = invoiceRepository;
        this.userStateRepository = userStateRepository;
        this.gateLogRepository = gateLogRepository;
        this.layoutRepository = layoutRepository;
        this.amenityRequestRepository = amenityRequestRepository;
        this.amenityRepository = amenityRepository;
    }
    @Override
    public List<OwnerDTO> findAll() {
        final List<Owner> owners = ownerRepository.findAll(Sort.by("id"));
        return owners.stream()
                .map(owner -> mapToDTO(owner, new OwnerDTO()))
                .toList();
    }
    @Override
    public OwnerDTO get(final UUID id) {
        return ownerRepository.findById(id)
                .map(owner -> mapToDTO(owner, new OwnerDTO()))
                .orElseThrow(NotFoundException::new);
    }
    @Override
    public UUID create(final OwnerDTO ownerDTO) {
        final Owner owner = new Owner();
        mapToEntity(ownerDTO, owner);
        return ownerRepository.save(owner).getId();
    }
    @Override
    public void update(final UUID id, final OwnerDTO ownerDTO) {
        final Owner owner = ownerRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(ownerDTO, owner);
        ownerRepository.save(owner);
    }
    @Override
    public void delete(final UUID id) {
        ownerRepository.deleteById(id);
    }

    private OwnerDTO mapToDTO(final Owner owner, final OwnerDTO ownerDTO) {
        ownerDTO.setId(owner.getId());
        ownerDTO.setName(owner.getName());
        ownerDTO.setAddress(owner.getAddress());
        ownerDTO.setEmail(owner.getEmail());
        ownerDTO.setBankAccount(owner.getBankAccount());
        return ownerDTO;
    }

    private Owner mapToEntity(final OwnerDTO ownerDTO, final Owner owner) {
        owner.setName(ownerDTO.getName());
        owner.setAddress(ownerDTO.getAddress());
        owner.setEmail(ownerDTO.getEmail());
        owner.setBankAccount(ownerDTO.getBankAccount());
        return owner;
    }

    public boolean emailExists(final String email) {
        return ownerRepository.existsByEmailIgnoreCase(email);
    }

    public ReferencedWarning getReferencedWarning(final UUID id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Owner owner = ownerRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Invoice ownerInvoice = invoiceRepository.findFirstByOwner(owner);
        if (ownerInvoice != null) {
            referencedWarning.setKey("owner.invoice.owner.referenced");
            referencedWarning.addParam(ownerInvoice.getInvoiceId());
            return referencedWarning;
        }
        final UserState userUserState = userStateRepository.findFirstByOwner(owner);
        if (userUserState != null) {
            referencedWarning.setKey("owner.userState.user.referenced");
            referencedWarning.addParam(userUserState.getStateId());
            return referencedWarning;
        }
        final GateLog userGateLog = gateLogRepository.findFirstByOwner(owner);
        if (userGateLog != null) {
            referencedWarning.setKey("owner.gateLog.user.referenced");
            referencedWarning.addParam(userGateLog.getLogId());
            return referencedWarning;
        }
        final Layout ownerLayout = layoutRepository.findFirstByOwner(owner);
        if (ownerLayout != null) {
            referencedWarning.setKey("owner.layout.owner.referenced");
            referencedWarning.addParam(ownerLayout.getId());
            return referencedWarning;
        }
        final AmenityRequest ownerAmenityRequest = amenityRequestRepository.findFirstByOwner(owner);
        if (ownerAmenityRequest != null) {
            referencedWarning.setKey("owner.amenityRequest.owner.referenced");
            referencedWarning.addParam(ownerAmenityRequest.getRequestId());
            return referencedWarning;
        }

        return null;
    }

}
