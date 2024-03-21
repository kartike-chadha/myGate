package com.kartike.my_gate.rest;

import com.kartike.my_gate.model.BlockDTO;
import com.kartike.my_gate.service.BlockServiceImpl;
import com.kartike.my_gate.util.ReferencedException;
import com.kartike.my_gate.util.ReferencedWarning;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/blocks", produces = MediaType.APPLICATION_JSON_VALUE)
public class BlockResource {

    private final BlockServiceImpl blockService;

    public BlockResource(final BlockServiceImpl blockService) {
        this.blockService = blockService;
    }

    @GetMapping
    public ResponseEntity<List<BlockDTO>> getAllBlocks() {
        return ResponseEntity.ok(blockService.findAll());
    }

    @GetMapping("/{blockId}")
    public ResponseEntity<BlockDTO> getBlock(
            @PathVariable(name = "blockId") final Integer blockId) {
        return ResponseEntity.ok(blockService.get(blockId));
    }

    @PostMapping
    public ResponseEntity<Integer> createBlock(@RequestBody @Valid final BlockDTO blockDTO) {
        final Integer createdBlockId = blockService.create(blockDTO);
        return new ResponseEntity<>(createdBlockId, HttpStatus.CREATED);
    }

    @PutMapping("/{blockId}")
    public ResponseEntity<Integer> updateBlock(
            @PathVariable(name = "blockId") final Integer blockId,
            @RequestBody @Valid final BlockDTO blockDTO) {
        blockService.update(blockId, blockDTO);
        return ResponseEntity.ok(blockId);
    }

    @DeleteMapping("/{blockId}")
    public ResponseEntity<Void> deleteBlock(@PathVariable(name = "blockId") final Integer blockId) {
        final ReferencedWarning referencedWarning = blockService.getReferencedWarning(blockId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        blockService.delete(blockId);
        return ResponseEntity.noContent().build();
    }

}
