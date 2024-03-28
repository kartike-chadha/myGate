package com.kartike.my_gate.service.implementations;

import com.kartike.my_gate.domain.Block;
import com.kartike.my_gate.domain.GateLog;
import com.kartike.my_gate.domain.Layout;
import com.kartike.my_gate.domain.Owner;
import com.kartike.my_gate.model.LayoutDTO;
import com.kartike.my_gate.repos.BlockRepository;
import com.kartike.my_gate.repos.GateLogRepository;
import com.kartike.my_gate.repos.LayoutRepository;
import com.kartike.my_gate.repos.OwnerRepository;
import com.kartike.my_gate.service.interfaces.LayoutService;
import com.kartike.my_gate.util.NotFoundException;
import com.kartike.my_gate.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LayoutServiceImpl implements LayoutService {

    private final LayoutRepository layoutRepository;
    private final BlockRepository blockRepository;
    private final OwnerRepository ownerRepository;
    private final GateLogRepository gateLogRepository;

    public LayoutServiceImpl(final LayoutRepository layoutRepository,
                             final BlockRepository blockRepository, final OwnerRepository ownerRepository,
                             final GateLogRepository gateLogRepository) {
        this.layoutRepository = layoutRepository;
        this.blockRepository = blockRepository;
        this.ownerRepository = ownerRepository;
        this.gateLogRepository = gateLogRepository;
    }
    @Override
    public List<LayoutDTO> findAll() {
        final List<Layout> layouts = layoutRepository.findAll(Sort.by("id"));
        return layouts.stream()
                .map(layout -> mapToDTO(layout, new LayoutDTO()))
                .toList();
    }
    @Override
    public LayoutDTO get(final Integer id) {
        return layoutRepository.findById(id)
                .map(layout -> mapToDTO(layout, new LayoutDTO()))
                .orElseThrow(NotFoundException::new);
    }
    @Override
    public Integer createAndAssignLayout(final LayoutDTO layoutDTO) {
        final Layout layout = new Layout();
        mapToEntity(layoutDTO, layout);
        return layoutRepository.save(layout).getId();
    }
    @Override
    public void update(final Integer id, final LayoutDTO layoutDTO) {
        final Layout layout = layoutRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(layoutDTO, layout);
        layoutRepository.save(layout);
    }
    @Override
    public void delete(final Integer id) {
        layoutRepository.deleteById(id);
    }

    private LayoutDTO mapToDTO(final Layout layout, final LayoutDTO layoutDTO) {
        layoutDTO.setId(layout.getId());
        layoutDTO.setHouseNumber(layout.getHouseNumber());
        layoutDTO.setBlockId(layout.getBlock().getBlockId());
        layoutDTO.setOwnerId(layout.getOwner().getId());
        return layoutDTO;
    }

    private Layout mapToEntity(final LayoutDTO layoutDTO, final Layout layout) {
        layout.setId(layoutDTO.getId());
        layout.setHouseNumber(layoutDTO.getHouseNumber());
        Block block = blockRepository.findById(layoutDTO.getBlockId())
                .orElseThrow(() -> new RuntimeException("Block not found"));
        layout.setBlock(block);
        Owner owner = ownerRepository.findById(layoutDTO.getOwnerId())
                .orElseThrow(()->new RuntimeException("Owner not found"));
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
