package com.kartike.my_gate.service;

import com.kartike.my_gate.domain.Block;
import com.kartike.my_gate.domain.Layout;
import com.kartike.my_gate.model.BlockDTO;
import com.kartike.my_gate.repos.BlockRepository;
import com.kartike.my_gate.repos.LayoutRepository;
import com.kartike.my_gate.util.NotFoundException;
import com.kartike.my_gate.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class BlockService {

    private final BlockRepository blockRepository;
    private final LayoutRepository layoutRepository;

    public BlockService(final BlockRepository blockRepository,
            final LayoutRepository layoutRepository) {
        this.blockRepository = blockRepository;
        this.layoutRepository = layoutRepository;
    }

    public List<BlockDTO> findAll() {
        final List<Block> blocks = blockRepository.findAll(Sort.by("blockId"));
        return blocks.stream()
                .map(block -> mapToDTO(block, new BlockDTO()))
                .toList();
    }

    public BlockDTO get(final Integer blockId) {
        return blockRepository.findById(blockId)
                .map(block -> mapToDTO(block, new BlockDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final BlockDTO blockDTO) {
        final Block block = new Block();
        mapToEntity(blockDTO, block);
        return blockRepository.save(block).getBlockId();
    }

    public void update(final Integer blockId, final BlockDTO blockDTO) {
        final Block block = blockRepository.findById(blockId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(blockDTO, block);
        blockRepository.save(block);
    }

    public void delete(final Integer blockId) {
        blockRepository.deleteById(blockId);
    }

    private BlockDTO mapToDTO(final Block block, final BlockDTO blockDTO) {
        blockDTO.setBlockId(block.getBlockId());
        blockDTO.setBlockName(block.getBlockName());
        return blockDTO;
    }

    private Block mapToEntity(final BlockDTO blockDTO, final Block block) {
        block.setBlockName(blockDTO.getBlockName());
        return block;
    }

    public ReferencedWarning getReferencedWarning(final Integer blockId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Block block = blockRepository.findById(blockId)
                .orElseThrow(NotFoundException::new);
        final Layout blockLayout = layoutRepository.findFirstByBlock(block);
        if (blockLayout != null) {
            referencedWarning.setKey("block.layout.block.referenced");
            referencedWarning.addParam(blockLayout.getId());
            return referencedWarning;
        }
        return null;
    }

}
