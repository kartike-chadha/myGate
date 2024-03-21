package com.kartike.my_gate.rest;

import com.kartike.my_gate.model.PayableDTO;
import com.kartike.my_gate.service.PayableServiceImpl;
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
@RequestMapping(value = "/api/payables", produces = MediaType.APPLICATION_JSON_VALUE)
public class PayableResource {

    private final PayableServiceImpl payableService;

    public PayableResource(final PayableServiceImpl payableService) {
        this.payableService = payableService;
    }

    @GetMapping
    public ResponseEntity<List<PayableDTO>> getAllPayables() {
        return ResponseEntity.ok(payableService.findAll());
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PayableDTO> getPayable(
            @PathVariable(name = "paymentId") final Integer paymentId) {
        return ResponseEntity.ok(payableService.get(paymentId));
    }

    @PostMapping
    public ResponseEntity<Integer> createPayable(@RequestBody @Valid final PayableDTO payableDTO) {
        final Integer createdPaymentId = payableService.create(payableDTO);
        return new ResponseEntity<>(createdPaymentId, HttpStatus.CREATED);
    }

    @PutMapping("/{paymentId}")
    public ResponseEntity<Integer> updatePayable(
            @PathVariable(name = "paymentId") final Integer paymentId,
            @RequestBody @Valid final PayableDTO payableDTO) {
        payableService.update(paymentId, payableDTO);
        return ResponseEntity.ok(paymentId);
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Void> deletePayable(
            @PathVariable(name = "paymentId") final Integer paymentId) {
        payableService.delete(paymentId);
        return ResponseEntity.noContent().build();
    }

}
