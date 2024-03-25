package com.kartike.my_gate.rest;

import com.kartike.my_gate.model.GateLogDTO;
import com.kartike.my_gate.service.GateLogServiceImpl;
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
@RequestMapping(value = "/api/gateLogs", produces = MediaType.APPLICATION_JSON_VALUE)
public class GateLogResource {

    private final GateLogServiceImpl gateLogService;

    public GateLogResource(final GateLogServiceImpl gateLogService) {
        this.gateLogService = gateLogService;
    }

    @GetMapping
    public ResponseEntity<List<GateLogDTO>> getAllGateLogs() {
        return ResponseEntity.ok(gateLogService.findAll());
    }

    @GetMapping("/{logId}")
    public ResponseEntity<GateLogDTO> getGateLog(
            @PathVariable(name = "logId") final Integer logId) {
        return ResponseEntity.ok(gateLogService.get(logId));
    }

    @PostMapping
    public ResponseEntity<Integer> createGateLog(@RequestBody @Valid final GateLogDTO gateLogDTO) {
        final Integer createdLogId = gateLogService.create(gateLogDTO);
        return new ResponseEntity<>(createdLogId, HttpStatus.CREATED);
    }

}
