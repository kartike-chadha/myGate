package com.kartike.my_gate.service;

import com.kartike.my_gate.domain.Block;
import com.kartike.my_gate.domain.GateLog;
import com.kartike.my_gate.domain.Layout;
import com.kartike.my_gate.domain.Owner;
import com.kartike.my_gate.model.LayoutDTO;
import com.kartike.my_gate.repos.BlockRepository;
import com.kartike.my_gate.repos.GateLogRepository;
import com.kartike.my_gate.repos.LayoutRepository;
import com.kartike.my_gate.repos.OwnerRepository;
import com.kartike.my_gate.util.NotFoundException;
import com.kartike.my_gate.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LayoutService {

    private final LayoutRepository layoutRepository;
    private final BlockRepository blockRepository;
    private final OwnerRepository ownerRepository;
    private final GateLogRepository gateLogRepository;

    public LayoutService(final LayoutRepository layoutRepository,
            final BlockRepository blockRepository, final OwnerRepository ownerRepository,
            final GateLogRepository gateLogRepository) {
        this.layoutRepository = layoutRepository;
        this.blockRepository = blockRepository;
        this.ownerRepository = ownerRepository;
        this.gateLogRepository = gateLogRepository;
    }

    public List<LayoutDTO> findAll() {
        final List<Layout> layouts = layoutRepository.findAll(Sort.by("id"));
        return layouts.stream()
                .map(layout -> mapToDTO(layout, new LayoutDTO()))
                .toList();
    }

    public LayoutDTO get(final Integer id) {
        return layoutRepository.findById(id)
                .map(layout -> mapToDTO(layout, new LayoutDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final LayoutDTO layoutDTO) {
        final Layout layout = new Layout();
        mapToEntity(layoutDTO, layout);
        return layoutRepository.save(layout).getId();
    }

    public void update(final Integer id, final LayoutDTO layoutDTO) {
        final Layout layout = layoutRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(layoutDTO, layout);
        layoutRepository.save(layout);
    }

    public void delete(final Integer id) {
        layoutRepository.deleteById(id);
    }

    private LayoutDTO mapToDTO(final Layout layout, final LayoutDTO layoutDTO) {
        layoutDTO.setId(layout.getId());
        layoutDTO.setBlockId(layout.getBlockId());
        layoutDTO.setHouseNumber(layout.getHouseNumber());
        layoutDTO.setOwnerId(layout.getOwnerId());
        layoutDTO.setBlock(layout.getBlock() == null ? null : layout.getBlock().getBlockId());
        layoutDTO.setOwner(layout.getOwner() == null ? null : layout.getOwner().getId());
        return layoutDTO;
    }

    private Layout mapToEntity(final LayoutDTO layoutDTO, final Layout layout) {
        layout.setBlockId(layoutDTO.getBlockId());
        layout.setHouseNumber(layoutDTO.getHouseNumber());
        layout.setOwnerId(layoutDTO.getOwnerId());
        final Block block = layoutDTO.getBlock() == null ? null : blockRepository.findById(layoutDTO.getBlock())
                .orElseThrow(() -> new NotFoundException("block not found"));
        layout.setBlock(block);
        final Owner owner = layoutDTO.getOwner() == null ? null : ownerRepository.findById(layoutDTO.getOwner())
                .orElseThrow(() -> new NotFoundException("owner not found"));
        layout.setOwner(owner);
        return layout;
    }

    public ReferencedWarning getReferencedWarning(final Integer id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Layout layout = layoutRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final GateLog houseGateLog = gateLogRepository.findFirstByHouse(layout);
        if (houseGateLog != null) {
            referencedWarning.setKey("layout.gateLog.house.referenced");
            referencedWarning.addParam(houseGateLog.getLogId());
            return referencedWarning;
        }
        return null;
    }

}
