package com.kartike.my_gate.rest;

import com.kartike.my_gate.model.LayoutDTO;
import com.kartike.my_gate.service.LayoutService;
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
@RequestMapping(value = "/api/layouts", produces = MediaType.APPLICATION_JSON_VALUE)
public class LayoutResource {

    private final LayoutService layoutService;

    public LayoutResource(final LayoutService layoutService) {
        this.layoutService = layoutService;
    }

    @GetMapping
    public ResponseEntity<List<LayoutDTO>> getAllLayouts() {
        return ResponseEntity.ok(layoutService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LayoutDTO> getLayout(@PathVariable(name = "id") final Integer id) {
        return ResponseEntity.ok(layoutService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createLayout(@RequestBody @Valid final LayoutDTO layoutDTO) {
        final Integer createdId = layoutService.create(layoutDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Integer> updateLayout(@PathVariable(name = "id") final Integer id,
            @RequestBody @Valid final LayoutDTO layoutDTO) {
        layoutService.update(id, layoutDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLayout(@PathVariable(name = "id") final Integer id) {
        final ReferencedWarning referencedWarning = layoutService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        layoutService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
